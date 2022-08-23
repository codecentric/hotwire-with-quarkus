package de.codecentric.todo.rest;

import de.codecentric.todo.core.api.AddTodoUseCase;
import de.codecentric.todo.core.api.MarkTodoCompleteUseCase;
import de.codecentric.todo.core.api.QueryTodoUseCase;
import de.codecentric.todo.core.api.RemoveTodoUseCase;
import de.codecentric.todo.core.api.types.TodoDTO;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.MultipartForm;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.sse.OutboundSseEvent;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseBroadcaster;
import javax.ws.rs.sse.SseEventSink;
import java.util.List;
import java.util.UUID;

/**
 * Resource for the samples with {@code turbo-stream} events via Server-Sent-Events (SSE).
 *
 * @author Felix Riess, codecentric AG
 * @since 22 Aug 2022
 */
@Path("/todos/sse")
@Singleton
public class TodoSSEResource {

    private static final Logger LOG = Logger.getLogger(TodoSSEResource.class);

    private final AddTodoUseCase addTodoUseCase;
    private final MarkTodoCompleteUseCase markTodoCompleteUseCase;
    private final QueryTodoUseCase queryTodoUseCase;
    private final RemoveTodoUseCase removeTodoUseCase;

    private final Sse sse;
    private final SseBroadcaster broadcaster;

    @Inject
    TodoSSEResource(@Context Sse sse, AddTodoUseCase addTodoUseCase, MarkTodoCompleteUseCase markTodoCompleteUseCase,
                    QueryTodoUseCase queryTodoUseCase, RemoveTodoUseCase removeTodoUseCase) {
        this.sse = sse;
        this.broadcaster = this.sse.newBroadcaster();
        this.addTodoUseCase = addTodoUseCase;
        this.markTodoCompleteUseCase = markTodoCompleteUseCase;
        this.queryTodoUseCase = queryTodoUseCase;
        this.removeTodoUseCase = removeTodoUseCase;
    }

    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance todoListSse(final List<TodoDTO> todoList);
        public static native TemplateInstance todoListSseAddTodo(final TodoDTO todo);
        public static native TemplateInstance todoListSseMarkTodoCompleted(final TodoDTO todo);
        public static native TemplateInstance todoListSseRemoveTodo(final UUID todoId, final boolean noTodoLeft);
    }

    @GET
    @Path("/connect")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public void registerSink(@Context SseEventSink eventSink) {
        LOG.info("Registering new connection");
        this.broadcaster.register(eventSink);
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getTodoList() throws InterruptedException {
        LOG.info("Sleeping to simulate long running process");
        Thread.sleep(1500);
        return Templates.todoListSse(this.queryTodoUseCase.findAll());
    }


    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public void addTodo(@MultipartForm final TodoCreationForm todoCreationForm) {
        final UUID created = this.addTodoUseCase.addTodo(todoCreationForm.getName());
        final String rendered = Templates.todoListSseAddTodo(this.queryTodoUseCase.findById(created))
                                         .render()
                                         .replace("\n", ""); // replace line breaks to send only one "data:" attribute. Else for each new line a "data:" attribute will be sent.
        final OutboundSseEvent addTodoEvent = this.sse.newEventBuilder()
                                                      .id("ADD" + created.toString())
                                                      .mediaType(MediaType.TEXT_HTML_TYPE)
                                                      .data(String.class, rendered)
                                                      .build();
        this.broadcaster.broadcast(addTodoEvent);
    }

    @POST
    @Path("/{todoId}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public void markTodoCompleted(@PathParam("todoId") final UUID todoId) {
        this.markTodoCompleteUseCase.markCompleted(todoId);
        final String rendered = Templates.todoListSseMarkTodoCompleted(this.queryTodoUseCase.findById(todoId))
                                         .render()
                                         .replace("\n", ""); // replace line breaks to send only one "data:" attribute. Else for each new line a "data:" attribute will be sent.
        final OutboundSseEvent markTodoCompletedEvent = this.sse.newEventBuilder()
                                                                .id("COM" + todoId.toString())
                                                                .mediaType(MediaType.TEXT_HTML_TYPE)
                                                                .data(String.class, rendered)
                                                                .build();
        this.broadcaster.broadcast(markTodoCompletedEvent);
    }

    @POST
    @Path("/remove/{todoId}")
    public void removeTodo(@PathParam("todoId") final UUID todoId) {
        this.removeTodoUseCase.removeTodo(todoId);
        final String rendered = Templates.todoListSseRemoveTodo(todoId, this.queryTodoUseCase.findAll().isEmpty())
                                         .render()
                                         .replace("\n", ""); // replace line breaks to send only one "data:" attribute. Else for each new line a "data:" attribute will be sent.
        final OutboundSseEvent removeTodoEvent = this.sse.newEventBuilder()
                                                         .id("DEL" + todoId.toString())
                                                         .mediaType(MediaType.TEXT_HTML_TYPE)
                                                         .data(String.class, rendered)
                                                         .build();
        this.broadcaster.broadcast(removeTodoEvent);
    }
}

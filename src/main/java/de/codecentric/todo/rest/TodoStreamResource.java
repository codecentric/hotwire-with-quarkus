package de.codecentric.todo.rest;

import de.codecentric.todo.core.api.AddTodoUseCase;
import de.codecentric.todo.core.api.MarkTodoCompleteUseCase;
import de.codecentric.todo.core.api.QueryTodoUseCase;
import de.codecentric.todo.core.api.RemoveTodoUseCase;
import de.codecentric.todo.core.api.types.TodoDTO;
import de.codecentric.todo.rest.types.TodoCreationForm;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.MultipartForm;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.UUID;

/**
 * Resource for the samples with {@code turbo-stream} responses based on user interaction.
 *
 * @author Felix Riess, codecentric AG
 * @since 19 Aug 2022
 */
@Path("/todos/stream")
@Produces(MediaType.TEXT_HTML)
public class TodoStreamResource {

    private static final Logger LOG = Logger.getLogger(TodoStreamResource.class);

    private final AddTodoUseCase addTodoUseCase;
    private final MarkTodoCompleteUseCase markTodoCompleteUseCase;
    private final QueryTodoUseCase queryTodoUseCase;
    private final RemoveTodoUseCase removeTodoUseCase;

    @Inject
    TodoStreamResource(AddTodoUseCase addTodoUseCase, MarkTodoCompleteUseCase markTodoCompleteUseCase,
                       QueryTodoUseCase queryTodoUseCase, RemoveTodoUseCase removeTodoUseCase) {
        this.addTodoUseCase = addTodoUseCase;
        this.markTodoCompleteUseCase = markTodoCompleteUseCase;
        this.queryTodoUseCase = queryTodoUseCase;
        this.removeTodoUseCase = removeTodoUseCase;
    }

    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance todoListStream(final List<TodoDTO> todoList);
        public static native TemplateInstance todoListMarkTodoCompleted(final TodoDTO todo);
        public static native TemplateInstance todoListRemoveTodo(final UUID todoId, final boolean noTodoLeft);
        public static native TemplateInstance todoListAddTodo(final TodoDTO todo);
    }

    @GET
    public TemplateInstance getTodoList() throws InterruptedException {
        LOG.info("Sleeping to simulate long running process");
        Thread.sleep(1500);
        return Templates.todoListStream(this.queryTodoUseCase.findAll());
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(ApplicationResource.TURBO_STREAM_RESPONSE_TYPE)
    public TemplateInstance addTodo(@MultipartForm final TodoCreationForm todoCreationForm) {
        final UUID created = this.addTodoUseCase.addTodo(todoCreationForm.getName());
        return Templates.todoListAddTodo(this.queryTodoUseCase.findById(created));
    }

    @POST
    @Path("/remove/{todoId}")
    @Produces(ApplicationResource.TURBO_STREAM_RESPONSE_TYPE)
    public TemplateInstance removeViaStream(@PathParam("todoId") final UUID todoId) {
        this.removeTodoUseCase.removeTodo(todoId);
        return Templates.todoListRemoveTodo(todoId, this.queryTodoUseCase.findAll().isEmpty());
    }

    @POST
    @Path("/{todoId}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(ApplicationResource.TURBO_STREAM_RESPONSE_TYPE)
    public TemplateInstance markTodoAsDoneViaStream(@PathParam("todoId") final UUID todoId) {
        this.markTodoCompleteUseCase.markCompleted(todoId);
        return Templates.todoListMarkTodoCompleted(this.queryTodoUseCase.findById(todoId));
    }
}

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
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.UUID;

@Path("/todos")
@Produces(MediaType.TEXT_HTML)
public class TodoResource {

    private static final Logger LOG = Logger.getLogger(TodoResource.class);

    @Inject
    AddTodoUseCase addTodoUseCase;
    @Inject
    MarkTodoCompleteUseCase markTodoCompleteUseCase;
    @Inject
    QueryTodoUseCase queryTodoUseCase;
    @Inject
    RemoveTodoUseCase removeTodoUseCase;

    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance todos(final List<TodoDTO> todoList);
        public static native TemplateInstance todoDetail(final TodoDTO todo);
    }

    @GET
    public TemplateInstance getTodoList() throws InterruptedException {
        LOG.info("Sleeping to simulate long running process");
        Thread.sleep(1500);
        return Templates.todos(this.queryTodoUseCase.findAll());
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public TemplateInstance addTodo(@MultipartForm final TodoCreationForm todoCreationForm) {
        this.addTodoUseCase.addTodo(todoCreationForm.getName());
        return Templates.todos(this.queryTodoUseCase.findAll());
    }

    @POST
    @Path("/remove/{todoId}")
    public TemplateInstance remove(@PathParam("todoId") final UUID todoId) {
        this.removeTodoUseCase.removeTodo(todoId);
        return Templates.todos(this.queryTodoUseCase.findAll());
    }

    @GET
    @Path("/{todoId}")
    public TemplateInstance getTodo(@PathParam("todoId") final UUID todoId) {
        return Templates.todoDetail(queryTodoUseCase.findById(todoId));
    }

    @POST
    @Path("/{todoId}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public TemplateInstance markTodoAsDone(@PathParam("todoId") final UUID todoId) {
        this.markTodoCompleteUseCase.markCompleted(todoId);
        return Templates.todos(this.queryTodoUseCase.findAll());
    }
}

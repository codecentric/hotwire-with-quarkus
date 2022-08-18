package de.codecentric.todo;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.MultipartForm;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Path("/todos")
@Produces(MediaType.TEXT_HTML)
public class TodoResource {

    private static final Logger LOG = Logger.getLogger(TodoResource.class);
    private final List<Todo> todoList = new ArrayList<>();

    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance todos(final List<Todo> todoList);
        public static native TemplateInstance todoDetail(final Todo todo);
    }

    @GET
    public TemplateInstance getTodoList() throws InterruptedException {
        LOG.info("Sleeping to simulate long running process");
        Thread.sleep(3000);
        return Templates.todos(this.todoList);
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response addTodo(@MultipartForm final TodoCreationForm todoCreationForm) {
        final Todo created = todoCreationForm.fromForm();
        this.todoList.add(created);
        return Response.seeOther(URI.create("/")).build();
    }

    @GET
    @Path("/{todoId}")
    public TemplateInstance getTodo(@PathParam("todoId") final UUID todoId) {
        Optional<Todo> todoWithId = findById(todoId);
        if (todoWithId.isPresent()) {
            return Templates.todoDetail(todoWithId.get());
        } else {
            throw new NotFoundException("Todo with id " + todoId.toString() + " is not existing");
        }
    }

    @POST
    @Path("/{todoId}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public TemplateInstance markTodoAsDone(@PathParam("todoId") final UUID todoId) {
        Optional<Todo> todoWithId = findById(todoId);
        if (todoWithId.isPresent()) {
            todoWithId.get().markComplete();
            return Templates.todos(this.todoList);
        } else {
            throw new NotFoundException("Todo with id " + todoId.toString() + " is not existing");
        }
    }

    private Optional<Todo> findById(final UUID id) {
        return this.todoList.stream().filter(todo -> todo.getId().equals(id)).findFirst();
    }
}

package de.codecentric.todo;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import org.jboss.resteasy.reactive.MultipartForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Path("/todos")
@Produces(MediaType.TEXT_HTML)
public class TodoResource {

    private static final Logger LOG = LoggerFactory.getLogger(TodoResource.class);
    private final List<Todo> todoList = new ArrayList<>();

    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance todos(final List<Todo> todoList);
        public static native TemplateInstance todoDetail(final Todo todo);
    }

    @GET
    public TemplateInstance getTodoList() throws InterruptedException {
        Thread.sleep(3000);
        return Templates.todos(this.todoList);
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response addTodo(@MultipartForm final TodoCreationForm todoCreationForm) {
        final Todo created = todoCreationForm.fromForm();
        this.todoList.add(created);
        LOG.info("Todo List elements {}", this.todoList);
        return Response.seeOther(URI.create("/todos/")).build();
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
    public Response markTodoAsDone(@PathParam("todoId") final UUID todoId) {
        Optional<Todo> todoWithId = findById(todoId);
        if (todoWithId.isPresent()) {
            todoWithId.get().markComplete();
            return Response.seeOther(URI.create("/todos/")).build();
        } else {
            throw new NotFoundException("Todo with id " + todoId.toString() + " is not existing");
        }
    }

    private Optional<Todo> findById(final UUID id) {
        return this.todoList.stream().filter(todo -> todo.getId().equals(id)).findFirst();
    }
}

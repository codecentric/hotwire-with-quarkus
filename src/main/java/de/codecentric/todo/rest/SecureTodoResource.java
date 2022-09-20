package de.codecentric.todo.rest;

import de.codecentric.common.logging.ILogger;
import de.codecentric.todo.core.api.AddTodoUseCase;
import de.codecentric.todo.core.api.MarkTodoCompleteUseCase;
import de.codecentric.todo.core.api.QueryTodoUseCase;
import de.codecentric.todo.core.api.QueryUsersUseCase;
import de.codecentric.todo.core.api.RemoveTodoUseCase;
import de.codecentric.todo.core.api.types.TodoDTO;
import de.codecentric.todo.rest.types.TodoCreationForm;
import io.quarkus.oidc.IdToken;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.security.Authenticated;
import io.quarkus.security.ForbiddenException;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.resteasy.reactive.MultipartForm;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@Authenticated
@Path("/secure")
public class SecureTodoResource {

    private static final ILogger LOG = ILogger.getLogger(SecureTodoResource.class);

    @IdToken
    @Inject
    JsonWebToken idToken;

    @Inject
    QueryTodoUseCase queryTodoUseCase;
    @Inject
    AddTodoUseCase addTodoUseCase;
    @Inject
    RemoveTodoUseCase removeTodoUseCase;
    @Inject
    MarkTodoCompleteUseCase markTodoCompleteUseCase;
    @Inject
    QueryUsersUseCase queryUsersUseCase;

    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance appHome(boolean readonly, boolean isAdmin, UUID userId);
        public static native TemplateInstance todoList(boolean readonly, List<TodoDTO> todoList);
        public static native TemplateInstance todoListAdd(TodoDTO todo);
        public static native TemplateInstance todoListRemove(UUID todoId, boolean noTodoLeft);
        public static native TemplateInstance todoListMarkCompleted(TodoDTO todo);
        public static native TemplateInstance users(List<UUID> users, boolean isAdmin);
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance renderAppHome(@QueryParam("userId") final UUID userId, @Context SecurityContext context) {
        final String currentLoggedIn = this.idToken.getSubject();
        final boolean isAdmin = isAdmin(context);
        if (userId == null) {
            return Templates.appHome(false, isAdmin, UUID.fromString(currentLoggedIn));
        } else {
            final boolean isReadonly = isReadOnlyAccess(userId, UUID.fromString(currentLoggedIn), isAdmin);
            return Templates.appHome(isReadonly, isAdmin, userId);
        }
    }

    @GET
    @Path("/todos")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance listTodos(@QueryParam("userId") final UUID userId, @Context SecurityContext context) {
        final String currentLoggedIn = this.idToken.getSubject();
        final boolean isAdmin = isAdmin(context);
        final boolean isReadonly = isReadOnlyAccess(userId, UUID.fromString(currentLoggedIn), isAdmin);
        final List<TodoDTO> todoList = this.queryTodoUseCase.findByUserId(userId);
        return Templates.todoList(isReadonly, todoList);
    }

    @POST
    @Path("/todos")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(ApplicationResource.TURBO_STREAM_RESPONSE_TYPE)
    public TemplateInstance addTodo(@MultipartForm final TodoCreationForm todoCreationForm) {
        final UUID currentLoggedIn = UUID.fromString(this.idToken.getSubject());
        final UUID created = this.addTodoUseCase.addTodo(todoCreationForm.getName(), currentLoggedIn);
        return Templates.todoListAdd(this.queryTodoUseCase.findById(created));
    }

    @POST
    @Path("/todos/remove/{todoId}")
    @Produces(ApplicationResource.TURBO_STREAM_RESPONSE_TYPE)
    public TemplateInstance removeTodo(@PathParam("todoId") final UUID todoId) {
        final UUID currentLoggedIn = UUID.fromString(this.idToken.getSubject());
        final boolean hasUserTodo = this.queryUsersUseCase.hasUserTodo(currentLoggedIn, todoId);
        if (hasUserTodo) {
            this.removeTodoUseCase.removeTodo(todoId);
            return Templates.todoListRemove(todoId, this.queryTodoUseCase.findByUserId(currentLoggedIn).isEmpty());
        } else {
            LOG.warn("User {} wanted to remove todo {} which is not his own");
            throw new ForbiddenException("You can't remove others todos!");
        }
    }

    @POST
    @Path("/todos/{todoId}")
    @Produces(ApplicationResource.TURBO_STREAM_RESPONSE_TYPE)
    public TemplateInstance markTodoCompleted(@PathParam("todoId") final UUID todoId) {
        final UUID currentLoggedIn = UUID.fromString(this.idToken.getSubject());
        final boolean hasUserTodo = this.queryUsersUseCase.hasUserTodo(currentLoggedIn, todoId);
        if (hasUserTodo) {
            this.markTodoCompleteUseCase.markCompleted(todoId);
            return Templates.todoListMarkCompleted(this.queryTodoUseCase.findById(todoId));
        } else {
            LOG.warn("User {} wanted to mark todo {} as completed which is not his own", currentLoggedIn, todoId);
            throw new ForbiddenException("You can't mark others todos as completed!");
        }
    }

    @GET
    @Path("/users")
    @RolesAllowed({"ADMIN"})
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getUsersWithTodo(@Context SecurityContext context) {
        return Templates.users(this.queryUsersUseCase.findAll(), isAdmin(context));
    }

    @GET
    @Path("/logout")
    public Response logout() {
        // this method is only existing because otherwise quarkus thinks "/secure/logout" does not exist.
        // The "real" config for this endpoint is located in application.properties.
        return Response.seeOther(URI.create("/")).build();
    }

    private boolean isAdmin(SecurityContext securityContext) {
        return securityContext.isUserInRole("ADMIN");
    }

    private boolean isReadOnlyAccess(UUID resourceUserId, UUID loggedInUserId, boolean isAdmin) {
        if (resourceUserId.equals(loggedInUserId)) {
            LOG.info("User {} is loading own resource", resourceUserId);
            return false;
        } else if (isAdmin) {
            LOG.info("User {} is admin and accesses resource of user {}", loggedInUserId, resourceUserId);
            return true;
        } else {
            LOG.warn("User {} tried to access resource of user {} although not being an ADMIN", loggedInUserId, resourceUserId);
            throw new ForbiddenException("You are not allowed to access this page");
        }
    }
}

package de.codecentric.todo.rest;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Root Resource where the different "Home" pages are controlled.
 *
 * @author Felix Riess, codecentric AG
 * @since 18 Aug 2022
 */
@Path("/")
@Produces(MediaType.TEXT_HTML)
public class ApplicationResource {

    /**
     * The response type for {@code turbo-stream} responses. Public constant to be accessible for each other resource.
     */
    public static final String TURBO_STREAM_RESPONSE_TYPE = "text/vnd.turbo-stream.html";

    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance todoAppHome();
        public static native TemplateInstance todoAppStreamHome();
        public static native TemplateInstance todoAppSseHome();
    }

    @GET
    public TemplateInstance renderAppHome() {
        return Templates.todoAppHome();
    }

    @GET
    @Path("/stream")
    public TemplateInstance renderStreamAppHome() {
        return Templates.todoAppStreamHome();
    }

    @GET
    @Path("/sse")
    public TemplateInstance renderSseAppHome() {
        return Templates.todoAppSseHome();
    }
}

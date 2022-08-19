package de.codecentric.todo.rest;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
@Produces(MediaType.TEXT_HTML)
public class ApplicationResource {

    public static final String TURBO_STREAM_RESPONSE_TYPE = "text/vnd.turbo-stream.html";

    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance todoAppHome();
        public static native TemplateInstance todoAppStreamHome();
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
}

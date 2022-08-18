package de.codecentric;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
@Produces(MediaType.TEXT_HTML)
public class ApplicationResource {

    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance todoAppHome();
    }

    @GET
    public TemplateInstance renderAppHome() {
        return Templates.todoAppHome();
    }
}

package de.codecentric.auth.rest;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.security.Authenticated;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/secret")
@Authenticated
public class AuthResource {

    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance secret();
        public static native TemplateInstance userSecret();
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @RolesAllowed("ADMIN")
    public TemplateInstance secret() {
        return Templates.secret();
    }

    @GET
    @Path("/users")
    @RolesAllowed("USER")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance secretForUsers() {
        return Templates.userSecret();
    }
}

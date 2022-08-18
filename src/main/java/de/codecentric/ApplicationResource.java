package de.codecentric;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("/")
public class ApplicationResource {

    @GET
    public Response redirect() {
        return Response.seeOther(URI.create("/todos/")).build();
    }
}

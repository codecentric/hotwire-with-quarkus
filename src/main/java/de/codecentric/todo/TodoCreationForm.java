package de.codecentric.todo;

import org.jboss.resteasy.reactive.PartType;

import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;

public class TodoCreationForm {

    public @FormParam("name") @PartType(MediaType.TEXT_PLAIN) String name;

    public Todo fromForm() {
        return new Todo(this.name);
    }
}

package de.codecentric.todo.rest;

import org.jboss.resteasy.reactive.PartType;

import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;

public class TodoCreationForm {

    private @FormParam("name") @PartType(MediaType.TEXT_PLAIN) String name;

    public String getName() {
        return name;
    }
}

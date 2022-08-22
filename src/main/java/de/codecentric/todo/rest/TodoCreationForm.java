package de.codecentric.todo.rest;

import org.jboss.resteasy.reactive.PartType;
import org.jboss.resteasy.reactive.RestForm;

import javax.ws.rs.core.MediaType;

public class TodoCreationForm {

    private @RestForm("name") @PartType(MediaType.TEXT_PLAIN) String name;

    public String getName() {
        return name;
    }
}

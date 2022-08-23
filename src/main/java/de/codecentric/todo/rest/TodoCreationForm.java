package de.codecentric.todo.rest;

import org.jboss.resteasy.reactive.PartType;
import org.jboss.resteasy.reactive.RestForm;

import javax.ws.rs.core.MediaType;

/**
 * The representation of the todo creation form which is used by the client.
 *
 * @author Felix Riess, codecentric AG
 * @since 17 Aug 2022
 */
public class TodoCreationForm {

    private @RestForm("name") @PartType(MediaType.TEXT_PLAIN) String name;

    public String getName() {
        return this.name;
    }
}

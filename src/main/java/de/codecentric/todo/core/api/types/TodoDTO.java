package de.codecentric.todo.core.api.types;

import java.util.UUID;

public class TodoDTO {

    private final UUID id;
    private final String name;
    private final boolean completed;

    public TodoDTO(UUID id, String name, boolean completed) {
        this.id = id;
        this.name = name;
        this.completed = completed;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isCompleted() {
        return completed;
    }
}

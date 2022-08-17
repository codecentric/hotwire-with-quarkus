package de.codecentric.todo;

import java.util.UUID;

public class Todo {

    private final UUID id;
    private final String name;
    private boolean completed;

    public Todo(final String name) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.completed = false;
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

    public void markComplete() {
        this.completed = true;
    }
}

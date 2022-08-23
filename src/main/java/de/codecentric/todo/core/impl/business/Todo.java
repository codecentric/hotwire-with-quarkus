package de.codecentric.todo.core.impl.business;

import java.util.Objects;
import java.util.UUID;

/**
 * An entity representing a Todo.
 *
 * @author Felix Riess, codecentric AG
 * @since 17 Aug 2022
 */
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Todo todo = (Todo) o;

        return Objects.equals(id, todo.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}

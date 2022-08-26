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
    private final UUID userId;

    public Todo(final String name) {
        this(name, null);
    }

    public Todo(final String name, final UUID userId) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.completed = false;
        this.userId = userId;
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


    public UUID getUserId() {
        return userId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Todo todo = (Todo) o;
        return id.equals(todo.id) && Objects.equals(userId, todo.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId);
    }
}

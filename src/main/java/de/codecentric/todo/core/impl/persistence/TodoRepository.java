package de.codecentric.todo.core.impl.persistence;

import de.codecentric.todo.core.impl.business.Todo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TodoRepository {

    List<Todo> findAll();

    Optional<Todo> findById(UUID id);

    void remove(UUID id);

    UUID add(Todo todo);
}

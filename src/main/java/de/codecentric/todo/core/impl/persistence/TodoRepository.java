package de.codecentric.todo.core.impl.persistence;

import de.codecentric.todo.core.impl.business.Todo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface to access persisted todos.
 *
 * @author Felix Riess, codecentric AG
 * @since 18 Aug 2022
 */
public interface TodoRepository {

    /**
     * Find all persisted todos.
     *
     * @return a {@link List} of all todos as {@link Todo}.
     */
    List<Todo> findAll();

    /**
     * Find all persisted todos of a specific user.
     *
     * @param userId the id of the user.
     * @return a {@link List} of all todos of the provided user as {@link Todo}.
     */
    List<Todo> findByUserId(UUID userId);

    /**
     * Find a todo by its id.
     *
     * @param id the id of the todo.
     * @return an {@link Optional} which may contain the existing todo with the provided id.
     */
    Optional<Todo> findById(UUID id);

    /**
     * Removes a todo with the provided id.
     *
     * @param id id of the todo which should be removed.
     */
    void remove(UUID id);

    /**
     * Adds a todo.
     *
     * @param todo the todo to be added.
     * @return the ID of the newly added todo.
     */
    UUID add(Todo todo);
}

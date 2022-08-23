package de.codecentric.todo.core.api;

import java.util.UUID;

/**
 * Interface for the use case to remove a todo.
 *
 * @author Felix Riess, codecentric AG
 * @since 18 Aug 2022
 */
public interface RemoveTodoUseCase {

    /**
     * Remove the todo with the provided id.
     *
      * @param todoId the id of the todo to be removed (not {@code null}).
     */
    void removeTodo(UUID todoId);
}

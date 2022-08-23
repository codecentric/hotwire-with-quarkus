package de.codecentric.todo.core.api;

import java.util.UUID;

/**
 * Interface for the use case to mark a todo as complete.
 *
 * @author Felix Riess, codecentric AG
 * @since 18 Aug 2022
 */
public interface MarkTodoCompleteUseCase {

    /**
     * Marks the todo with the provided id as completed.
     *
     * @param todoId the id of the todo which should be marked as complete (not {@code null}).
     */
    void markCompleted(UUID todoId);
}

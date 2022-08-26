package de.codecentric.todo.core.api;

import java.util.UUID;

/**
 * Interface for the use case to add a todo.
 *
 * @author Felix Riess, codecentric AG
 * @since 18 Aug 2022
 */
public interface AddTodoUseCase {

    /**
     * Adds a todo to the existing ones.
     *
     * @param name the name of the todo (not {@code null} or empty).
     * @return the id of the created todo as {@link UUID}.
     */
    UUID addTodo(String name);

    /**
     * Adds a todo for a user to the existing ones.
     *
     * @param name the name of the todo (not {@code null} or empty).
     * @param userId the user id who created the todo (not {@code null}).
     * @return the id of the created todo as {@link UUID}.
     */
    UUID addTodo(String name, UUID userId);
}

package de.codecentric.todo.core.api;

import java.util.List;
import java.util.UUID;

/**
 * Interface for the uses cases to query users.
 *
 * @author Felix Riess, codecentric AG
 * @since 26 Aug 2022
 */
public interface QueryUsersUseCase {

    /**
     * Find all users who have at least one todo.
     *
     * @return a {@link List} with all users who have at least one todo.
     */
    List<UUID> findAll();

    /**
     * Returns {@code true} if todo with provided id belongs to the provided user, {@code false} otherwise.
     *
     * @param userId the id of the user (not {@code null}).
     * @param todoId thei id of the todo (not {@code null}).
     * @return {@code true} if todo belongs to user, {@code false} otherwise.
     */
    boolean hasUserTodo(UUID userId, UUID todoId);
}

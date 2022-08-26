package de.codecentric.todo.core.api;

import de.codecentric.todo.core.api.types.TodoDTO;

import java.util.List;
import java.util.UUID;

/**
 * Interface for the use cases to query todos.
 *
 * @author Felix Riess, codecentric AG
 * @since 18 Aug 2022
 */
public interface QueryTodoUseCase {

    /**
     * Find a todo by its id.
     *
     * @param todoId the id of the todo to be returned (not {@code null}).
     * @return the todo with the provided id as {@link TodoDTO}.
     */
    TodoDTO findById(UUID todoId);

    /**
     * Find all existing todos.
     *
     * @return all existing todos as {@link List} of {@link TodoDTO}.
     */
    List<TodoDTO> findAll();

    /**
     * Find all todos of a specific user.
     *
     * @param userId the user id for which all todos should be returned (not {@code null}).
     * @return all todos of the user as {@link List} of {@link TodoDTO}.
     */
    List<TodoDTO> findByUserId(UUID userId);
}

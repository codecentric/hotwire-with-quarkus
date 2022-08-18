package de.codecentric.todo.core.api;

import de.codecentric.todo.core.api.types.TodoDTO;

import java.util.List;
import java.util.UUID;

public interface QueryTodoUseCase {

    TodoDTO findById(UUID todoId);

    List<TodoDTO> findAll();
}

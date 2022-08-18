package de.codecentric.todo.core.api;

import java.util.UUID;

public interface AddTodoUseCase {

    UUID addTodo(String name);
}

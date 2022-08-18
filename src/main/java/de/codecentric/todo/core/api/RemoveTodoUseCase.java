package de.codecentric.todo.core.api;

import java.util.UUID;

public interface RemoveTodoUseCase {

    void removeTodo(UUID todoId);
}

package de.codecentric.todo.core.api;

import java.util.UUID;

public interface MarkTodoCompleteUseCase {

    void markCompleted(UUID todoId);
}

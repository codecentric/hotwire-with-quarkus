package de.codecentric.todo.core.impl.access;

import de.codecentric.todo.core.api.AddTodoUseCase;
import de.codecentric.todo.core.api.MarkTodoCompleteUseCase;
import de.codecentric.todo.core.api.QueryTodoUseCase;
import de.codecentric.todo.core.api.RemoveTodoUseCase;
import de.codecentric.todo.core.api.types.TodoDTO;
import de.codecentric.todo.core.impl.business.Todo;
import de.codecentric.todo.core.impl.business.TodoService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class TodoFacade implements AddTodoUseCase, MarkTodoCompleteUseCase, QueryTodoUseCase, RemoveTodoUseCase {

    private final TodoService todoService;

    @Inject
    TodoFacade(TodoService todoService) {
        this.todoService = todoService;
    }

    @Override
    public UUID addTodo(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Todo name must not be null or empty");
        }
        return this.todoService.addTodo(name);
    }

    @Override
    public void markCompleted(UUID todoId) {
        if (todoId == null) {
            throw new IllegalArgumentException("Todo Id must not be null");
        }
        this.todoService.markCompleted(todoId);
    }

    @Override
    public TodoDTO findById(UUID todoId) {
        if (todoId == null) {
            throw new IllegalArgumentException("Todo Id must not be null");
        }
        return mapToDTO(this.todoService.findById(todoId));
    }

    @Override
    public List<TodoDTO> findAll() {
        return this.todoService.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public void removeTodo(UUID todoId) {
        if (todoId == null) {
            throw new IllegalArgumentException("Todo Id must not be null");
        }
        this.todoService.removeTodo(todoId);
    }

    private TodoDTO mapToDTO(Todo in) {
        return new TodoDTO(in.getId(), in.getName(), in.isCompleted());
    }
}

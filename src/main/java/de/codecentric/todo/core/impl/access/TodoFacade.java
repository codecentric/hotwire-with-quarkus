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

    @Inject
    TodoService todoService;

    @Override
    public UUID addTodo(String name) {
        return this.todoService.addTodo(name);
    }

    @Override
    public void markCompleted(UUID todoId) {
        this.todoService.markCompleted(todoId);
    }

    @Override
    public TodoDTO findById(UUID todoId) {
        return mapToDTO(this.todoService.findById(todoId));
    }

    @Override
    public List<TodoDTO> findAll() {
        return this.todoService.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public void removeTodo(UUID todoId) {
        this.todoService.removeTodo(todoId);
    }

    private TodoDTO mapToDTO(Todo in) {
        return new TodoDTO(in.getId(), in.getName(), in.isCompleted());
    }
}

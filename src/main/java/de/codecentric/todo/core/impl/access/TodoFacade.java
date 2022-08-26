package de.codecentric.todo.core.impl.access;

import de.codecentric.common.validation.ArgumentChecker;
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

/**
 * The Facade of the todo component which implements the use cases.
 *
 * @author Felix Riess, codecentric AG
 * @since 18 Aug 2022
 */
@ApplicationScoped
public class TodoFacade implements AddTodoUseCase, MarkTodoCompleteUseCase, QueryTodoUseCase, RemoveTodoUseCase {

    private final TodoService todoService;

    @Inject
    TodoFacade(TodoService todoService) {
        this.todoService = todoService;
    }

    @Override
    public UUID addTodo(String name) {
        ArgumentChecker.checkNotEmpty(name, "Todo name");
        return this.todoService.addTodo(name);
    }

    @Override
    public UUID addTodo(String name, UUID userId) {
        ArgumentChecker.checkNotEmpty(name, "Todo name");
        ArgumentChecker.checkNotNull(userId, "User Id");
        return this.todoService.addTodo(name, userId);
    }

    @Override
    public void markCompleted(UUID todoId) {
        ArgumentChecker.checkNotNull(todoId, "Todo Id");
        this.todoService.markCompleted(todoId);
    }

    @Override
    public TodoDTO findById(UUID todoId) {
        ArgumentChecker.checkNotNull(todoId, "Todo Id");
        return mapToDTO(this.todoService.findById(todoId));
    }

    @Override
    public List<TodoDTO> findAll() {
        return this.todoService.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<TodoDTO> findByUserId(UUID userId) {
        ArgumentChecker.checkNotNull(userId, "User Id");
        return this.todoService.findByUserId(userId).stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public void removeTodo(UUID todoId) {
        ArgumentChecker.checkNotNull(todoId, "Todo Id");
        this.todoService.removeTodo(todoId);
    }

    private TodoDTO mapToDTO(Todo in) {
        return new TodoDTO(in.getId(), in.getName(), in.isCompleted());
    }
}

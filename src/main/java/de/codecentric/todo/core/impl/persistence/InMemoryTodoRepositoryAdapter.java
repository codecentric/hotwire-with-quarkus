package de.codecentric.todo.core.impl.persistence;

import de.codecentric.todo.core.impl.business.Todo;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Adapter implementation for {@link TodoRepository} which holds Todos in-memory ({@link ArrayList}).
 * It is initialized with three dummy todos.
 *
 * @author Felix Riess, codecentric AG
 * @since 18 Aug 2022
 */
@ApplicationScoped
public class InMemoryTodoRepositoryAdapter implements TodoRepository {

    private final List<Todo> todoList = new ArrayList<>();

    public InMemoryTodoRepositoryAdapter() {
        this.todoList.add(new Todo("Todo"));
        this.todoList.add(new Todo("Another Todo"));
        this.todoList.add(new Todo("Yet another Todo"));
    }

    @Override
    public List<Todo> findAll() {
        return this.todoList;
    }

    @Override
    public List<Todo> findByUserId(UUID userId) {
        return this.todoList.stream()
                            .filter(todo -> todo.getUserId() != null && todo.getUserId().equals(userId))
                            .collect(Collectors.toList());
    }

    @Override
    public Optional<Todo> findById(UUID id) {
        return this.todoList.stream().filter(todo -> todo.getId().equals(id)).findFirst();
    }

    @Override
    public void remove(UUID id) {
        Optional<Todo> toBeRemoved = findById(id);
        toBeRemoved.ifPresent(this.todoList::remove);
    }

    @Override
    public UUID add(Todo todo) {
        this.todoList.add(todo);
        return todo.getId();
    }
}

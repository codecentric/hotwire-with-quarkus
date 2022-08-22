package de.codecentric.todo.core.impl.business;

import de.codecentric.todo.core.impl.persistence.TodoRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class TodoService {

    private final TodoRepository repository;

    @Inject
    TodoService(TodoRepository repository) {
        this.repository = repository;
    }

    public UUID addTodo(String name) {
        return this.repository.add(new Todo(name));
    }

    public void markCompleted(UUID todoId) {
        Todo existing = findById(todoId);
        existing.markComplete();
    }

    public Todo findById(UUID todoId) {
        Optional<Todo> existing = this.repository.findById(todoId);
        if (existing.isPresent()) {
            return existing.get();
        } else {
            throw new IllegalArgumentException("Todo with id " + todoId + " is not existing");
        }
    }

    public List<Todo> findAll() {
        return this.repository.findAll();
    }

    public void removeTodo(UUID todoId) {
        this.repository.remove(todoId);
    }
}

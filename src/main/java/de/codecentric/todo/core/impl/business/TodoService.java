package de.codecentric.todo.core.impl.business;

import de.codecentric.common.errorhandling.ErrorCode;
import de.codecentric.common.errorhandling.exception.BusinessException;
import de.codecentric.common.logging.ILogger;
import de.codecentric.todo.core.impl.persistence.TodoRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * The implementation of the business logic of a Todo.
 *
 * @author Felix Riess, codecentric AG
 * @since 18 Aug 2022
 */
@ApplicationScoped
public class TodoService {

    private static final ILogger LOG = ILogger.getLogger(TodoService.class);

    private final TodoRepository repository;

    @Inject
    TodoService(TodoRepository repository) {
        this.repository = repository;
    }

    public UUID addTodo(String name) {
        return this.repository.add(new Todo(name));
    }

    public UUID addTodo(String name, UUID userId) {
        return this.repository.add(new Todo(name, userId));
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
            LOG.warn("Todo with id {} is not existing", todoId);
            throw new BusinessException(ErrorCode.NOT_FOUND, "Todo with id is not existing", todoId);
        }
    }

    public List<Todo> findAll() {
        return this.repository.findAll();
    }

    public void removeTodo(UUID todoId) {
        this.repository.remove(todoId);
    }

    public List<Todo> findByUserId(UUID userId) {
        return this.repository.findByUserId(userId);
    }
}

package de.codecentric.todo.core.impl.access;

import de.codecentric.common.validation.ArgumentChecker;
import de.codecentric.todo.core.api.QueryUsersUseCase;
import de.codecentric.todo.core.impl.business.Todo;
import de.codecentric.todo.core.impl.business.TodoService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Facade as implementation of {@link QueryUsersUseCase}.
 *
 * @author Felix Riess, codecentric AG
 * @since 26 Aug 2022
 */
@ApplicationScoped
public class UserFacade implements QueryUsersUseCase {

    private final TodoService todoService;

    @Inject
    UserFacade(TodoService todoService) {
        this.todoService = todoService;
    }

    @Override
    public List<UUID> findAll() {
        // this implementation is pretty bad and will cause bad performance when where are many todos. No production-ready code!
        final Set<UUID> distinctUserIds = new HashSet<>();
        this.todoService.findAll().forEach(todo -> {
            if (todo.getUserId() != null) {
                distinctUserIds.add(todo.getUserId());
            }
        });
        return new ArrayList<>(distinctUserIds);
    }

    @Override
    public boolean hasUserTodo(UUID userId, UUID todoId) {
        ArgumentChecker.checkNotNull(userId, "User Id");
        ArgumentChecker.checkNotNull(todoId, "Todo Id");
        final Todo todo = this.todoService.findById(todoId);
        return todo.getUserId() != null && todo.getUserId().equals(userId);
    }
}

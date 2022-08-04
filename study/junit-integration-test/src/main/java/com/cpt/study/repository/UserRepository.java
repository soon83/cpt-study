package com.cpt.study.repository;

import com.cpt.study.domain.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class UserRepository {

    private static Long index = 1L;
    private static final List<User> users = new ArrayList<>();

    public List<User> findAll() {
        return users;
    }

    public Optional<User> findById(final Long id) {
        return users.stream()
                .filter(user -> Objects.equals(user.getId(), id))
                .findFirst();
    }

    public User save(final User user) {
        user.setId(index++);
        users.add(user);
        return user;
    }

    public void update(final Long id, final User user) {
        findById(id).ifPresent(u -> u.update(user));
    }

    public void delete(final Long id) {
        findById(id).ifPresent(users::remove);
    }

    public void clearAll() {
        index = 1L;
        users.clear();
    }
}

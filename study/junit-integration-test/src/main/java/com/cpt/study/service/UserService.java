package com.cpt.study.service;

import com.cpt.study.domain.User;
import com.cpt.study.exception.UserNotFoundException;
import com.cpt.study.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getUserList() {
        return userRepository.findAll();
    }

    public User getUser(final Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
    }

    public User createUser(final User user) {
        return userRepository.save(user);
    }

    public void updateUser(final Long userId, final User user) {
        userRepository.update(userId, user);
    }

    public void deleteUser(final Long userId) {
        userRepository.delete(userId);
    }
}

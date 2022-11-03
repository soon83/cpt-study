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

    private final NotificationService notificationService;

    private final UserRepository userRepository;

    public List<User> getUserList() {
        return userRepository.findAll();
    }

    public User getUser(final Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
    }

    public User createUser(final User user) {
        User createdUser = userRepository.save(user);

        // TODO 회원가입 성공 push
        if (createdUser.getEmail() != null && !createdUser.getEmail().isBlank()) {
            notificationService.pushEmail();
        }
        notificationService.pushSms();

        return createdUser;
    }

    public void updateUser(final Long userId, final User user) {
        userRepository.update(userId, user);
    }

    public void deleteUser(final Long userId) {
        userRepository.delete(userId);
    }
}

package com.cpt.test.service;

import com.cpt.study.domain.User;
import com.cpt.study.repository.UserRepository;
import com.cpt.study.service.NotificationService;
import com.cpt.study.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class UserServiceTest {

    //@Mock
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private NotificationService notificationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        //userService = new UserService(notificationService, userRepository);
    }

    @Test
    @DisplayName("회원가입 시 이메일이 있는 경우 이메일 푸시가 가면 성공")
    public void test001() {
        // given
        User user = User.builder()
                .name("하츄핑")
                .age(10)
                .gender(User.Gender.MALE)
                .email("test@email.com")
                .build();

        User createdUser = User.builder()
                .id(1L)
                .name("하츄핑")
                .age(20)
                .gender(User.Gender.MALE)
                .email("test@email.com")
                .build();

        given(userRepository.save(any(User.class)))
                .willReturn(createdUser);

        // when
        userService.createUser(user);

        // then
        verify(userRepository, times(1)).save(user);
        verify(notificationService, times(1)).pushEmail();
        verify(notificationService, times(1)).pushSms();
    }

    @Test
    @DisplayName("회원가입 시 이메일이 없는 경우 이메일 푸시 호출되지 않으면 성공")
    public void test002() {
        // given
        User user = User.builder()
                .name("하츄핑")
                .age(10)
                .gender(User.Gender.MALE)
                .build();

        User createdUser = User.builder()
                .id(1L)
                .name("하츄핑")
                .age(10)
                .gender(User.Gender.MALE)
                .build();
        given(userRepository.save(any(User.class)))
                .willReturn(createdUser);

        // when
        userService.createUser(user);

        // then
        verify(notificationService, times(0)).pushEmail();
        verify(notificationService, times(1)).pushSms();
    }
}
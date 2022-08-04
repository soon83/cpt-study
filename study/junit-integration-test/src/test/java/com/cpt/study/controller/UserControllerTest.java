package com.cpt.study.controller;

import com.cpt.study.domain.User;
import com.cpt.study.model.UserDto;
import com.cpt.study.repository.UserRepository;
import com.cpt.study.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.io.IOException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {

    private WebTestClient webTestClient;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @LocalServerPort
    private Integer port;

    @BeforeEach
    public void setUpServer() throws IOException {
        webTestClient = WebTestClient.bindToServer()
                .baseUrl("http://127.0.0.1:" + port)
                .build();
    }

    @BeforeEach
    public void setUpBefore() throws IOException {
        // given
        UserDto.CreateRequest request1 = UserDto.CreateRequest.builder()
                .userName("하츄핑")
                .userAge(10)
                .userEmail("chu@email.com")
                .userGender(User.Gender.FEMALE)
                .build();
        UserDto.CreateRequest request2 = UserDto.CreateRequest.builder()
                .userName("믿어핑")
                .userAge(11)
                .userEmail("believe@email.com")
                .userGender(User.Gender.MALE)
                .build();
        UserDto.CreateRequest request3 = UserDto.CreateRequest.builder()
                .userName("방글핑")
                .userAge(10)
                .userEmail("bangle@email.com")
                .userGender(User.Gender.FEMALE)
                .build();

        userService.createUser(request1.toUser());
        userService.createUser(request2.toUser());
        userService.createUser(request3.toUser());
    }

    @Test
    @DisplayName("[사용자 목록 조회] 정상적으로 조회하는 케이스")
    public void test01() {
        // when
        WebTestClient.ResponseSpec exchange = webTestClient.get().uri("/api/v1/users")
                .accept(MediaType.APPLICATION_JSON)
                .exchange();

        // then
        exchange
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("result").isEqualTo("SUCCESS")
                .jsonPath("success").isEqualTo(true)
                .jsonPath("data").isMap()
                .jsonPath("data.content[0].userId").exists()
                .jsonPath("data.content[0].userId").isEqualTo(1)
                .jsonPath("data.content[0].userName").isEqualTo("하츄핑")
                .jsonPath("data.content[0].userAge").isEqualTo(10)
                .jsonPath("data.content[0].userEmail").isEqualTo("chu@email.com")
                .jsonPath("data.content[0].userGender").isEqualTo("FEMALE")
                .jsonPath("data.content[1].userId").exists()
                .jsonPath("data.content[2].userId").exists()
        ;
    }

    @Test
    @DisplayName("[사용자 단건 조회] 정상적으로 조회하는 케이스")
    public void test02() {
        // when
        WebTestClient.ResponseSpec exchange = webTestClient.get().uri("/api/v1/users/{userId}", 2)
                .accept(MediaType.APPLICATION_JSON)
                .exchange();

        // then
        exchange
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("result").isEqualTo("SUCCESS")
                .jsonPath("success").isEqualTo(true)
                .jsonPath("data").isMap()
                .jsonPath("data.userId").isEqualTo(2)
                .jsonPath("data.userName").isEqualTo("믿어핑")
                .jsonPath("data.userAge").isEqualTo(11)
                .jsonPath("data.userEmail").isEqualTo("believe@email.com")
                .jsonPath("data.userGender").isEqualTo("MALE")
        ;
    }

    @Test
    @DisplayName("[사용자 단건 조회] 없는 회원을 조회하는 케이스")
    public void test03() {
        // when
        WebTestClient.ResponseSpec exchange = webTestClient.get().uri("/api/v1/users/{userId}", 99999L)
                .accept(MediaType.APPLICATION_JSON)
                .exchange();

        // then
        exchange
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("result").isEqualTo("FAILURE")
                .jsonPath("success").isEqualTo(false)
        ;
    }

    @Test
    @DisplayName("[사용자 단건 등록] 정상적으로 등록하는 케이스")
    public void test04() {
        // given
        UserDto.CreateRequest request = UserDto.CreateRequest.builder()
                .userName("악동핑")
                .userAge(100)
                .userEmail("acdong@email.com")
                .userGender(User.Gender.MALE)
                .build();

        // when
        WebTestClient.ResponseSpec exchange = webTestClient.post().uri("/api/v1/users")
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .exchange();

        // then
        exchange
                .expectStatus().isCreated()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("result").isEqualTo("SUCCESS")
                .jsonPath("success").isEqualTo(true)
                .jsonPath("data.userId").exists()
        ;
    }

    @Test
    @DisplayName("[사용자 단건 등록] 필수 파라미터를 누락한 케이스")
    public void test05() {
        // given
        UserDto.CreateRequest request = UserDto.CreateRequest.builder()
                .userAge(100)
                .userEmail("acdong@email.com")
                .userGender(User.Gender.MALE)
                .build();

        // when
        WebTestClient.ResponseSpec exchange = webTestClient.post().uri("/api/v1/users")
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .exchange();

        // then
        exchange
                .expectStatus().isBadRequest()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("result").isEqualTo("FAILURE")
                .jsonPath("success").isEqualTo(false)
        ;
    }

    @AfterEach
    public void setUpAfter() {
        userRepository.clearAll();
    }
}

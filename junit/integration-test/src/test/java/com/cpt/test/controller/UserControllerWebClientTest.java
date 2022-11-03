package com.cpt.test.controller;

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
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.io.IOException;
import java.util.Map;

import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerWebClientTest {

    private WebTestClient webTestClient;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @LocalServerPort
    private Integer port;

    @BeforeEach
    public void setUpServer() throws IOException {
        this.webTestClient = WebTestClient.bindToServer()
                .baseUrl("http://127.0.0.1:" + port)
                .build();
    }

    @BeforeEach
    public void setUpBefore() throws IOException {
        userService.createUser(UserDto.CreateRequest.builder()
                .userName("하츄핑").userAge(10).userEmail("chu@email.com").userGender(User.Gender.FEMALE)
                .build()
                .toUser());
        userService.createUser(UserDto.CreateRequest.builder()
                .userName("믿어핑").userAge(11).userEmail("believe@email.com").userGender(User.Gender.MALE)
                .build()
                .toUser());
        userService.createUser(UserDto.CreateRequest.builder()
                .userName("방글핑").userAge(10).userEmail("bangle@email.com").userGender(User.Gender.FEMALE)
                .build().toUser());
    }

    @AfterEach
    public void setUpAfter() {
        userRepository.clearAll();
    }

    @Test
    @DisplayName("[사용자 목록 조회] 정상 조회")
    public void test01() {
        // when
        WebTestClient.ResponseSpec exchange = webTestClient.get().uri("/api/v1/users")
                .accept(MediaType.APPLICATION_JSON)
                .exchange();

        // then
        exchange
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody().consumeWith(System.out::println)
                .jsonPath("$.result").value(is("SUCCESS"))
                .jsonPath("$.success").value(is(true))
                .jsonPath("$.data").value(isA(Map.class))
                .jsonPath("$.data.content").value(isA(Iterable.class))
                .jsonPath("$.data.content[0].userId").value(isA(Number.class))
                .jsonPath("$.data.content[0].userId").value(is(1))
                .jsonPath("$.data.content[0].userName").value(is("하츄핑"))
                .jsonPath("$.data.content[0].userAge").value(is(10))
                .jsonPath("$.data.content[0].userEmail").value(is("chu@email.com"))
                .jsonPath("$.data.content[0].userGender").value(is("FEMALE"))
                .jsonPath("$.data.content[1]").exists()
                .jsonPath("$.data.content[2]").exists()
        ;
    }

    @Test
    @DisplayName("[사용자 단건 조회] 정상 조회")
    public void test02() {
        // when
        WebTestClient.ResponseSpec exchange = webTestClient.get().uri("/api/v1/users/{userId}", 2)
                .accept(MediaType.APPLICATION_JSON)
                .exchange();

        // then
        exchange
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody().consumeWith(System.out::println)
                .jsonPath("$.result").value(is("SUCCESS"))
                .jsonPath("$.success").value(is(true))
                .jsonPath("$.data").value(isA(Map.class))
                .jsonPath("$.data.userId").value(isA(Number.class))
                .jsonPath("$.data.userId").value(is(2))
                .jsonPath("$.data.userName").value(is("믿어핑"))
                .jsonPath("$.data.userAge").value(is(11))
                .jsonPath("$.data.userEmail").value(is("believe@email.com"))
                .jsonPath("$.data.userGender").value(is("MALE"))
        ;
    }

    @Test
    @DisplayName("[사용자 단건 조회] 존재하지않는 회원 조회")
    public void test03() {
        // when
        WebTestClient.ResponseSpec exchange = webTestClient.get().uri("/api/v1/users/{userId}", 99999L)
                .accept(MediaType.APPLICATION_JSON)
                .exchange();

        // then
        exchange
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody().consumeWith(System.out::println)
                .jsonPath("$.result").value(is("FAILURE"))
                .jsonPath("$.success").value(is(false))
                .jsonPath("$.data.errorCode").value(is("U101"))
        ;
    }

    @Test
    @DisplayName("[사용자 단건 등록] 정상 등록")
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
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .exchange();

        // then
        exchange
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody().consumeWith(System.out::println)
                .jsonPath("$.result").value(is("SUCCESS"))
                .jsonPath("$.success").value(is(true))
                .jsonPath("$.data.userId").exists()
        ;
    }

    @Test
    @DisplayName("[사용자 단건 등록] 필수 파라미터 누락")
    public void test05() {
        // given
        UserDto.CreateRequest request = UserDto.CreateRequest.builder()
                .build();

        // when
        WebTestClient.ResponseSpec exchange = webTestClient.post().uri("/api/v1/users")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.CONTENT_TYPE, String.valueOf(MediaType.APPLICATION_JSON))
                .body(BodyInserters.fromValue(request))
                .exchange();

        // then
        exchange
                .expectStatus().isBadRequest()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody().consumeWith(System.out::println)
                .jsonPath("$.result").value(is("FAILURE"))
                .jsonPath("$.success").value(is(false))
                .jsonPath("$.data.errors").value(hasSize(4))
        ;
    }

    @Test
    @DisplayName("[사용자 단건 등록] invalid 파라미터, userAge, userEmail")
    public void test06() throws Exception {
        // given
        UserDto.CreateRequest request = UserDto.CreateRequest.builder()
                .userName("하츄핑")
                .userAge(-1)
                .userEmail("email.com")
                .userGender(User.Gender.MALE)
                .build();

        // when
        WebTestClient.ResponseSpec exchange = webTestClient.post().uri("/api/v1/users")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .exchange();

        // then
        exchange
                .expectStatus().isBadRequest()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody().consumeWith(System.out::println)
                .jsonPath("$.result").value(is("FAILURE"))
                .jsonPath("$.success").value(is(false))
                .jsonPath("$.data.errorCode").value(is("C002"))
                .jsonPath("$.data.errors").value(hasSize(2))
                .jsonPath("$.data.errors[?(@.field == 'userAge')]").exists()
                .jsonPath("$.data.errors[?(@.field == 'userEmail')]").exists()
        ;
    }
}

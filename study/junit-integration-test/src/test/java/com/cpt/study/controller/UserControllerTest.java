package com.cpt.study.controller;

import com.cpt.study.domain.User;
import com.cpt.study.model.UserDto;
import com.cpt.study.repository.UserRepository;
import com.cpt.study.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.reactive.function.BodyInserters;

import java.io.IOException;
import java.util.LinkedHashMap;

import static org.hamcrest.Matchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {

    private WebTestClient webTestClient;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @LocalServerPort
    private Integer port;

    @BeforeEach
    public void setUpServer(WebApplicationContext webApplicationContext) throws IOException {
        this.webTestClient = WebTestClient.bindToServer()
                .baseUrl("http://127.0.0.1:" + port)
                .build();

        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
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
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.result").isEqualTo("SUCCESS")
                .jsonPath("$.success").isEqualTo(true)
                .jsonPath("$.data").isMap()
                .jsonPath("$.data.content[0].userId").exists()
                .jsonPath("$.data.content[0].userId").isEqualTo(1)
                .jsonPath("$.data.content[0].userName").isEqualTo("하츄핑")
                .jsonPath("$.data.content[0].userAge").isEqualTo(10)
                .jsonPath("$.data.content[0].userEmail").isEqualTo("chu@email.com")
                .jsonPath("$.data.content[0].userGender").isEqualTo("FEMALE")
                .jsonPath("$.data.content[1].userId").exists()
                .jsonPath("$.data.content[2].userId").exists()
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
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.result").isEqualTo("SUCCESS")
                .jsonPath("$.success").isEqualTo(true)
                .jsonPath("$.data").isMap()
                .jsonPath("$.data.userId").isEqualTo(2)
                .jsonPath("$.data.userName").isEqualTo("믿어핑")
                .jsonPath("$.data.userAge").isEqualTo(11)
                .jsonPath("$.data.userEmail").isEqualTo("believe@email.com")
                .jsonPath("$.data.userGender").isEqualTo("MALE")
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
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.result").isEqualTo("FAILURE")
                .jsonPath("$.success").isEqualTo(false)
                .jsonPath("$.data.errorCode").isEqualTo("U101")
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
                .body(BodyInserters.fromValue(request))
                .exchange();

        // then
        exchange
                .expectStatus().isCreated()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.result").isEqualTo("SUCCESS")
                .jsonPath("$.success").isEqualTo(true)
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
                .body(BodyInserters.fromValue(request))
                .exchange();

        // then
        exchange
                .expectStatus().isBadRequest()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.result").isEqualTo("FAILURE")
                .jsonPath("$.success").isEqualTo(false)
                .jsonPath("$.data.errors.length()").isEqualTo(4)
        ;
    }

    @Test
    @DisplayName("[사용자 단건 등록] userAge, userEmail 올바른 포맷이 아님")
    public void test06() throws Exception {
        // given
        UserDto.CreateRequest request = UserDto.CreateRequest.builder()
                .userName("하츄핑")
                .userAge(-1)
                .userEmail("email.com")
                .userGender(User.Gender.MALE)
                .build();

        // when
        ResultActions action = mockMvc.perform(post("/api/v1/users")
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        action
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.result", is("FAILURE")))
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.data.errorCode", is("C002")))
                .andExpect(jsonPath("$.data.errors", hasSize(2)))
                .andExpect(jsonPath("$.data.errors[?(@.field == 'userAge')]").exists())
                .andExpect(jsonPath("$.data.errors[?(@.field == 'userEmail')]").exists())
        ;

        // when
        /*WebTestClient.ResponseSpec exchange = webTestClient.post().uri("/api/v1/users")
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .exchange();*/

        // then
        /*String responseBody = exchange
                //.expectStatus().isBadRequest()
                .expectBody(String.class)
                .returnResult()
                .getResponseBody();
        System.out.println("responseBody = " + responseBody);*/
    }
}

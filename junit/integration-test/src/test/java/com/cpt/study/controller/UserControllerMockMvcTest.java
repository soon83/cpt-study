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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class UserControllerMockMvcTest {

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUpMock(WebApplicationContext webApplicationContext) throws IOException {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @BeforeEach
    public void setUpBefore() throws IOException {
        userService.createUser(UserDto.CreateRequest.builder()
                .userName("νμΈν").userAge(10).userEmail("chu@email.com").userGender(User.Gender.FEMALE)
                .build()
                .toUser());
        userService.createUser(UserDto.CreateRequest.builder()
                .userName("λ―Ώμ΄ν").userAge(11).userEmail("believe@email.com").userGender(User.Gender.MALE)
                .build()
                .toUser());
        userService.createUser(UserDto.CreateRequest.builder()
                .userName("λ°©κΈν").userAge(10).userEmail("bangle@email.com").userGender(User.Gender.FEMALE)
                .build().toUser());
    }

    @AfterEach
    public void setUpAfter() {
        userRepository.clearAll();
    }

    @Test
    @DisplayName("[μ¬μ©μ λͺ©λ‘ μ‘°ν] μ μ μ‘°ν")
    public void test01() throws Exception {
        // when
        ResultActions action = mockMvc.perform(get("/api/v1/users")
                .accept(MediaType.APPLICATION_JSON));

        // then
        action
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", is("SUCCESS")))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data", isA(Map.class)))
                .andExpect(jsonPath("$.data.content", isA(Iterable.class)))
                .andExpect(jsonPath("$.data.content[0].userId", isA(Number.class)))
                .andExpect(jsonPath("$.data.content[0].userId", is(1)))
                .andExpect(jsonPath("$.data.content[0].userName", is("νμΈν")))
                .andExpect(jsonPath("$.data.content[0].userAge", is(10)))
                .andExpect(jsonPath("$.data.content[0].userEmail", is("chu@email.com")))
                .andExpect(jsonPath("$.data.content[0].userGender", is("FEMALE")))
                .andExpect(jsonPath("$.data.content[1]").exists())
                .andExpect(jsonPath("$.data.content[2]").exists())
        ;
    }

    @Test
    @DisplayName("[μ¬μ©μ λ¨κ±΄ μ‘°ν] μ μ μ‘°ν")
    public void test02() throws Exception {
        // when
        ResultActions action = mockMvc.perform(get("/api/v1/users/{userId}", 2)
                .accept(MediaType.APPLICATION_JSON));

        // then
        action
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", is("SUCCESS")))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data", isA(Map.class)))
                .andExpect(jsonPath("$.data.userId", isA(Number.class)))
                .andExpect(jsonPath("$.data.userId", is(2)))
                .andExpect(jsonPath("$.data.userName", is("λ―Ώμ΄ν")))
                .andExpect(jsonPath("$.data.userAge", is(11)))
                .andExpect(jsonPath("$.data.userEmail", is("believe@email.com")))
                .andExpect(jsonPath("$.data.userGender", is("MALE")))
        ;
    }

    @Test
    @DisplayName("[μ¬μ©μ λ¨κ±΄ μ‘°ν] μ‘΄μ¬νμ§μλ νμ μ‘°ν")
    public void test03() throws Exception {
        // when
        ResultActions action = mockMvc.perform(get("/api/v1/users/{userId}", 99999L)
                .accept(MediaType.APPLICATION_JSON));

        // then
        action
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", is("FAILURE")))
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.data.errorCode", is("U101")))
        ;
    }

    @Test
    @DisplayName("[μ¬μ©μ λ¨κ±΄ λ±λ‘] μ μ λ±λ‘")
    public void test04() throws Exception {
        // given
        UserDto.CreateRequest request = UserDto.CreateRequest.builder()
                .userName("μλν")
                .userAge(100)
                .userEmail("acdong@email.com")
                .userGender(User.Gender.MALE)
                .build();

        // when
        ResultActions action = mockMvc.perform(post("/api/v1/users")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        // then
        action
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.result", is("SUCCESS")))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data.userId").exists())
        ;
    }

    @Test
    @DisplayName("[μ¬μ©μ λ¨κ±΄ λ±λ‘] νμ νλΌλ―Έν° λλ½")
    public void test05() throws Exception {
        // given
        UserDto.CreateRequest request = UserDto.CreateRequest.builder()
                .build();

        // when
        ResultActions action = mockMvc.perform(post("/api/v1/users")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        // then
        action
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.result", is("FAILURE")))
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.data.errors", hasSize(4)))
        ;
    }

    @Test
    @DisplayName("[μ¬μ©μ λ¨κ±΄ λ±λ‘] invalid νλΌλ―Έν°, userAge, userEmail")
    public void test06() throws Exception {
        // given
        UserDto.CreateRequest request = UserDto.CreateRequest.builder()
                .userName("νμΈν")
                .userAge(-1)
                .userEmail("email.com")
                .userGender(User.Gender.MALE)
                .build();

        // when
        ResultActions action = mockMvc.perform(post("/api/v1/users")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

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
    }
}

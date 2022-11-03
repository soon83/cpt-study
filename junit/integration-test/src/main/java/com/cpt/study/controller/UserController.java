package com.cpt.study.controller;

import com.cpt.study.common.Res;
import com.cpt.study.domain.User;
import com.cpt.study.model.UserDto;
import com.cpt.study.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity getUsers() {
        List<User> userList = userService.getUserList();
        List<UserDto.MainResponse> response = userList.stream()
                .map(UserDto.MainResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(Res.success(Map.of("content", response)));
    }

    @GetMapping("/{userId}")
    public ResponseEntity getUser(@PathVariable Long userId) {
        User user = userService.getUser(userId);
        UserDto.MainResponse response = new UserDto.MainResponse(user);
        return ResponseEntity.ok(Res.success(response));
    }

    @PostMapping
    public ResponseEntity createUser(@RequestBody @Valid UserDto.CreateRequest request) throws URISyntaxException {
        User user = request.toUser();
        User createdUser = userService.createUser(user);
        UserDto.CreateResponse response = new UserDto.CreateResponse(createdUser);
        return ResponseEntity.created(URI.create("/api/v1/users/" + response.getUserId())).body(Res.success(response));
    }

    @PutMapping("/{userId}")
    public ResponseEntity updateUser(@PathVariable Long userId, @RequestBody @Valid UserDto.UpdateRequest request) {
        User user = request.toUser();
        userService.updateUser(userId, user);
        return ResponseEntity.ok(Res.success());
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok(Res.success());
    }
}

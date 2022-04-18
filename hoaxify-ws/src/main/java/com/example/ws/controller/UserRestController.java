package com.example.ws.controller;

import com.example.ws.dto.request.GenericUserRequest;
import com.example.ws.dto.response.GenericUserResponse;
import com.example.ws.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;

@RequestMapping("/users")
@RequestScope
@RestController
@CrossOrigin
@Validated
@RequiredArgsConstructor
public class UserRestController {
    private final UserService userService;


    @GetMapping("{username}")
    public GenericUserResponse getUserByUsername(
            @PathVariable String username) {
        return userService.findByUsername(username);
    }

    @GetMapping()
    public List<GenericUserResponse> getUsersByPage(
            @RequestParam(required = false, defaultValue = "0")
            int pageNo,
            @RequestParam(required = false, defaultValue = "20")
            int pageSize) {
        return userService.findAllUsers(pageNo, pageSize);
    }

    @PostMapping()
    public GenericUserResponse addNewUser(
            @RequestBody @Validated GenericUserRequest request) {
        return userService.createNewUser(request);
    }

    @PutMapping(value = "{username}")
    public GenericUserResponse updateUser(
            @PathVariable String username,
            @RequestBody @Validated GenericUserRequest request) {
        return userService.updateUser(username, request);
    }

    @DeleteMapping("{username}")
    public GenericUserResponse deleteUserByUsername(
            @PathVariable String username) {
        return userService.removeByUsername(username);
    }
}

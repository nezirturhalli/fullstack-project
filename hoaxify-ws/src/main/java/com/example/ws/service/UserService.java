package com.example.ws.service;

import com.example.ws.dto.request.GenericUserRequest;
import com.example.ws.dto.response.GenericUserResponse;

import java.util.List;

public interface UserService {

    GenericUserResponse findByUsername(String username);

    List<GenericUserResponse> findAllUsers(int pageNo, int pageSize);


    GenericUserResponse createNewUser(GenericUserRequest request);


    GenericUserResponse updateUser(String username, GenericUserRequest request);


    GenericUserResponse removeByUsername(String username);

}

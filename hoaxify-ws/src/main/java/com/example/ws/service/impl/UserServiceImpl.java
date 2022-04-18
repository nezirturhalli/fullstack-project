package com.example.ws.service.impl;

import com.example.ws.dto.request.GenericUserRequest;
import com.example.ws.dto.response.GenericUserResponse;
import com.example.ws.entity.User;
import com.example.ws.exception.UserNotFoundException;
import com.example.ws.repository.UserRepository;
import com.example.ws.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    public GenericUserResponse findByUsername(String username) {

        return modelMapper.map(
                userRepository.findByUsername(username)
                        .orElseThrow(() -> new UserNotFoundException("User not found!"))
                , GenericUserResponse.class
        );
    }

    @Override
    public List<GenericUserResponse> findAllUsers(int pageNo, int pageSize) {
        return userRepository.findAll(PageRequest.of(pageNo, pageSize))
                .stream()
                .map(user -> modelMapper.map(user, GenericUserResponse.class))
                .sorted(Comparator.comparing(GenericUserResponse::getUsername))
                .collect(Collectors.toList());
    }

    @Override
    public GenericUserResponse createNewUser(GenericUserRequest request) {
        User user = modelMapper.map(request, User.class);
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        return modelMapper.map(userRepository.save(user), GenericUserResponse.class);
    }

    @Override
    public GenericUserResponse updateUser(String username, GenericUserRequest request) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found!"));
        modelMapper.map(request, user);
        return modelMapper.map(userRepository.saveAndFlush(user), GenericUserResponse.class);
    }

    @Override
    public GenericUserResponse removeByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found!"));
        userRepository.deleteByUsername(username);
        return modelMapper.map(user, GenericUserResponse.class);
    }
}

package com.example.ws.config;


import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {


    @Bean("standardModelMapper")
    ModelMapper createModelMapper() {
        return new ModelMapper();
    }
}

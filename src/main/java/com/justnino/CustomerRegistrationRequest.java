package com.justnino;

public record CustomerRegistrationRequest(
        String name,
        String email,
        Integer age
){}

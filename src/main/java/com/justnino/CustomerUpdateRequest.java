package com.justnino;

public record CustomerUpdateRequest(
        String name,
        String email,
        Integer age
) {
}

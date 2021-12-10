package com.dots.crypto.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class JwtDTO {
    private final String token;
    private final String username;
    private final List<String> roles;
}

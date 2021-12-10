package com.dots.crypto.controller;

import com.dots.crypto.controller.dto.JwtDTO;
import com.dots.crypto.controller.dto.LoginDTO;
import com.dots.crypto.controller.dto.SignupDTO;
import com.dots.crypto.model.APIRole;
import com.dots.crypto.model.APIUser;
import com.dots.crypto.model.RoleEntity;
import com.dots.crypto.repository.APIUserRepository;
import com.dots.crypto.repository.RoleRepository;
import com.dots.crypto.service.auth.details.UserDetailsImpl;
import com.dots.crypto.service.auth.token.TokenGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor(onConstructor_= @Autowired)
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final APIUserRepository apiUserRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenGenerator tokenGenerator;

    @PostMapping("/generateToken")
    public JwtDTO generateToken(@RequestBody LoginDTO loginDTO) {
        final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String jwt = tokenGenerator.generateJwtToken(authentication);
        final UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        final List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        log.info("Successfully generate token for user {}", userDetails.getUsername());
        return new JwtDTO(jwt, userDetails.getUsername(), roles);
    }

    @PostMapping("/register")
    public String register(@RequestBody SignupDTO signupDTO) {
        if (apiUserRepository.existsByUsername(signupDTO.getUsername())) {
            return "Error: Username is already taken!";
        }

        final APIUser user = new APIUser();

        user.setUsername(signupDTO.getUsername());
        user.setPassword(passwordEncoder.encode(signupDTO.getPassword()));
        user.setRoles(Collections.singleton(roleRepository.findOrSave(0, roleRepository, () -> {
            final RoleEntity roleEntity = new RoleEntity();
            roleEntity.setName(APIRole.ROLE_ADMIN);
            return roleEntity;
        })));

        apiUserRepository.save(user);

        log.info("Successfully registered user {}", user);
        return "User registered successfully!";
    }
}

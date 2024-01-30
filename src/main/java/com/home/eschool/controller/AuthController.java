package com.home.eschool.controller;

import com.home.eschool.entity.Users;
import com.home.eschool.models.dto.CreateUserDto;
import com.home.eschool.models.dto.LoginDto;
import com.home.eschool.models.dto.PasswordUpdateDto;
import com.home.eschool.models.dto.UserAvatarDto;
import com.home.eschool.models.payload.LoginPayload;
import com.home.eschool.models.payload.UserPayload;
import com.home.eschool.security.jwt.JwtTokenProvider;
import com.home.eschool.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "auth")
@CrossOrigin(origins="*")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginPayload> login(@RequestBody LoginDto loginDto) {

        try {
            String username = loginDto.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, loginDto.getPassword()));
            Users user = userService.findUserByLogin(username);

            if (user == null) {
                throw new UsernameNotFoundException("User with username: " + username + " not found");
            }

            String token = jwtTokenProvider.generateAccessToken(user);

            return ResponseEntity.ok(
                    new LoginPayload(token,
                            new UserPayload(
                                    user.getId(),
                                    user.getLogin(),
                                    user.getFullName(),
                                    user.getRole().getLabel(),
                                    user.getRole().getName())));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).build();
        }
    }

    @PostMapping("/updatePassword")
    public void updatePassword(@Valid @RequestBody PasswordUpdateDto dto) {
        userService.updatePassword(dto);
    }

    @PostMapping("/createUser")
    public void createUser(@Valid @RequestBody CreateUserDto dto) {
        userService.createUser(dto);
    }
}

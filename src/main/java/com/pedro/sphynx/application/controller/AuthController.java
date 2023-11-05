package com.pedro.sphynx.application.controller;

import com.pedro.sphynx.application.dtos.auth.UserDataLoginInputDTO;
import com.pedro.sphynx.application.dtos.auth.UserDataOutputLoginDTO;
import com.pedro.sphynx.application.dtos.auth.UserDataVerifyInputDTO;
import com.pedro.sphynx.application.dtos.auth.UserDataVerifyOutput;
import com.pedro.sphynx.domain.TokenService;
import com.pedro.sphynx.infrastructure.entities.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RequestMapping("login")
public class AuthController {
    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity login(@RequestBody @Valid UserDataLoginInputDTO data){
        var token = new UsernamePasswordAuthenticationToken(data.user(), data.password());
        var auth = manager.authenticate(token);
        var jwtToken = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new UserDataOutputLoginDTO(jwtToken));
    }

    @PostMapping("/verify")
    public ResponseEntity verify(@RequestBody @Valid UserDataVerifyInputDTO data){
        boolean valid = tokenService.isValid(data.token());

        return ResponseEntity.ok(new UserDataVerifyOutput(data.token(), valid));
    }
}

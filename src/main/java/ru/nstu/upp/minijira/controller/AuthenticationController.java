package ru.nstu.upp.minijira.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nstu.upp.minijira.dto.SignInRequestDto;
import ru.nstu.upp.minijira.dto.SignInResponseDto;
import ru.nstu.upp.minijira.dto.SignUpRequestDto;
import ru.nstu.upp.minijira.dto.SignUpResponseDto;
import ru.nstu.upp.minijira.service.AuthenticationService;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final String SIGN_IN = "/sign-in";
    private final String SIGN_UP = "/sign-up/{inviteCode}";

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping(SIGN_IN)
    public ResponseEntity<SignInResponseDto> signIn(@RequestBody SignInRequestDto requestDto) {
        return ResponseEntity.ok(authenticationService.signIn(requestDto));
    }

    @PostMapping(SIGN_UP)
    public ResponseEntity<SignUpResponseDto> signUp(
            @RequestBody @Valid SignUpRequestDto requestDto,
            @PathVariable(name = "inviteCode", required = false) String inviteCode
    ) {
        return ResponseEntity.ok(authenticationService.signUp(requestDto, inviteCode));
    }
}

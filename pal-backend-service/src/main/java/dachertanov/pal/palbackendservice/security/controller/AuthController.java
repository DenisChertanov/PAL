package dachertanov.pal.palbackendservice.security.controller;

import dachertanov.pal.palbackenddto.user.UserInfoInDto;
import dachertanov.pal.palbackenddto.user.UserInfoOutDto;
import dachertanov.pal.palbackendservice.security.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/public/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;

    @GetMapping("/signin")
    public ResponseEntity<String> signIn(@RequestHeader("Authorization") String authHeader) {
        return ResponseEntity.ok(authService.signIn(authHeader));
    }

    @PostMapping("/signup")
    public ResponseEntity<UserInfoOutDto> signUp(@Valid @RequestBody UserInfoInDto userInfoInDto) {
        return ResponseEntity.ok(authService.signUp(userInfoInDto));
    }

    @PostMapping("/signup-admin")
    public ResponseEntity<UserInfoOutDto> signUpAdmin(@Valid @RequestBody UserInfoInDto userInfoInDto) {
        return ResponseEntity.ok(authService.signUpAdmin(userInfoInDto));
    }
}

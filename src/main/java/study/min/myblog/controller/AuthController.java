package study.min.myblog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.min.myblog.data.dto.AuthenticationRequestDto;
import study.min.myblog.data.dto.AuthenticationResponseDto;
import study.min.myblog.data.dto.RegisterRequestDto;
import study.min.myblog.data.entity.UserEntity;
import study.min.myblog.jwt.JwtUtil;
import study.min.myblog.service.UserService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userServiceImpl;

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    // 로그인
    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequestDto authenticationRequest) {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("이메일 또는 비밀번호가 틀렸습니다", e);
        }

        final UserDetails userDetails = userDetailsService
            .loadUserByUsername(authenticationRequest.getEmail());

        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponseDto(jwt));
    }

    // 새로운 회원가입 엔드포인트
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequestDto registerRequest) {
        UserEntity registeredUser = userServiceImpl.registerUser(registerRequest);

        // 회원가입 성공 시, 예를 들어 성공 메시지나 사용자 ID 반환
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully with ID: " + registeredUser.getId());
    }
}

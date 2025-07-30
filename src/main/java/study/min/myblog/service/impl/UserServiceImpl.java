package study.min.myblog.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.min.myblog.common.exception.EmailExistException;
import study.min.myblog.dao.UserDao;
import study.min.myblog.data.dto.RegisterRequestDto;
import study.min.myblog.data.entity.UserEntity;
import study.min.myblog.service.UserService;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder; // SecurityConfig에서 Bean으로 등록한 PasswordEncoder

    @Transactional
    public UserEntity registerUser(RegisterRequestDto request) {
        // 1. 이메일 중복 확인
        if (userDao.existsByEmail(request.getEmail())) {
            throw new EmailExistException("이미 존재하는 이메일입니다: " + request.getEmail());
        }

        // 2. 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // 3. UserEntity 생성 및 저장
        UserEntity newUser = UserEntity.builder()
            .email(request.getEmail())
            .username(request.getUsername())
            .password(encodedPassword)
            .roles("ROLE_USER") // 기본 역할 부여. 여러 역할이라면 콤마로 구분된 문자열
            .build();

        return userDao.save(newUser);
    }
}

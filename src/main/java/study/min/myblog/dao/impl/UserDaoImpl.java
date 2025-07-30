package study.min.myblog.dao.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import study.min.myblog.dao.UserDao;
import study.min.myblog.data.entity.UserEntity;
import study.min.myblog.repository.UserRepository;

@Slf4j
@Component
@Repository
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {

    private final UserRepository userRepository;

    @Override
    public UserEntity save(UserEntity user) {
        return userRepository.save(user);
    }

    @Override
    public UserEntity findByEmail(String email) {

        UserEntity userEntity = userRepository.findByEmail(email).orElseThrow(
            () -> new UsernameNotFoundException("등록되지 않은 이메일입니다: " + email)
        );

        return userEntity;
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}

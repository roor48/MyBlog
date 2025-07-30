package study.min.myblog.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import study.min.myblog.data.CustomUserDetails;
import study.min.myblog.data.entity.UserEntity;
import study.min.myblog.dao.UserDao;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserDao userDao; // UserEntity를 가져올 Repository 또는 DAO

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // 데이터베이스에서 이메일을 통해 UserEntity를 조회
        UserEntity userEntity = userDao.findByEmail(email);

        // 조회된 UserEntity를 CustomUserDetails로 변환하여 반환
        return new CustomUserDetails(userEntity);
    }
}

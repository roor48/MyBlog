package study.min.myblog.data;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.Arrays; // roles가 콤마로 구분된 문자열일 경우
import study.min.myblog.data.entity.UserEntity;

public class CustomUserDetails implements UserDetails {

    private final UserEntity userEntity;

    public CustomUserDetails(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // UserEntity의 roles 필드(콤마로 구분된 문자열 예: "ROLE_USER,ROLE_ADMIN")를
        // GrantedAuthority 객체 리스트로 변환
        return Arrays.stream(userEntity.getRoles().split(","))
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());

        // 만약 roles 필드가 단일 역할 문자열이라면
        // return Collections.singletonList(new SimpleGrantedAuthority(userEntity.getRoles()));
    }

    @Override
    public String getPassword() {
        return userEntity.getPassword();
    }

    @Override
    public String getUsername() {
        // Spring Security의 UserDetails에서 username은 사용자를 식별하는 고유 값이어야 합니다.
        return userEntity.getEmail();
    }

    // 계정 만료 여부 (true: 만료되지 않음)
    @Override
    public boolean isAccountNonExpired() {
        return true; // 여기서는 항상 true로 설정. 실제 비즈니스 로직에 따라 변경.
    }

    // 계정 잠금 여부 (true: 잠금되지 않음)
    @Override
    public boolean isAccountNonLocked() {
        return true; // 여기서는 항상 true로 설정. 실제 비즈니스 로직에 따라 변경.
    }

    // 자격 증명(비밀번호) 만료 여부 (true: 만료되지 않음)
    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 여기서는 항상 true로 설정. 실제 비즈니스 로직에 따라 변경.
    }

    // 계정 활성화 여부 (true: 활성화됨)
    @Override
    public boolean isEnabled() {
        return true; // 여기서는 항상 true로 설정. 실제 비즈니스 로직에 따라 변경.
    }
}

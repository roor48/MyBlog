package study.min.myblog.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import study.min.myblog.data.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
    boolean existsByEmail(String email);
}

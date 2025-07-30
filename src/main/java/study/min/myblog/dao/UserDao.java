package study.min.myblog.dao;

import study.min.myblog.data.entity.UserEntity;

public interface UserDao {
    UserEntity save(UserEntity user);
    UserEntity findByEmail(String email);
    boolean existsByEmail(String email);
}

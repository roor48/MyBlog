package study.min.myblog.service;

import study.min.myblog.data.dto.RegisterRequestDto;
import study.min.myblog.data.entity.UserEntity;

public interface UserService {
    UserEntity registerUser(RegisterRequestDto request);
}

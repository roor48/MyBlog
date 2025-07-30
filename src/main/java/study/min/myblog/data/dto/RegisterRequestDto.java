package study.min.myblog.data.dto;

import lombok.Data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
public class RegisterRequestDto {
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Size(min = 6, max = 20, message = "비밀번호는 6자 이상 20자 이하로 입력해주세요.") // 비밀번호 길이 제한
    private String password;

    @NotBlank(message = "사용자 이름은 필수 입력 값입니다.")
    private String username;
}

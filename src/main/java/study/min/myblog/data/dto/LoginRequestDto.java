package study.min.myblog.data.dto;

import lombok.Data; // Lombok 어노테이션

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Data // @Getter, @Setter, @ToString, @EqualsAndHashCode, @NoArgsConstructor 등을 자동 생성
public class LoginRequestDto {
    @NotBlank(message = "이메일은 필수 입력 값입니다.") // 빈 값이거나 공백만 있는 경우를 허용하지 않음
    @Email(message = "이메일 형식이 올바르지 않습니다.") // 이메일 형식 검증
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    private String password;
}

package com.sparta.week03.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Setter
@Getter
public class SignupRequestDto {

    @NotBlank(message = "닉네임을 입력해주세요")
    @Size(min = 3, message = "닉네임은 3자이상으로 입력해주세요.")
    @Pattern(regexp = "^[a-zA-Z0-9]{3}$", message = "닉네임은 알파벳 대소문자(a~z, A~Z), 숫자(0~9)로 입력해주세요.")
    private String username;

    @NotBlank
    @Size(min = 4, message = "비밀번호는 4자리 이상 입력해주세요.")
    private String password;
}
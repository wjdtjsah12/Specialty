package com.sparta.week03.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Setter
@Getter
public class SignupRequestDto {

    private String username;
    private String password;
    private String passwordcheck;

    @Builder
    public SignupRequestDto(String username, String password, String passwordcheck){
        this.username = username;
        this.password = password;
        this.passwordcheck = passwordcheck;
    }
}
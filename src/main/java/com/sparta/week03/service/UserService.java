package com.sparta.week03.service;

import com.sparta.week03.dto.SignupRequestDto;
import com.sparta.week03.model.User;
import com.sparta.week03.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(SignupRequestDto requestDto) {
        // 회원 ID 중복 및 유효성 검사
        String username = checkUsername(requestDto);

        //패스워드 유효성 검사
        checkPassword(requestDto);

        // 패스워드 암호화
        String password = passwordEncoder.encode(requestDto.getPassword());

        User user = new User(username, password);
        userRepository.save(user);
    }

    private String checkUsername(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자 닉네임이 존재합니다.");
        }
        int length = username.length();
        if (length < 3 || length > 15){
            throw new IllegalArgumentException("닉네임은 3 ~ 15 자리로 입력해주세요");
        }

        if(!username.matches(".*[a-zA-Z0-9].*")){
            throw new IllegalArgumentException("닉네임에는 영문 및 숫자가 반드시 포함되어야 합니다.");
        }
        return username;
    }

    private void checkPassword(SignupRequestDto requestDto) {
        if(requestDto.getPassword().contains(requestDto.getUsername())){
            throw new IllegalArgumentException("닉네임은 패스워드에 포함 될 수 없습니다.");
        }

        if(!requestDto.getPassword().equals((requestDto.getPasswordcheck()))){
            throw new IllegalArgumentException("패스워드와 패스워드 확인이 다릅니다.");
        }

        int length = requestDto.getPassword().length();
        if(length < 4 || length > 16) {
            throw new IllegalArgumentException("패스워드는 4 ~ 16자로 입력해주세요.");
        }

    }

    // Controller에서 유효성 검사에 실패한 필드가 있다면, Service 계층으로 Errors 객체를 전달하여 비즈니스 로직을 구현합니다.
    public Map<String, String> validateHandling(Errors errors) {
        Map<String, String> validatorResult = new HashMap<>();
        for (FieldError error : errors.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }
        return validatorResult;
    }
}
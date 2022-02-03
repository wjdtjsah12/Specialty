package com.sparta.week03.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.week03.dto.SignupRequestDto;
import com.sparta.week03.security.UserDetailsImpl;
import com.sparta.week03.service.KakaoUserService;
import com.sparta.week03.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class UserController {

    private final UserService userService;
    private final KakaoUserService kakaoUserService;

    @Autowired
    public UserController(UserService userService, KakaoUserService kakaoUserService) {
        this.userService = userService;
        this.kakaoUserService = kakaoUserService;
    }

    // 회원 로그인 페이지
    @GetMapping("/user/login")
    public String login(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails ==null) {
            return "login";
        }else {
            model.addAttribute("username", userDetails.getUsername());
            return "login";
        }
    }

    // 회원 가입 페이지
    @GetMapping("/user/signup")
    public String signup(SignupRequestDto signupRequestDto, Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails ==null) {
            return "signup";
        }else {
            model.addAttribute("username", userDetails.getUsername());
            return "signup";
        }
    }


    // 회원 가입 요청 처리
    @PostMapping("/user/signup")
    public String registerUser(@Valid SignupRequestDto signupRequestDto, Errors errors, Model model) {
        if (errors.hasErrors()) {
            // 회원가입 실패시, 입력 데이터를 유지
            model.addAttribute("signupRequestDto", signupRequestDto);
            // 유효성 통과 못한 필드와 메시지를 핸들링
            Map<String, String> validatorResult = userService.validateHandling(errors);
            for (String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            }
            return "/signup";
        }

        try {
            //유효성 검사 & 회원가입
            userService.registerUser(signupRequestDto);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "/signup";
        }
        return "redirect:/user/login";
    }

    @GetMapping("/user/kakao/callback")
    // RequestParam으로 code를 받아온다
    public String kakaoLogin(@RequestParam String code) throws JsonProcessingException {
        kakaoUserService.kakaoLogin(code);
        return "redirect:/";
    }
}
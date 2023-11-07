package com.b2.supercoding_prj01.web.controller;

import com.b2.supercoding_prj01.repository.UserRepository;
import com.b2.supercoding_prj01.service.JwtService;
import com.b2.supercoding_prj01.service.UserService;
import com.b2.supercoding_prj01.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;

//    회원가입 - signup
    @PostMapping("/signup")
    public ResponseEntity<String> singUp(@RequestBody UserDto user){
        return userService.saveUser(user);
    }


//    로그인 - login
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody UserDto user) {
            if(userService.checkUser(user)){
                String token = jwtService.createToken(user); // 토큰 생성
                HttpHeaders addTokenToHeaders = jwtService.addTokenToHeaders(token); // HttpHeaders 에 도큰 추가
                return ResponseEntity.status(200).headers(addTokenToHeaders).body(user.getEmail() + "님 반갑습니다.\ntoken : " + user.getToken());
            }
            else return ResponseEntity.status(HttpStatus.FORBIDDEN).body("존재 하지 않는 user입니다.");
    }

}

package com.b2.supercoding_prj01.service;

import com.b2.supercoding_prj01.entity.UserEntity;
import com.b2.supercoding_prj01.repository.UserRepository;
import com.b2.supercoding_prj01.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

//    password encoding 후 user Table에 등록
//    user 존재 여부 확인 후 return
    public ResponseEntity<String> saveUser(UserDto userDto) {
        if(userRepository.findByEmail(userDto.getEmail()).isEmpty()) {
            String plain_password = userDto.getPassword();
            String encoder_password = passwordEncoder.encode(plain_password);

            UserEntity user = UserEntity.builder()
                    .email(userDto.getEmail())
                    .password(encoder_password)
                    .build();

            userRepository.save(user);

            return ResponseEntity.ok("회원 가입이 완료되었습니다.");
        }
        else return ResponseEntity.status(HttpStatus.FORBIDDEN).body("이미 등록된 회원입니다.");
    }

    public boolean checkUser(UserDto userDto) {
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) { //email 확인
            Optional<String> encodedPassword = userRepository.findPasswordByEmail(userDto.getEmail());
            return passwordEncoder.matches(userDto.getPassword(), encodedPassword.get()); //password 확인
        }
        return false;
    }




}

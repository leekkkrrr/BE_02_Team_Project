package com.b2.supercoding_prj01.service;

import com.b2.supercoding_prj01.repository.UserRepository;
import com.b2.supercoding_prj01.web.dto.UserDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final UserRepository userRepository;

    @Value("mySecretKey")
    private String mySecretKey; // 비밀 키

    //토큰 생성
    public String createToken(UserDto user) {
        String token = Jwts.builder()
                            .setSubject(user.getEmail())
                            .signWith(SignatureAlgorithm.HS256, mySecretKey)
            //                .setIssuedAt()
            //                .setExpiration()
                            .compact();

//        UserDto.builder()
//                .email(user.getEmail())
//                .password(user.getPassword())
//                .token(token)          //반환된 토큰 적용
//                .build();

        user.setToken(token);

        return token;
    }

    // 생성한 코드 로그인 시 HttpHeaders에 넘겨줌
    public HttpHeaders addTokenToHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        return headers;
    }

    public String validateAndGetUserId(String token) {
        // parseClaimsJws 메서드가 Base64로 디코딩 및 파싱
        // 헤더와 페이로드를 setSigningKey로 넘어온 시크릿을 이용해 서명한 후 token의 서명과 비교
        // 위조되지 않았다면 페이로드(Claims) 리턴, 위조라면 예외를 날림
        // 그중 우리는 userId가 필요하므로 getBody를 부른다
        Claims claims = Jwts.parser()
                .setSigningKey(mySecretKey)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject(); // subject 즉 사용자 아이디를 리턴한다.
    }
}

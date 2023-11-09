package com.b2.supercoding_prj01.dto;

import com.b2.supercoding_prj01.entity.UserEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Builder
@AllArgsConstructor
public class UserRequestDto {

    private String email;
    private String password;

    public UserEntity toEntity(){
        return UserEntity.builder()
                .email(email)
                .password(password)
                .build();
    }

}

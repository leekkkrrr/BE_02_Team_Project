package com.b2.supercoding_prj01.repository.userDetails;

import com.b2.supercoding_prj01.entity.UserEntity;
import com.b2.supercoding_prj01.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetails implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetails(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("로그인 아이디가 없습니다"));

        return user;
    }
}

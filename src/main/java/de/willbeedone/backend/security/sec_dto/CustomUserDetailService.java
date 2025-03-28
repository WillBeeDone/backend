package de.willbeedone.backend.security.sec_dto;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface CustomUserDetailService {
        UserDetails loadUserByEmail(String email) throws UsernameNotFoundException;
    }


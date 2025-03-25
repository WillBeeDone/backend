package de.willbeedone.backend.security.sec_dto;

import de.willbeedone.backend.domain.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Collections;

public interface CustomUserDetails extends UserDetails {

//    @Override
//    Collection<? extends GrantedAuthority> getAuthorities();
//
//    @Override
//    public String getPassword() {
//        return user.getPassword();
//    }


    String getEmail();


   @Override
   String getUsername();


//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//
//    public User getUser() {
//        return user;
//    }

}

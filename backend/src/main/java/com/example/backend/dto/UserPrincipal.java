package com.example.backend.dto;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.backend.entity.User;

public class UserPrincipal implements UserDetails {

    private User user;

    public UserPrincipal(User user) {
        this.user = user;
    }

    // public UserPrincipal(LoginRequest loginRequest){
    // this.user.setEmail(loginRequest.getEmail());
    // this.user.setPassword(loginRequest.getPassword());
    // }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("USER"));
        // throw new UnsupportedOperationException("Unimplemented method
        // 'getAuthorities'");
    }

    @Override
    public String getPassword() {
        return user.getPassword();
        // throw new UnsupportedOperationException("Unimplemented method
        // 'getPassword'");
    }

    @Override
    public String getUsername() {
        return user.getEmail();
        // throw new UnsupportedOperationException("Unimplemented method
        // 'getUsername'");
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {

        return true;
    }

    @Override
    public boolean isEnabled() {

        return true;
    }

}

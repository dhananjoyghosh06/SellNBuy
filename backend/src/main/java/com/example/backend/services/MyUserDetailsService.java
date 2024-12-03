package com.example.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.backend.dto.UserPrincipal;
import com.example.backend.entity.User;
import com.example.backend.repository.UserRepo;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try{
            User user = userRepo.findByEmail(username);
            if(user == null) throw new UsernameNotFoundException("Invalid Email");
            return new UserPrincipal(user);
        }catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
        // throw new UnsupportedOperationException("Unimplemented method 'loadUserByUsername'");
    }

}

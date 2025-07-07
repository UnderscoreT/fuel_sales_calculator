package com.blestcodestudios.fuelsalesapp.service;

import com.blestcodestudios.fuelsalesapp.repository.UserRepository;
import com.blestcodestudios.fuelsalesapp.util.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
     private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var profile = userRepository.findByUsernameIgnoreCase(username);

        if(profile.isPresent()) return new CustomUserDetails(profile.get());
        throw new UsernameNotFoundException("User not found");

    }
}
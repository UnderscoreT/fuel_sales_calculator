package com.blestcodestudios.fuelsalesapp.util;

import com.blestcodestudios.fuelsalesapp.entity.AppUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class CustomUserDetails implements UserDetails {

    private final AppUser appUser;
    public CustomUserDetails(AppUser appUser) {
        this.appUser = appUser;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        var authorities = new ArrayList<SimpleGrantedAuthority>();
        SimpleGrantedAuthority dev=new SimpleGrantedAuthority("ROLE_DEVELOPER");
        SimpleGrantedAuthority owner = new SimpleGrantedAuthority("ROLE_OWNER");
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

        if(Objects.equals("obey", appUser.getUsername().toLowerCase())){
            authorities.add(dev);
            authorities.add(owner);
        }
        return authorities;
    }


    @Override
    public String getPassword() {
        return appUser.getEncryptedPassword();
    }

    @Override
    public String getUsername() {
        return appUser.getUsername();
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
        return true;    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

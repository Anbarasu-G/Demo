package com.rest.ets.security;

import com.rest.ets.entity.User;
import com.rest.ets.enums.Privilege;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
public class CustomUserDetails implements UserDetails {

    private  final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {


        return user.getRole().getPrivileges().stream().map((privilage)->{
            return new SimpleGrantedAuthority(privilage.name());
        }).toList();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }
}

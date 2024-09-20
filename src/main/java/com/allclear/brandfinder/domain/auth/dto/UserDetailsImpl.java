package com.allclear.brandfinder.domain.auth.dto;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.allclear.brandfinder.domain.users.entity.Rank;
import com.allclear.brandfinder.domain.users.entity.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return null;
    }

    @Override
    public String getPassword() {

        return user.getPassword();
    }

    @Override
    public String getUsername() {

        return user.getUsername();
    }

    public String getEmail() {
        return user.getEmail();
    }

    public Rank getRank() {
        return user.getRank();
    }

    public LocalDate getBirth() {
        return user.getBirth();
    }

}

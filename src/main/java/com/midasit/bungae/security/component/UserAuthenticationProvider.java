package com.midasit.bungae.security.component;

import com.midasit.bungae.user.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authToken = (UsernamePasswordAuthenticationToken)authentication;
        UserDetails user = userDetailsService.loadUserByUsername(authToken.getName());

        if (user == null) {
            throw new UsernameNotFoundException(authToken.getName());
        }

        if (!matchPassword(user.getPassword(), authToken.getCredentials())) {
            throw new BadCredentialsException("not matching username or password");
        }

        List<GrantedAuthority> authorities = (List<GrantedAuthority>)user.getAuthorities();

        return new UsernamePasswordAuthenticationToken(user, authentication.getCredentials(), authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

    private boolean matchPassword(String password, Object credentials) {
        return password.equals(credentials);
    }
}

package org.dorixon.websiteproject.config;

import org.dorixon.websiteproject.model.AppUser;
import org.dorixon.websiteproject.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
public class UserDetailsConfig {


    @Bean
    public UserDetailsService userDetailsService(UserService userService) {
        return username -> {
            AppUser appUser = userService.findByUsername(username);
            if (appUser == null) {
                throw new UsernameNotFoundException("Użytkownik nie znaleziony: " + username);
            }
            return User.builder()
                    .username(appUser.getUsername())
                    .password(appUser.getPassword())
                    .disabled(!appUser.isEnabled())
                    .accountExpired(!appUser.isAccountNonExpired())
                    .accountLocked(!appUser.isAccountNonLocked())
                    .credentialsExpired(!appUser.isCredentialsNonExpired())
                    .authorities("ROLE_USER")
                    .build();
        };
    }
}
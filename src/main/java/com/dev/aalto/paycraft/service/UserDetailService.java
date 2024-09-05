package com.dev.aalto.paycraft.service;

import com.dev.aalto.paycraft.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {

    private final UserAccountRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var userOptional = userRepository.findByEmailAddress(username);
        if (userOptional.isPresent()) {
            return userOptional.orElseThrow();
        }
        throw new UsernameNotFoundException("No USER found with email: " + username);
    }
}

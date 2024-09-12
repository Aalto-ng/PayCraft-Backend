package com.aalto.paycraft.service;

import com.aalto.paycraft.repository.EmployerProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {

    private final EmployerProfileRepository employerProfileRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var userOptional = employerProfileRepository.findByEmailAddress(username);
        if (userOptional.isPresent()) {
            return userOptional.orElseThrow();
        }
        throw new UsernameNotFoundException("No USER found with emailAddress: " + username);
    }
}

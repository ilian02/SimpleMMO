package com.example.Profiles.service;

import com.example.Profiles.repository.MMOUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MMOUserDetailsService implements UserDetailsService {

    @Autowired
    private MMOUserRepository mmoUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return mmoUserRepository.findByUsername(username).orElseThrow();
    }
}

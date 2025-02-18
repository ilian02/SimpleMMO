package com.example.Profiles.service;

import com.example.Profiles.Dto.Response;
import com.example.Profiles.Dto.UserLoginDto;
import com.example.Profiles.Dto.UserRegisterDto;
import com.example.Profiles.entity.MMOUser;
import com.example.Profiles.repository.MMOUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.Optional;

@Service
public class MMOUserService {

    @Autowired
    private MMOUserRepository mmoUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtils jwtUtils;

    public Response register(UserRegisterDto userRegisterDto) {
        Response resp = new Response();

        if (userRegisterDto.getUsername().length() < 5 || userRegisterDto.getPassword().length() < 5) {
            resp.setStatusCode(401);
            resp.setError("Username and password must be at least 5 symbols");
            return resp;
        }

        try {
            MMOUser user = new MMOUser();
            user.setUsername(userRegisterDto.getUsername());
            user.setEmail(userRegisterDto.getEmail());
            user.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));

            Optional<MMOUser> checkForUsernameUsed = mmoUserRepository.findByUsername(user.getUsername());
            Optional<MMOUser> checkForEmail = mmoUserRepository.findByEmail(user.getEmail());
            if (checkForUsernameUsed.isPresent()) {
                resp.setStatusCode(500);
                resp.setError("Username is already used");
                return resp;
            }

            if (checkForEmail.isPresent()) {
                resp.setStatusCode(500);
                resp.setError("Email is already used");
                return resp;
            }

            MMOUser userCreated = mmoUserRepository.save(user);
            if (userCreated.getId() > 0) {
                resp.setMessage("Client registered");
                var jwt = jwtUtils.generateToken(user);
                var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);
                resp.setToken(jwt);
                resp.setRefreshToken(refreshToken);
                resp.setExpirationTime("24Hrs");
                resp.setStatusCode(200);
            }


        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }

        return resp;
    }

    public Response login(UserLoginDto userLoginDto) {
        Response resp = new Response();

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userLoginDto.getUsername(), userLoginDto.getPassword()
            ));
            Optional<MMOUser> user = mmoUserRepository.findByUsername(userLoginDto.getUsername());
            var jwt = jwtUtils.generateToken(user.get());
            var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user.get());
            resp.setStatusCode(200);
            resp.setToken(jwt);
            resp.setRefreshToken(refreshToken);
            resp.setExpirationTime("24Hrs");
            resp.setMessage("Client logged in successfully");
        } catch (Exception e) {
            resp.setStatusCode(402);
            resp.setError(e.getMessage());
        }

        return resp;
    }

}

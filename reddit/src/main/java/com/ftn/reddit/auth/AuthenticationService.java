/*package com.ftn.reddit.auth;

import com.ftn.reddit.model.UserRole;
import com.ftn.reddit.model.Users;
import com.ftn.reddit.repositorys.UsersRepository;
import com.ftn.reddit.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements AuthenticationProvider {

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final UsersRepository repository;

    private final PasswordEncoder passwordEncoder;


    public AuthenticationResponse generateToken(AuthenticationRequest request) {
        var user = repository.findByEmail(request.getEmail());
        var jwtToken = jwtService.generateToken((UserDetails) user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        Users user = repository.findByUsername(username);

        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
        } else {
            throw new BadCredentialsException("Bad credentials");
        }
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
*/
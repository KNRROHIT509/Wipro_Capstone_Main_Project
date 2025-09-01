package com.knr.hospital.controller.auth;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.knr.hospital.dto.AuthenticationRequest;
import com.knr.hospital.dto.AuthenticationResponse;
import com.knr.hospital.dto.SignupRequest;
import com.knr.hospital.dto.UserDto;
import com.knr.hospital.entities.User;
import com.knr.hospital.repositories.UserRepository;
import com.knr.hospital.services.auth.AuthService;
import com.knr.hospital.services.jwt.UserService;
import com.knr.hospital.utils.JwtUtil;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private final AuthService authService;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final JwtUtil jwtUtil;
    @Autowired
    private final UserService userService;
    @Autowired
    private final AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    public ResponseEntity<?> signupUser(@Valid @RequestBody SignupRequest signupRequest) {
        if (authService.hasUserWithEmail(signupRequest.getEmail())) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("User already exists with this email");
        }
        UserDto createdUserDto = authService.signupUser(signupRequest);
        if (createdUserDto == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not created");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUserDto);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
                    (authenticationRequest.getEmail(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect password or email!");
        }
        final UserDetails userDetails = userService.userDetailsService().loadUserByUsername(authenticationRequest.getEmail());
        Optional<User> optionalUser = userRepository.findFirstByEmail(authenticationRequest.getEmail());
        final String jwtToken = jwtUtil.generateToken(userDetails);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        if (optionalUser.isPresent()) {
            authenticationResponse.setJwt(jwtToken);
            authenticationResponse.setUserId(optionalUser.get().getId());
            authenticationResponse.setUserRole(optionalUser.get().getUserRole());
        }
        return authenticationResponse;
    }

    // New endpoint for admin to create doctor accounts
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create-doctor")
    public ResponseEntity<?> createDoctor(@Valid @RequestBody SignupRequest signupRequest) {
        signupRequest.setRole(com.knr.hospital.enums.UserRole.DOCTOR); // Enforce DOCTOR role
        UserDto createdDoctor = authService.createDoctorAccount(signupRequest);
        if (createdDoctor == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Doctor not created");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDoctor);
    }
}

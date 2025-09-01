package com.knr.hospital.services.auth;

import java.util.Optional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.knr.hospital.dto.SignupRequest;
import com.knr.hospital.dto.UserDto;
import com.knr.hospital.entities.User;
import com.knr.hospital.enums.UserRole;
import com.knr.hospital.repositories.UserRepository;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j; // For logging best practices

@Service
@AllArgsConstructor
@Slf4j // Added for auditing signup/creation
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    @PostConstruct
    @Transactional
    public void createAdminAccount() {
        Optional<User> optionalUser = userRepository.findByUserRole(UserRole.ADMIN).stream().findFirst(); // Updated to use List from repository
        if (optionalUser.isEmpty()) {
            User user = new User();
            user.setEmail("admin@test.com");
            user.setName("admin");
            user.setPassword(new BCryptPasswordEncoder().encode("admin"));
            user.setUserRole(UserRole.ADMIN);
            userRepository.save(user);
            log.info("Admin account created successfully");
            
            // TODO: Trigger Kafka event for admin creation notification (as per PDF event-driven design)
        } else {
            log.info("Admin account already exists");
        }
        // Removed simulated failure; add only for testing rollbacks if needed
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserDto signupUser(SignupRequest signupRequest) {
        if (userRepository.findFirstByEmail(signupRequest.getEmail()).isPresent()) {
            log.warn("Signup failed: Email {} already exists", signupRequest.getEmail());
            throw new IllegalStateException("Email already exists");
        }

        User user = new User();
        user.setEmail(signupRequest.getEmail());
        user.setName(signupRequest.getName());
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
        user.setUserRole(signupRequest.getRole() != null ? signupRequest.getRole() : UserRole.PATIENT); // Use provided role or default to PATIENT for hospital patient registration

        userRepository.save(user);
        log.info("User signed up: {} with role {}", user.getEmail(), user.getUserRole());
        
        // TODO: Trigger Kafka notification event for new user (e.g., welcome email/SMS as per PDF patient workflow)
        
        return createUserDto(user); // Updated to use a helper method for DTO conversion
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserDto createDoctorAccount(SignupRequest signupRequest) {
        // Similar to signupUser but enforces DOCTOR role (for admin workflow)
        if (userRepository.findFirstByEmail(signupRequest.getEmail()).isPresent()) {
            log.warn("Doctor creation failed: Email {} already exists", signupRequest.getEmail());
            throw new IllegalStateException("Email already exists");
        }

        User user = new User();
        user.setEmail(signupRequest.getEmail());
        user.setName(signupRequest.getName());
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
        user.setUserRole(UserRole.DOCTOR); // Enforced for doctor creation

        userRepository.save(user);
        log.info("Doctor account created: {} with role {}", user.getEmail(), user.getUserRole());
        
        // TODO: Trigger Kafka notification event for new doctor (e.g., assign department, notify admin)
        
        return createUserDto(user);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean hasUserWithEmail(String email) {
        return userRepository.findFirstByEmail(email).isPresent();
    }
    
    private UserDto createUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setUserRole(user.getUserRole());
        // Password not included in DTO for security
        return userDto;
    }
}

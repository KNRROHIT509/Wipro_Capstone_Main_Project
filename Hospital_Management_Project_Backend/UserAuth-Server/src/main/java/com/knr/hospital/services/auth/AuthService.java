package com.knr.hospital.services.auth;

import com.knr.hospital.dto.SignupRequest;
import com.knr.hospital.dto.UserDto;

public interface AuthService 
{

	UserDto signupUser(SignupRequest signupRequest);
	boolean hasUserWithEmail(String email);
	
	UserDto createDoctorAccount(SignupRequest signupRequest);
}

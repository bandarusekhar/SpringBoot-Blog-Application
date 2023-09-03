package in.sekhar.service;

import in.sekhar.payload.LoginDto;
import in.sekhar.payload.RegisterDto;

public interface AuthService {

	String login(LoginDto loginDto);

	String register(RegisterDto registerDto);
}

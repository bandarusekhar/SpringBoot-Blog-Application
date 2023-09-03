package in.sekhar.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;

import in.sekhar.entity.Role;
import in.sekhar.entity.User;
import in.sekhar.exception.BlogAPIException;
import in.sekhar.payload.LoginDto;
import in.sekhar.payload.RegisterDto;
import in.sekhar.repos.RoleRepository;
import in.sekhar.repos.UserRepository;
import in.sekhar.security.JwtTokenProvider;
import in.sekhar.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@Override
	public String login(LoginDto loginDto) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String token = jwtTokenProvider.generateToken(authentication);
		return token;
	}

	@Override
	public String register(RegisterDto registerDto) {

		if (userRepository.existsByUsername(registerDto.getUsername())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Username already exists");
		}
		if (userRepository.existsByEmail(registerDto.getEmail())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Email already exists");
		}

		System.out.println("user Starts");
		
		User user = new User();
		user.setName(registerDto.getName());
		user.setUsername(registerDto.getUsername());
		user.setEmail(registerDto.getEmail());
		user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
		

		Set<Role> roles = new HashSet<>();
		Role role = roleRepository.findByName("ROLE_USER").get();
		roles.add(role);
		user.setRoles(roles);
		userRepository.save(user);
		return "User registered";
	}

}

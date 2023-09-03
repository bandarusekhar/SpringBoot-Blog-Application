package in.sekhar.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.sekhar.payload.JWTAuthResponse;
import in.sekhar.payload.LoginDto;
import in.sekhar.payload.RegisterDto;
import in.sekhar.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(
		name="Authentication REST APIs"
)
public class AuthController {

	@Autowired
	private AuthService authService;
	
	@Operation(
			summary = "JWT Token Rest API",
			description = "This REST API is used to get the JWT token"
	)
	@ApiResponse(
			responseCode = "200",
			description = "Http status 200 SUCEESS"
	)
	@PostMapping(value = { "/login", "/signin" })
	public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDto loginDto) {
		String token = authService.login(loginDto);
		JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
		jwtAuthResponse.setAccessToken(token);
		return ResponseEntity.ok(jwtAuthResponse);
	}

	@Operation(
			summary = "Register Rest API",
			description = "This REST API is used to register new user and store it to database"
	)
	@ApiResponse(
			responseCode = "201",
			description = "Http status 201 CREATED"
	)
	@PostMapping(value = { "/register", "/signup" })
	public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
		String response = authService.register(registerDto);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
}

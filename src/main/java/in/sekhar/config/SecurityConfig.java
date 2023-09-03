package in.sekhar.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import in.sekhar.security.CustomeUserDetailsService;
import in.sekhar.security.JwtAuthenticationEntryPoint;
import in.sekhar.security.JwtAuthenticationFilter;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@Configuration
@EnableMethodSecurity
@SecurityScheme(
		name="Bearer Authentication",
		type=SecuritySchemeType.HTTP,
		bearerFormat = "JWT",
		scheme = "bearer"
		)
public class SecurityConfig {

	@Autowired
	private UserDetailsService userDtlsService;

	@Autowired
	private JwtAuthenticationEntryPoint authenticationEntryPoint;
	
	@Autowired
	private JwtAuthenticationFilter authenticationFilter;
	
	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.csrf().disable().authorizeHttpRequests((authorize) ->
		// authorize.anyRequest().authenticated()
		authorize.requestMatchers(HttpMethod.GET, "/api/v1/**").permitAll()
		       // .requestMatchers(HttpMethod.GET,"/api/categories/**").permitAll()
		        .requestMatchers("/swagger-ui/**").permitAll()
		        .requestMatchers("/v3/api-docs/**").permitAll()
				.requestMatchers("/api/v1/auth/**").permitAll().anyRequest().authenticated())
				.httpBasic(Customizer.withDefaults())
				.exceptionHandling(exception -> exception.authenticationEntryPoint(authenticationEntryPoint))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();

	}

//	@Bean
//	public UserDetailsService userDetailsService() {
//		UserDetails sekhar = User.builder().username("sekhar").password(passwordEncoder().encode("sekhar"))
//				.roles("USER").build();
//
//		UserDetails admin = User.builder().username("admin").password(passwordEncoder().encode("admin")).roles("ADMIN")
//				.build();
//
//		return new InMemoryUserDetailsManager(sekhar, admin);
//
//	}
}

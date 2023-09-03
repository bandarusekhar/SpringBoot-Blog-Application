package in.sekhar;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import in.sekhar.entity.Role;
import in.sekhar.repos.RoleRepository;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Spring Boot Blog App REST APIs", description = "Spring Boot App REST APIs Documentation", version = "v1.0", contact = @Contact(name = "sekhar", email = "abc@gmail.com", url = "https://google.com"), license = @License(name = "Apache 2.8", url = "https://google.com")), externalDocs = @ExternalDocumentation(description = "Spring Boot Blog App Documentation", url = "https://google.com"))
public class SpringbootBloggRestApiApplication implements CommandLineRunner {

	@Autowired
	private RoleRepository roleRepository;

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringbootBloggRestApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Role adminRole = new Role();
		adminRole.setName("ROLE_ADMIN");
		Role userRole = new Role();
		userRole.setName("ROLE_USER");
		roleRepository.save(adminRole);
		roleRepository.save(userRole);
	}

}

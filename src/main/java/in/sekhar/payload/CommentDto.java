package in.sekhar.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class CommentDto {
	private Long commentId;

	@NotEmpty(message = "Name should not be null or empty")
	private String name;

	@NotEmpty(message = "Name should not be null or empty")
	@Email
	private String email;
	@NotEmpty
	@Size(min = 10, message = "comment body should be minimum 10 characters")
	private String body;
}

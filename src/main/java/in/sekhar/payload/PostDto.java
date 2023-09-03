package in.sekhar.payload;

import java.util.Set;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Schema(
		description = "PostDto Model information"
)
@Data
public class PostDto {

	private Long id;
	
	@Schema(
			description = "Blog post title"
	)
	@NotEmpty
	@Size(min=2,message = "Post title should have atleast 2 characters")
	private String title;
	
	@Schema(
			description = "Blog post description"
	)
	@NotEmpty
	@Size(min=2,message = "description should have atleast 10 characters")
	private String description;
	
	@Schema(
			description = "Blog post content"
	)
	@NotEmpty
	private String content;
	
	@Schema(
			description = "Blog post comments"
	)
	private Set<CommentDto> comment;
	
	@Schema(
			description = "Blog post category"
	)
	private Long categoryId;
}

package in.sekhar.payload;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(
		description = "Post Response information"
)
@Data
public class PostResponse {

	@Schema(
			description = "Posts"
	)
	private List<PostDto> content;
	
	@Schema(
			description = "Pages"
	)
	private int pageNo;
	@Schema(
			description = "Page Size"
	)
	private int pageSize;
	@Schema(
			description = "Total Posts"
	)
	private Long totalElements;
	@Schema(
			description = "Total Pages"
	)
	private int totalPages;
	@Schema(
			description = "Last Page"
	)
	private boolean last;
}

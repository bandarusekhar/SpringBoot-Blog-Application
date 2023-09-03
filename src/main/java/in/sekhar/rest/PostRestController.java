package in.sekhar.rest;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.sekhar.payload.PostDto;
import in.sekhar.payload.PostResponse;
import in.sekhar.repos.PostRepository;
import in.sekhar.service.PostService;
import in.sekhar.utils.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(
		name="CRUD Rest APIs for POST Resource"
)
public class PostRestController {

	@Autowired
	private PostService postService;

	@SecurityRequirement(
			name="Bearer Authentication"
	)
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/api/v1/posts")
	@Operation(
			summary = "Create Post REST API",
			description = "Create Post REST API is used to save post into database"
	)
	@ApiResponse(
			responseCode = "201",
			description = "Http Status 201 CREATED"
	)
	public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto) {
		return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
	}

	@GetMapping("/api/v1/posts")
	@Operation(
			summary = "Fetch Posts REST API",
			description = "Fetch Posts REST API is used to get the all posts from database"
	)
	@ApiResponse(
			responseCode = "200",
			description = "Http Status 200 SUCCESS"
	)
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
		return new ResponseEntity<>(postService.getAllPosts(pageNo, pageSize, sortBy, sortDir), HttpStatus.OK);
	}

	@Operation(
			summary = "Get Post REST API",
			description = "Get Post REST API is used to get a Post by Id from database"
	)
	@ApiResponse(
			responseCode = "200",
			description = "Http Status 200 SUCCESS"
	)
	@GetMapping("/api/v1/posts/{id}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Long id) {
		return new ResponseEntity<>(postService.getPostById(id), HttpStatus.OK);
	}

	@SecurityRequirement(
			name="Bearer Authentication"
	)
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/api/v1/posts/{id}")
	@Operation(
			summary = "Update Post REST API",
			description = "Update Post REST API is used to update the existing Post in database"
	)
	@ApiResponse(
			responseCode = "200",
			description = "Http Status 200 SUCCESS"
	)
	public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable Long id) {
		return new ResponseEntity<>(postService.updatePost(postDto, id), HttpStatus.OK);
	}

	@SecurityRequirement(
			name="Bearer Authentication"
	)
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/api/v1/posts/{id}")
	@Operation(
			summary = "Delete Post REST API",
			description = "Delete Post REST API is used to Delete a Post from database"
	)
	@ApiResponse(
			responseCode = "200",
			description = "Http Status 200 SUCCESS"
	)
	public ResponseEntity<String> deletePost(@PathVariable Long id) {
		postService.deletePostById(id);
		return new ResponseEntity<>("Post Deleted successfully", HttpStatus.OK);
	}
	
	@GetMapping("/api/v1/posts/category/{id}")
	@Operation(
			summary = "Fetch Post REST API by Category",
			description = "Fetch Post REST API is used to Fetch all posts based on category from database"
	)
	@ApiResponse(
			responseCode = "200",
			description = "Http Status 200 SUCCESS"
	)
	public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Long id){
		List<PostDto> posts = postService.getPostsByCategory(id);
		return ResponseEntity.ok(posts);
	}
}

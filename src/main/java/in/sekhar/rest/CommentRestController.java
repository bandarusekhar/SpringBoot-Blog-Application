package in.sekhar.rest;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.sekhar.payload.CommentDto;
import in.sekhar.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1")
@Tag(
		name="CRUD Rest APIs for COMMENT Resource"
)
public class CommentRestController {

	@Autowired
	private CommentService commentService;

	@Operation(
			summary = "Comment Post REST API",
			description = "This API is used to create a comment from perticular post and store it to database"
	)
	@ApiResponse(
			responseCode = "201",
			description = "Http Status 201 CREATED"
	)
	@PostMapping("/posts/{id}/comments")
	public ResponseEntity<CommentDto> createComment(@PathVariable(value = "id") Long postId,
			@Valid @RequestBody CommentDto commentDto) {

		commentDto = commentService.createComment(postId, commentDto);
		return new ResponseEntity<>(commentDto, HttpStatus.CREATED);
	}

	@Operation(
			summary = "Fetch Comments REST API",
			description = "This API is used to fetch all comments for perticular post from database"
	)
	@ApiResponse(
			responseCode = "200",
			description = "Http Status 200 SUCCESS"
	)
	@GetMapping("/posts/{id}/comments")
	public ResponseEntity<List<CommentDto>> getCommentsByPostId(@PathVariable("id") Long postId) {

		List<CommentDto> comments = commentService.getCommentsByPostId(postId);
		return new ResponseEntity<>(comments, HttpStatus.OK);
	}

	@Operation(
			summary = "Fetch Comment REST API",
			description = "This API is used to fetch a perticular comment for perticular post from database"
	)
	@ApiResponse(
			responseCode = "200",
			description = "Http Status 200 SUCCESS"
	)
	@GetMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<CommentDto> getCommentbyId(@PathVariable Long postId, @PathVariable Long commentId) {
		CommentDto commentDto = commentService.getCommentById(postId, commentId);

		return new ResponseEntity<>(commentDto, HttpStatus.OK);
	}

	@Operation(
			summary = "Update Comment REST API",
			description = "This API is used to update a perticular comment for perticular post in database"
	)
	@ApiResponse(
			responseCode = "200",
			description = "Http Status 200 SUCCESS"
	)
	@PutMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<CommentDto> updateComment(@PathVariable Long postId, @PathVariable Long commentId,
			@Valid @RequestBody CommentDto commentDto) {
		CommentDto updatedComment = commentService.updateComment(postId, commentId, commentDto);
		return new ResponseEntity<>(updatedComment, HttpStatus.OK);
	}

	@Operation(
			summary = "Delete Comment REST API",
			description = "This API is used to delete a perticular comment for perticular post in database"
	)
	@ApiResponse(
			responseCode = "200",
			description = "Http Status 200 SUCCESS"
	)
	@DeleteMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<String> deleteComment(@PathVariable Long postId, @PathVariable Long commentId) {
		commentService.deleteComment(postId, commentId);
		return new ResponseEntity<>("Comment deleted", HttpStatus.OK);
	}
}

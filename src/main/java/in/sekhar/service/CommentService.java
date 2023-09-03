package in.sekhar.service;

import java.util.List;

import in.sekhar.payload.CommentDto;

public interface CommentService {

	CommentDto createComment(Long id, CommentDto commentDto);

	List<CommentDto> getCommentsByPostId(Long postId);

	CommentDto getCommentById(Long postId, Long commentId);

	CommentDto updateComment(Long postId, Long commentId, CommentDto commentDto);

	void deleteComment(Long postId,Long commentId);
}

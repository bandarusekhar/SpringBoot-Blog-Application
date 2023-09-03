package in.sekhar.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import in.sekhar.entity.Comment;
import in.sekhar.entity.Post;
import in.sekhar.exception.BlogAPIException;
import in.sekhar.exception.ResourceNotFoundException;
import in.sekhar.payload.CommentDto;
import in.sekhar.repos.CommentRepository;
import in.sekhar.repos.PostRepository;
import in.sekhar.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CommentDto createComment(Long postId, CommentDto commentDto) {

		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
		Comment commentEntity = mapToEntity(commentDto);
		commentEntity.setPost(post);
		commentEntity = commentRepository.save(commentEntity);
		return mapToDto(commentEntity);
	}

	private Comment mapToEntity(CommentDto commentDto) {

		Comment comment = modelMapper.map(commentDto, Comment.class);
		return comment;

		/*
		 * Comment comment = new Comment(); comment.setName(commentDto.getName());
		 * comment.setEmail(commentDto.getEmail());
		 * comment.setBody(commentDto.getBody()); return comment;
		 */
	}

	private CommentDto mapToDto(Comment comment) {

		CommentDto commentDto = modelMapper.map(comment, CommentDto.class);
		return commentDto;
		/*
		 * CommentDto commentDto = new CommentDto();
		 * commentDto.setCommentId(comment.getId());
		 * commentDto.setName(comment.getName());
		 * commentDto.setEmail(comment.getEmail());
		 * commentDto.setBody(comment.getBody()); return commentDto;
		 */
	}

	@Override
	public List<CommentDto> getCommentsByPostId(Long postId) {
		List<Comment> comments = commentRepository.findByPostId(postId);
		return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
	}

	@Override
	public CommentDto getCommentById(Long postId, Long commentId) {

		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

		if (!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "comment does not belongg to this post");
		}

		return mapToDto(comment);
	}

	@Override
	public CommentDto updateComment(Long postId, Long commentId, CommentDto commentDto) {

		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

		if (!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "comment does not belongg to this post");
		}

		comment.setName(commentDto.getName());
		comment.setEmail(commentDto.getEmail());
		comment.setBody(commentDto.getBody());

		Comment updatedComment = commentRepository.save(comment);
		return mapToDto(updatedComment);
	}

	@Override
	public void deleteComment(Long postId, Long commentId) {

		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

		if (!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "comment does not belongg to this post");
		}

		commentRepository.delete(comment);

	}

}

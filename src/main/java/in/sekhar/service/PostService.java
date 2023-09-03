package in.sekhar.service;

import java.util.List;

import in.sekhar.payload.PostDto;
import in.sekhar.payload.PostResponse;

public interface PostService {

	PostDto createPost(PostDto postDto);

	PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

	PostDto getPostById(Long id);

	PostDto updatePost(PostDto postDto, Long id);

	void deletePostById(Long id);
	
	List<PostDto> getPostsByCategory(Long categoryId);
}

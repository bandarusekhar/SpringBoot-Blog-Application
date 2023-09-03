package in.sekhar.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import in.sekhar.entity.Category;
import in.sekhar.entity.Post;
import in.sekhar.exception.ResourceNotFoundException;
import in.sekhar.payload.PostDto;
import in.sekhar.payload.PostResponse;
import in.sekhar.repos.CategoryRepository;
import in.sekhar.repos.PostRepository;
import in.sekhar.service.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public PostDto createPost(PostDto postDto) {

		Category category = categoryRepository.findById(postDto.getCategoryId())
		                  .orElseThrow(()->new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));
		
		Post postEntity = mapToEntity(postDto);
		postEntity.setCategory(category);
		postEntity = postRepository.save(postEntity);
		PostDto createdPost = mapToDto(postEntity);
		return createdPost;
	}

	@Override
	public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {

		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending();

		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		Page<Post> pagePosts = postRepository.findAll(pageable);
		List<Post> listOfPosts = pagePosts.getContent();

		List<PostDto> content = listOfPosts.stream().map(p -> mapToDto(p)).collect(Collectors.toList());
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(content);
		postResponse.setPageNo(pagePosts.getNumber());
		postResponse.setPageSize(pagePosts.getSize());
		postResponse.setTotalElements(pagePosts.getTotalElements());
		postResponse.setTotalPages(pagePosts.getTotalPages());
		postResponse.setLast(pagePosts.isLast());

		return postResponse;
	}

	@Override
	public PostDto getPostById(Long id) {

		Post postEntity = postRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		PostDto postDto = new PostDto();
		postDto = mapToDto(postEntity);
		return postDto;

	}

	@Override
	public PostDto updatePost(PostDto postDto, Long id) {

		Post postEntity = postRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		
		Category category = categoryRepository.findById(postDto.getCategoryId())
                .orElseThrow(()->new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));

		postEntity.setTitle(postDto.getTitle());
		postEntity.setDescription(postDto.getDescription());
		postEntity.setContent(postDto.getContent());
		postEntity.setCategory(category);
		Post updatedPost = postRepository.save(postEntity);

	    PostDto updatedPostDto = mapToDto(updatedPost);
		return updatedPostDto;
	}

	@Override
	public void deletePostById(Long id) {

		Post postEntity = postRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		postRepository.delete(postEntity);

	}

	private Post mapToEntity(PostDto postDto) {
		Post post = modelMapper.map(postDto, Post.class);
		return post;
	}

	private PostDto mapToDto(Post post) {
		PostDto postDto = modelMapper.map(post, PostDto.class);
		return postDto;
	}

	@Override
	public List<PostDto> getPostsByCategory(Long categoryId) {

		Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("Category", "id", categoryId));
		
		List<Post> posts = postRepository.findByCategoryId(categoryId);
		
        return posts.stream().map((post)->mapToDto(post)).collect(Collectors.toList());
	}

}

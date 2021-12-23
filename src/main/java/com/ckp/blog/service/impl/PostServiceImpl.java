package com.ckp.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ckp.blog.dto.PostDto;
import com.ckp.blog.dto.PostPaginationSupport;
import com.ckp.blog.exception.ResourceNotFoundException;
import com.ckp.blog.model.Post;
import com.ckp.blog.repository.PostRepository;
import com.ckp.blog.service.PostService;

@Service
public class PostServiceImpl implements PostService {

	private PostRepository postRepository;
	private ModelMapper modelMapper;

	public PostServiceImpl(PostRepository postRepository, ModelMapper modelMappe) {
		this.postRepository = postRepository;
		this.modelMapper = modelMappe;
	}

	@Override
	public PostDto createPost(PostDto postDto) {

		Post post = mapToEntity(postDto);
		/*
		 * post.setTitle(postDto.getTitle());
		 * post.setDescription(postDto.getDescription());
		 * post.setContent(postDto.getContent());
		 */

		Post newPost = postRepository.save(post);

		PostDto postResponse = mapToDTO(newPost);
		/*
		 * postResponse.setId(newPost.getId());
		 * postResponse.setTitle(newPost.getTitle());
		 * postResponse.setDescription(newPost.getDescription());
		 * postResponse.setContent(newPost.getContent());
		 */

		return postResponse;
	}

	@Override
	public PostPaginationSupport getAllPost(int pageNo, int pageSize, String sortBy, String sortDir) {

		// Spring provied sorting method useing for sort

		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending();

		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		Page<Post> posts = postRepository.findAll(pageable);
		List<Post> listOfPost = posts.getContent();

		List<PostDto> content = listOfPost.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());

		PostPaginationSupport paginationSupport = new PostPaginationSupport();
		paginationSupport.setContent(content);
		paginationSupport.setPageNo(posts.getNumber());
		paginationSupport.setPageSize(posts.getSize());
		paginationSupport.setTotalElement(posts.getTotalElements());
		paginationSupport.setTotalPages(posts.getTotalPages());
		paginationSupport.setLast(posts.isLast());

		return paginationSupport;
	}

	@Override
	public PostDto getPostById(long id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		return mapToDTO(post);
	}

	@Override
	public PostDto updatePost(PostDto postDto, long id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		post.setTitle(postDto.getTitle());
		post.setDescription(postDto.getDescription());
		post.setContent(postDto.getContent());

		Post updatedPost = postRepository.save(post);

		return mapToDTO(updatedPost);
	}

	@Override
	public void deletePostById(long id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		postRepository.delete(post);
	}

	// convert Entity into DTO
	private PostDto mapToDTO(Post post) {

		PostDto postDto = modelMapper.map(post, PostDto.class);
		/*
		 * PostDto postDto = new PostDto(); postDto.setId(post.getId());
		 * postDto.setTitle(post.getTitle());
		 * postDto.setDescription(post.getDescription());
		 * postDto.setContent(post.getContent());
		 */
		return postDto;
	}

	// convert DTO to entity
	private Post mapToEntity(PostDto postDto) {
		Post post = modelMapper.map(postDto, Post.class);
		/*
		 * Post post = new Post(); post.setTitle(postDto.getTitle());
		 * post.setDescription(postDto.getDescription());
		 * post.setContent(postDto.getContent());
		 */
		return post;
	}

}

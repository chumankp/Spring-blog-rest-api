package com.ckp.blog.controller;

import javax.validation.Valid;

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

import com.ckp.blog.dto.PostDto;
import com.ckp.blog.dto.PostPaginationSupport;
import com.ckp.blog.service.PostService;
import com.ckp.blog.utils.AppConstants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "CRUD Rest APIs for Post resources")
@RestController
@RequestMapping("/api/posts")
public class PostController {

	private PostService postService;

	public PostController(PostService postService) {
		this.postService = postService;
	}

	@ApiOperation(value = "Create Post REST API")
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto) {
		return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
	}

	@ApiOperation(value = "Get All Posts REST API")
	@GetMapping
	public PostPaginationSupport getAllPosts(
			@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
		return postService.getAllPost(pageNo, pageSize, sortBy, sortDir);
	}

	@ApiOperation(value = "Get Post By Id REST API")
	@GetMapping("/{id}")
	public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") long id) {
		return ResponseEntity.ok(postService.getPostById(id));
	}

	@ApiOperation(value = "Update Post By Id REST API")
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable(name = "id") long id) {
		PostDto postResponce = postService.updatePost(postDto, id);
		return new ResponseEntity<>(postResponce, HttpStatus.OK);
	}

	@ApiOperation(value = "Delete Post By Id REST API")
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletePost(@PathVariable(name = "id") long id) {
		postService.deletePostById(id);
		return new ResponseEntity<>("Your post is deleted successfully.", HttpStatus.OK);

	}
}

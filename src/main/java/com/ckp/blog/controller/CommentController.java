package com.ckp.blog.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ckp.blog.dto.CommentDto;
import com.ckp.blog.service.CommentService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "CRUD REST APIs for Comment Respource")
@RestController
@RequestMapping("/api/")
public class CommentController {

	private CommentService commentService;

	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}

	@ApiOperation(value = "Create Comment REST API")
	@PostMapping("/posts/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(@PathVariable(value = "postId") long postId,
			@Valid @RequestBody CommentDto commentDto) {

		return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
	}

	@ApiOperation(value = "Get All Comments By Post ID REST API")
	@GetMapping("/posts/{postId}/comments")
	public List<CommentDto> getCommentByPostId(@PathVariable(value = "postId") long postId) {
		return commentService.getCommentByPostId(postId);
	}
	
	@ApiOperation(value = "Get Single Comment By ID REST API")
	@GetMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<CommentDto> getCommentById(@PathVariable(value = "postId") long postId,
			@PathVariable(value = "commentId") long commentId) {
		CommentDto commentDto = commentService.getCommentById(postId, commentId);

		return new ResponseEntity<>(commentDto, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Update Comment By ID REST API")
	@PutMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<CommentDto> updateComment(@PathVariable(value = "postId") long postId,
			@PathVariable(value = "commentId") long commentId, @Valid @RequestBody CommentDto commentDto) {

		CommentDto updateComment = commentService.updateCommentById(commentDto, commentId, postId);

		return new ResponseEntity<>(updateComment, HttpStatus.OK);
	}

	@ApiOperation(value = "Delete Comment By ID REST API")
	@DeleteMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<String> deleteComment(@PathVariable(name = "postId") long postId,
			@PathVariable(name = "commentId") long commentId) {
		commentService.deleteCommentById(postId, commentId);
		return new ResponseEntity<>("Your comment is deleted successfully.", HttpStatus.OK);
	}
}

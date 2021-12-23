package com.ckp.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ckp.blog.dto.CommentDto;
import com.ckp.blog.exception.BlogAPIException;
import com.ckp.blog.exception.ResourceNotFoundException;
import com.ckp.blog.model.Comment;
import com.ckp.blog.model.Post;
import com.ckp.blog.repository.CommentRepository;
import com.ckp.blog.repository.PostRepository;
import com.ckp.blog.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	private CommentRepository commentRepository;
	private PostRepository postRepository;
	private ModelMapper modelMapper;

	public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository,
			ModelMapper modelMapper) {
		this.commentRepository = commentRepository;
		this.postRepository = postRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public CommentDto createComment(long postId, CommentDto commentDto) {

		Comment comment = mapToModel(commentDto);
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

		comment.setPost(post);
		Comment newComment = commentRepository.save(comment);

		return mapToDTO(newComment);
	}

	@Override
	public List<CommentDto> getCommentByPostId(long postId) {
		List<Comment> comments = commentRepository.findByPostId(postId);

		return comments.stream().map(comment -> mapToDTO(comment)).collect(Collectors.toList());
	}

	@Override
	public CommentDto getCommentById(long postId, long commentId) {
		Comment comment = postIdAndCommentIdValadation(postId, commentId);
		return mapToDTO(comment);
	}

	@Override
	public CommentDto updateCommentById(CommentDto commentRequest, long commentId, long postId) {
		Comment comment = postIdAndCommentIdValadation(postId, commentId);

		comment.setName(commentRequest.getName());
		comment.setEmail(commentRequest.getEmail());
		comment.setMessageBody(commentRequest.getMessageBody());

		Comment updateComment = commentRepository.save(comment);

		return mapToDTO(updateComment);
	}

	@Override
	public void deleteCommentById(long postId, long commentId) {
		Comment comment = postIdAndCommentIdValadation(postId, commentId);
		commentRepository.delete(comment);
	}

	private CommentDto mapToDTO(Comment comment) {
		CommentDto commentDto = modelMapper.map(comment, CommentDto.class);
		/*
		 * CommentDto commentDto = new CommentDto(); commentDto.setId(comment.getId());
		 * commentDto.setName(comment.getName());
		 * commentDto.setEmail(comment.getEmail());
		 * commentDto.setMessageBody(comment.getMessageBody());
		 */
		return commentDto;
	}

	private Comment mapToModel(CommentDto commentDto) {
		Comment comment = modelMapper.map(commentDto, Comment.class);
		/*
		 * Comment comment = new Comment(); comment.setId(commentDto.getId());
		 * comment.setName(commentDto.getName());
		 * comment.setEmail(commentDto.getEmail());
		 * comment.setMessageBody(commentDto.getMessageBody());
		 */
		return comment;
	}

	private Comment postIdAndCommentIdValadation(long postId, long commentId) {
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

		if (!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belongs to post");
		}
		return comment;
	}

}

package com.ckp.blog.service;

import java.util.List;

import com.ckp.blog.dto.CommentDto;

public interface CommentService {

	CommentDto createComment(long postId, CommentDto commentDto);

	List<CommentDto> getCommentByPostId(long postId);

	CommentDto getCommentById(long postId, long commentId);

	CommentDto updateCommentById(CommentDto commentRequest, long commentId, long postId);

	void deleteCommentById(long postId, long commentId);
}

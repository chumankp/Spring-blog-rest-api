package com.ckp.blog.service;


import com.ckp.blog.dto.PostDto;
import com.ckp.blog.dto.PostPaginationSupport;

public interface PostService {

	PostDto createPost(PostDto postDto);

	PostPaginationSupport getAllPost(int pageNo, int pageSize, String sortBy, String sortDir);

	PostDto getPostById(long id);

	PostDto updatePost(PostDto postDto, long id);

	void deletePostById(long id);
}

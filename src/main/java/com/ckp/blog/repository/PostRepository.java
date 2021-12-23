package com.ckp.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ckp.blog.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
	// we are useing jpa repository methods
}

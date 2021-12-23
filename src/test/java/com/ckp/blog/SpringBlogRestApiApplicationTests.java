package com.ckp.blog;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ckp.blog.model.Post;
import com.ckp.blog.repository.PostRepository;

@SpringBootTest
class SpringBlogRestApiApplicationTests {
	
	@Autowired
	private PostRepository postRepository;

	@Test
	public void createPostTest() {
		Post post = new Post();
		post.setTitle("junit test case for setTitle");
		post.setContent("junit test case for setTitle contant");
		post.setDescription("junit test case for setTitle contant");
		postRepository.save(post);
		assertThat(post.getId()).isGreaterThan(0);
	}


}

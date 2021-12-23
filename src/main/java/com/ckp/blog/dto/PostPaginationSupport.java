package com.ckp.blog.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostPaginationSupport {
	private List<PostDto> content;
	private int pageNo;
	private int pageSize;
	private long totalElement;
	private int totalPages;
	private boolean last;
}

package com.ckp.blog.dto;

import lombok.Data;

@Data
public class LoginDto {
	private String usernameOrEmail;
	private String password;
}

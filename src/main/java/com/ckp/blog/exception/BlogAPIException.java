package com.ckp.blog.exception;

import org.springframework.http.HttpStatus;

public class BlogAPIException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HttpStatus httpStatus;
	private String message;

	public BlogAPIException(HttpStatus httpStatus, String message) {
		this.httpStatus = httpStatus;
		this.message = message;
	}

	public BlogAPIException(String message, HttpStatus httpStatus, String message2) {
		super(message);
		this.httpStatus = httpStatus;
		message = message2;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public String getMessage() {
		return message;
	}

}

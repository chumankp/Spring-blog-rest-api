package com.ckp.blog.error;

import java.util.Date;

import org.springframework.http.HttpStatus;

public class ErrorDetails {
	private Date timestamp;
	private HttpStatus errorcode;
	private String message;
	private String errorDetails;

	public ErrorDetails(Date timestamp, HttpStatus errorcode, String message, String errorDetails) {
		this.timestamp = timestamp;
		this.errorcode = errorcode;
		this.message = message;
		this.errorDetails = errorDetails;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public HttpStatus getErrorcode() {
		return errorcode;
	}

	public String getMessage() {
		return message;
	}

	public String getErrorDetails() {
		return errorDetails;
	}

}

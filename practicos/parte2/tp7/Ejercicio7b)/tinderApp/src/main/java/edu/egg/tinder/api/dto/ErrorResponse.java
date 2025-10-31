package edu.egg.tinder.api.dto;

import java.time.Instant;

import org.springframework.http.HttpStatus;

public class ErrorResponse {

	private final Instant timestamp = Instant.now();
	private final int status;
	private final String error;
	private final String message;

	private ErrorResponse(HttpStatus status, String message) {
		this.status = status.value();
		this.error = status.getReasonPhrase();
		this.message = message;
	}

	public Instant getTimestamp() {
		return timestamp;
	}

	public int getStatus() {
		return status;
	}

	public String getError() {
		return error;
	}

	public String getMessage() {
		return message;
	}

	public static ErrorResponse of(HttpStatus status, String message) {
		return new ErrorResponse(status, message);
	}
}

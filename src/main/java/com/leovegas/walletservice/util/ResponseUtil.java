package com.leovegas.walletservice.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {
	public static <T> ResponseEntity<T> get200OkResponse(T body) {
		return getResponse(HttpStatus.OK.value(), body);
	}

	public static <T> ResponseEntity<T> get400BadRequestResponse(T body) {
		return getResponse(HttpStatus.BAD_REQUEST.value(), body);
	}

	public static <T> ResponseEntity<T> get404NotFoundResponse(T body) {
		return getResponse(HttpStatus.NOT_FOUND.value(), body);
	}

	public static <T> ResponseEntity<T> get500InternalServerErrorResponse(T body) {
		return getResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), body);
	}

	private static <T> ResponseEntity<T> getResponse(int statusCode, T body) {
		return ResponseEntity.status(statusCode).body(body);
	}
}

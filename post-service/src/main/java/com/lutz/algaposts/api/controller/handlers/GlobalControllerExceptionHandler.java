package com.lutz.algaposts.api.controller.handlers;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.nio.channels.ClosedChannelException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.lutz.algaposts.domain.exceptions.BadInputException;
import com.lutz.algaposts.domain.exceptions.ResourceNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalControllerExceptionHandler {

	@ExceptionHandler({
			SocketTimeoutException.class,
			ConnectException.class,
			ClosedChannelException.class
	})
	public ProblemDetail handleNetworkExceptions(IOException ex) {
		ProblemDetail problemDetail = ProblemDetail
				.forStatus(HttpStatus.GATEWAY_TIMEOUT);
		problemDetail.setTitle("Gateway Timeout");
		problemDetail.setDetail(ex.getMessage());
		problemDetail.setType(URI.create("/errors/gateway-timeout"));

		return problemDetail;
	}

	@ExceptionHandler(BadInputException.class)
	public ProblemDetail handleNetworkExceptions(BadInputException ex) {
		ProblemDetail problemDetail = ProblemDetail
				.forStatus(HttpStatus.BAD_REQUEST);
		problemDetail.setTitle("Bad Input");
		problemDetail.setDetail(ex.getMessage());
		problemDetail.setType(URI.create("/errors/bad-input"));

		return problemDetail;
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ProblemDetail handleResourceNotFoundException(ResourceNotFoundException ignored) {
		ProblemDetail problemDetail = ProblemDetail
				.forStatus(HttpStatus.NOT_FOUND);
		problemDetail.setTitle("Resource not found");
		problemDetail.setType(URI.create("/errors/not-found"));

		return problemDetail;
	}

	@ExceptionHandler(Exception.class)
	public ProblemDetail handleGenericException(Exception ex) {
		log.error(ex.getMessage());
		ex.printStackTrace();

		ProblemDetail problemDetail = ProblemDetail
				.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		problemDetail.setTitle("Unexpected error");
		problemDetail.setType(URI.create("/errors/unexpected-error"));

		return problemDetail;
	}
}

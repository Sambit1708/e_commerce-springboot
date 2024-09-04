package com.eCommerce.exception;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.eCommerce.payload.ApiResponseMessage;
import com.eCommerce.payload.ECommerceConstant;

import io.jsonwebtoken.ExpiredJwtException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourcesNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<ApiResponseMessage> resourcesNotFoundException(ResourcesNotFoundException res)
	{
		String message = res.getMessage();
		ApiResponseMessage apiResponse = ApiResponseMessage.builder()
															.status(false)
															.message(message)
															.statusCode(ECommerceConstant.NOT_FOUND)
															.build();
		return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
	}
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<Map<String, String>> handleMethodArgsNotValidException(MethodArgumentNotValidException ex)
	{
		Map<String, String> resp = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> 
		{
			String fieldName = ((FieldError)error).getField();
			String message = error.getDefaultMessage();
			resp.put(fieldName, message);
		});
		
		
		return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ApiResponseMessage> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex)
	{
		String message = ex.getMessage();
		ApiResponseMessage apiResponse = ApiResponseMessage.builder()
															.status(false)
															.message(message)
															.statusCode(ECommerceConstant.BAD_REQUEST)
															.build();
		return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ApiResponseMessage> noResourceFoundException(NoHandlerFoundException ex)
	{
		String message = ex.getMessage();
		ApiResponseMessage apiResponse = ApiResponseMessage.builder()
															.status(false)
															.message(message)
															.statusCode(ECommerceConstant.BAD_REQUEST)
															.build();
		return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(ExpiredJwtException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ResponseEntity<ApiResponseMessage> expiredJwtException(ExpiredJwtException ex)
	{
		String message = "Invalid JwtToken !!";
		ApiResponseMessage apiResponse = ApiResponseMessage.builder()
															.status(false)
															.message(message)
															.statusCode(ECommerceConstant.UNAUTHORIZED)
															.build();
		return new ResponseEntity<>(apiResponse, HttpStatus.UNAUTHORIZED);
	}
	
	
	@ExceptionHandler(ResourcesAlreadyExistException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ApiResponseMessage> resourcesAlreadyExistException(ResourcesAlreadyExistException ex)
	{
		String message = ex.getMessage();
		ApiResponseMessage apiResponse = ApiResponseMessage.builder()
															.status(false)
															.message(message)
															.statusCode(ECommerceConstant.BAD_REQUEST)
															.build();
		return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(DisabledException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ApiResponseMessage> disabledException(DisabledException ex)
	{
		String message = ex.getMessage();
		ApiResponseMessage apiResponse = ApiResponseMessage.builder()
															.status(false)
															.message(message)
															.statusCode(ECommerceConstant.BAD_REQUEST)
															.build();
		return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(BadCredentialsException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ResponseEntity<ApiResponseMessage> badCredentialsException(BadCredentialsException ex)
	{
		String message = ex.getMessage();
		ApiResponseMessage apiResponse = ApiResponseMessage.builder()
															.status(false)
															.message(message)
															.statusCode(ECommerceConstant.UNAUTHORIZED)
															.build();
		return new ResponseEntity<>(apiResponse, HttpStatus.UNAUTHORIZED);
	}
	
	
	@ExceptionHandler(NullPointerException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ApiResponseMessage> nullPointerException(NullPointerException ex)
	{
		String message = ex.getMessage();
		ApiResponseMessage apiResponse = ApiResponseMessage.builder()
															.status(false)
															.message(message)
															.statusCode(ECommerceConstant.BAD_REQUEST)
															.build();
		return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(SQLException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ApiResponseMessage> sQLException(SQLException ex)
	{
		String message = ex.getMessage();
		ApiResponseMessage apiResponse = ApiResponseMessage.builder()
				.status(false)
				.message(message)
				.statusCode(ECommerceConstant.BAD_REQUEST)
				.build();
		return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(CreationFailedException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ApiResponseMessage> creationFailedException(CreationFailedException res)
	{
		String message = res.getMessage();
		ApiResponseMessage apiResponse = ApiResponseMessage.builder()
															.status(false)
															.message(message)
															.statusCode(ECommerceConstant.BAD_REQUEST)
															.build();
		return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
	}
}

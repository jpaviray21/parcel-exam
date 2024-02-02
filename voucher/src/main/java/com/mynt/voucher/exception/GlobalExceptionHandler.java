package com.mynt.voucher.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.mynt.voucher.dto.ErrorResponseDto;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponseDto> handleGlobalException(Exception exception, WebRequest webRequest){
		
		ErrorResponseDto errorResponseDto = new ErrorResponseDto(
				exception.getMessage());
		
		return new ResponseEntity<>(errorResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(VoucherNotExistingException.class)
	public ResponseEntity<ErrorResponseDto> handleVoucherNotExistingException(Exception exception, WebRequest webRequest){
		
		ErrorResponseDto errorResponseDto = new ErrorResponseDto(
				exception.getMessage());
		
		return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
	}
}

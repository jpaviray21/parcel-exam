package com.mynt.parcel.dto;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
@Schema(
		name="Parcel Application Error Response",
		description="Schema for parcel api error responses"
		)
public class ErrorResponseDto {
	
	@Schema(
			description="Invoked API by the client"
			)
	private String apiPath;
	
	@Schema(
			description="Http Error Code"
			)
	private HttpStatus errorCode;
	
	@Schema(
			description="Error Message"
			)
	private String errorMessage;
	
	@Schema(
			description="Date and Time error was encountered"
			)
	private LocalDateTime errorTime;

}

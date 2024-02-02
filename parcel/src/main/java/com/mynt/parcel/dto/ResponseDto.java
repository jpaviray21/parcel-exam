package com.mynt.parcel.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
@Schema(
		name="Parcel Application Response",
		description="Schema for parcel api response"
		)
public class ResponseDto {
	
	@Schema(
			description="Status Code in the response",
			example="200"
			)
	private String statusCode;
	
	@Schema(
			description="Status message in the response",
			example="Rule has been Created!"
			)
	private String statusMessage;

}

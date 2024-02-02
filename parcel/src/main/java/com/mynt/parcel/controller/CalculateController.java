package com.mynt.parcel.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mynt.parcel.dto.CalculateRequestDto;
import com.mynt.parcel.dto.CalculateResponseDto;
import com.mynt.parcel.dto.ErrorResponseDto;
import com.mynt.parcel.service.IParcelService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Tag(
		name="REST API for Parcel Delivery Charge computation",
		description="MYNT REST API for Parcel Delivery Charge computation"
		)
@RestController
@RequestMapping(path="/api/v1", produces= {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Validated
public class CalculateController {

	private IParcelService iParcelService;

	@Operation(
			summary="Calculate Delivery Cost",
			description="Calculates Delivery Cost and can apply voucher!"
			)
	@ApiResponses({
		@ApiResponse(
				responseCode="200", 
				description="HTTP Status OK"
				),
		@ApiResponse(
				responseCode="400", 
				description="HTTP Bad Request",
				content=@Content(
						schema=@Schema(
								implementation=ErrorResponseDto.class
								)
						)
				),
		@ApiResponse(
				responseCode="500", 
				description="HTTP Status Internal Server Error",
				content=@Content(
						schema=@Schema(
								implementation=ErrorResponseDto.class
								)
						)
				)
	})

	@PostMapping(path="/calculateDeliveryCost")
	public ResponseEntity<CalculateResponseDto> calculateDeliveryCost(@Valid @RequestBody CalculateRequestDto calculateRequestDto){
		CalculateResponseDto calculateResponseDto = iParcelService.calculateCost(calculateRequestDto);
		return ResponseEntity.status(HttpStatus.OK).body(calculateResponseDto);

	}


}

package com.mynt.parcel.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mynt.parcel.constants.ParcelConstants;
import com.mynt.parcel.dto.ErrorResponseDto;
import com.mynt.parcel.dto.ResponseDto;
import com.mynt.parcel.dto.RuleRequestDto;
import com.mynt.parcel.dto.UpdateRuleRequestDto;
import com.mynt.parcel.entity.RuleEntity;
import com.mynt.parcel.service.IRuleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Tag(
		name="Admin REST API for Rules Manipulation",
		description="Admin REST API for Rules Manipulation which supports create, update, get and delete"
		)
@RestController
@RequestMapping(path="/api/v1/admin/rule", produces= {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Validated
public class AdminController {

	private IRuleService iRuleService;

	@Operation(
			summary="Create Parcel Rule REST API",
			description="API to create a new rule for the parcel"
			)
	@ApiResponses({
		@ApiResponse(
				responseCode="PAC-200", 
				description="Parcel Application Create - 200 (Success)"
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
	@PostMapping(path="/create")
	public ResponseEntity<ResponseDto> createRule(@Valid @RequestBody RuleRequestDto saveRuleRequestDto){
		iRuleService.saveRule(saveRuleRequestDto);
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseDto(ParcelConstants.SUCCESSFUL_RULE_SAVE_CD.getValue(), 
						ParcelConstants.SUCCESSFUL_RULE_SAVE_MSG.getValue()));

	}

	@Operation(
			summary="Fetch All Parcel Rule REST API",
			description="API to fetch all rules for the parcels"
			)
	@ApiResponse(
			responseCode="200", 
			description="HTTP Status OK"
			)
	@GetMapping(path="/fetchAll")
	public ResponseEntity<List<RuleEntity>> getAllRules(){		
		return ResponseEntity.status(HttpStatus.OK).body(iRuleService.getAllRules());
	}

	@Operation(
			summary="Fetch Parcel Rule REST API by ruleId",
			description="API to fetch rule for the parcels by ruleId"
			)
	@ApiResponse(
			responseCode="200", 
			description="HTTP Status OK"
			)
	@GetMapping(path="/fetch")
	public ResponseEntity<RuleEntity> getRuleById(@RequestParam Long ruleId){	
		return ResponseEntity.status(HttpStatus.OK).body(iRuleService.fetchRule(ruleId));
	}

	@Operation(
			summary="Update Parcel Rule REST API by ruleId",
			description="API to update rule for the parcels using ruleId"
			)
	@ApiResponses({
		@ApiResponse(
				responseCode="PAU-200", 
				description="Parcel Application Update - 200 (Success)"
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
	@PutMapping(path="/update")
	public ResponseEntity<ResponseDto> updateRule(@RequestParam Long ruleId, @RequestBody UpdateRuleRequestDto updateRuleRequestDto){
		iRuleService.updateRule(ruleId, updateRuleRequestDto);
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseDto(ParcelConstants.SUCCESSFUL_RULE_UPDATE_CD.getValue(), 
						ParcelConstants.SUCCESSFUL_RULE_UPDATE_MSG.getValue()));

	}

	@Operation(
			summary="Delete Parcel Rule REST API by ruleId",
			description="API to delete rule for the parcels using ruleId"
			)
	@ApiResponses({
		@ApiResponse(
				responseCode="PAD-200", 
				description="Parcel Application Deletion - 200 (Success)"
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
	@DeleteMapping(path="/delete")
	public ResponseEntity<ResponseDto> deleteRule(@RequestParam Long ruleId){
		iRuleService.deleteRule(ruleId);
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseDto(ParcelConstants.SUCCESSFUL_RULE_DELETE_CD.getValue(), 
						ParcelConstants.SUCCESSFUL_RULE_DELETE_MSG.getValue()));

	}

}

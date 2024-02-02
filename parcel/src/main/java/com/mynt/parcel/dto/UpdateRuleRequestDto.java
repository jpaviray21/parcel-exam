package com.mynt.parcel.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter@Setter@ToString
@Schema(
		name="Rule Update",
		description="Admin Schema for updating rule request to parcel api"
		)
public class UpdateRuleRequestDto {

	private String ruleName;
	
	@Digits(integer=10, fraction=2)
	@Positive 
	private Double limit;
	
	@Digits(integer=10, fraction=2)
	@Positive 
	private BigDecimal charge;	
}

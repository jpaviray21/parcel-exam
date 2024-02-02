package com.mynt.parcel.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@MappedSuperclass
@Getter@Setter@ToString
@Schema(
		name="Rule Creation",
		description="Admin Schema for creating rule request to parcel api"
		)
public class RuleRequestDto {
	
	@Schema(
			description="Rule Name",
			example="XLarge"
			)
	@NotEmpty(message="Rule Name cannot be null or empty")
	private String ruleName;
	
	@Schema(
			description="Rule unit either V-Volume or W-Weight",
			example="V"
			)
	@NotEmpty(message="Unit cannot be null or empty")
	@Size(min=1,max=1, message = "Unit should only be V - Volume and W - Weight")
	private String unit;
	
	@Schema(
			description="Rule limit for Weight limit will be minimum limit while for Volume it will be the maximum limit",
			example="15"
			)
	@Digits(integer=10, fraction=2)
	@Positive 
	private Double limit;
	
	@Schema(
			description="Rule charge will be the multiplier of the parcel's weight or parcel's volume",
			example="30"
			)
	@Digits(integer=10, fraction=2)
	@Positive 
	private BigDecimal charge;	

}

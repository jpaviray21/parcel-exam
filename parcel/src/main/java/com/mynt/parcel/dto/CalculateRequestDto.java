package com.mynt.parcel.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
@Schema(
		name="Calculate Shipping Cost",
		description="Schema for sending calculate request to parcel api"
		)
public class CalculateRequestDto {

	@Schema(
			description="Parcel's weight by Kilogram",
			example="5"
			)
	@NotEmpty(message="Weight cannot be null or empty")
	private double weight;
	
	@Schema(
			description="Parcel's height by Centimeter",
			example="5"
			)
	@NotEmpty(message="Height cannot be null or empty")
	private double height;
	
	@Schema(
			description="Parcel's width by Centimeter",
			example="5"
			)
	@NotEmpty(message="Width cannot be null or empty")
	private double width;
	
	@Schema(
			description="Parcel's length by Centimeter",
			example="5"
			)
	@NotEmpty(message="Length cannot be null or empty")
	private double length;
	
	@Schema(
			description="Voucher for discounts",
			example="MYNT"
			)
	private String voucher;
}

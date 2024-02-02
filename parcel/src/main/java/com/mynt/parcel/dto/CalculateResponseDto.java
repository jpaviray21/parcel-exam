package com.mynt.parcel.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
@Schema(
		name="Calculate Shipping Cost Response",
		description="Schema for calculate response from parcel api"
		)
public class CalculateResponseDto {

	private String parcelCategorization;
	private BigDecimal deliveryCost;
	private String voucher;
	private BigDecimal discount;
	private String voucherExpiration;
	private BigDecimal totalDeliveryCost;
}

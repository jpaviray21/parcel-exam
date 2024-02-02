package com.mynt.voucher.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class VoucherItem {

	private String code;
	private BigDecimal discount;
	private LocalDateTime expiry;
}

package com.mynt.voucher.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mynt.voucher.dto.VoucherItem;
import com.mynt.voucher.service.IVoucherService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path="/api/v1", produces= {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
public class VoucherController {
	
	private IVoucherService iVoucherService;
	
	@GetMapping(path="/voucher")
	public ResponseEntity<VoucherItem> getVoucherDetails(@RequestParam String voucher){
		VoucherItem voucherItem = iVoucherService.getVoucherDetails(voucher);
		return ResponseEntity.status(HttpStatus.OK).body(voucherItem);		
	}

}

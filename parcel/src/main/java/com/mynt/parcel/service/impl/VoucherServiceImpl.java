package com.mynt.parcel.service.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.mynt.parcel.dto.VoucherResponseDto;
import com.mynt.parcel.exception.ExpiredVoucherException;
import com.mynt.parcel.host.IVoucherHostDas;
import com.mynt.parcel.service.IVoucherService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class VoucherServiceImpl implements IVoucherService{
	
	private IVoucherHostDas iVoucherHostDas;

	@Override
	public VoucherResponseDto getVoucherDetails(String voucherCode) {
		VoucherResponseDto voucher = new VoucherResponseDto();		
		
		voucher = iVoucherHostDas.validateVoucher(voucherCode);

		checkVoucherValidity(voucher);		
		return voucher;
	}
	
	private void checkVoucherValidity(VoucherResponseDto voucherResponseDto) {
		if(!LocalDateTime.now().isBefore(voucherResponseDto.getExpiry()))
		throw new ExpiredVoucherException(voucherResponseDto.getExpiry().toString());
	}


}

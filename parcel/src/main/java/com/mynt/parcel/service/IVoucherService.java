package com.mynt.parcel.service;

import com.mynt.parcel.dto.VoucherResponseDto;

public interface IVoucherService {
	
	public VoucherResponseDto getVoucherDetails(String voucherCode);

}

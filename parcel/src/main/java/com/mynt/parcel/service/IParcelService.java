package com.mynt.parcel.service;

import com.mynt.parcel.dto.CalculateRequestDto;
import com.mynt.parcel.dto.CalculateResponseDto;
import com.mynt.parcel.dto.VoucherResponseDto;

public interface IParcelService {
	
	public CalculateResponseDto calculateCost(CalculateRequestDto req);

}

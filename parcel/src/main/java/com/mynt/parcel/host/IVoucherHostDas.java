package com.mynt.parcel.host;

import com.mynt.parcel.dto.VoucherResponseDto;

public interface IVoucherHostDas {
	
	public VoucherResponseDto validateVoucher(String voucherCode);

}

package com.mynt.voucher.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.mynt.voucher.dto.VoucherItem;
import com.mynt.voucher.exception.VoucherNotExistingException;

@Service
public class VoucherServiceImpl implements IVoucherService{

	@Override
	public VoucherItem getVoucherDetails(String voucher) {
		if(voucher.equals("MYNT")) {
			return new VoucherItem("MYNT",new BigDecimal(0.5),LocalDateTime.now().minusDays(-5));
		}else if(voucher.equals("EXPIRED")) {
			return new VoucherItem("EXPIRED",new BigDecimal(100.5),LocalDateTime.now().minusDays(3));
		}else {
			throw new VoucherNotExistingException(voucher);
		}
	}

}

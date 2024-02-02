package com.mynt.voucher.exception;

public class VoucherNotExistingException extends RuntimeException{

	public VoucherNotExistingException(String voucherCode) {
		super(String.format("%s Voucher is not existing!", voucherCode));
	}
}

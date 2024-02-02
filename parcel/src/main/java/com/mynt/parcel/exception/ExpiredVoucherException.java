package com.mynt.parcel.exception;

public class ExpiredVoucherException extends RuntimeException{

	public ExpiredVoucherException(String expiration) {
		super(String.format("Entered voucher is already expired last %s", expiration));
	}
}

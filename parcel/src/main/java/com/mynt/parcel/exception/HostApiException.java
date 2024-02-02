package com.mynt.parcel.exception;

public class HostApiException extends RuntimeException{

	public HostApiException(String hostErrorMessage) {
		super(String.format("Host API returned %s", hostErrorMessage));
	}
}

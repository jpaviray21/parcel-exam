package com.mynt.parcel.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Getter
public class VoucherAPIConfig {

	@Value("${api.mynt.voucher.host:http//localhost:8081}")
	private String host;
	@Value("${api.mynt.voucher.timeout:15000}")
	private int timeOut;
	@Value("${api.mynt.voucher.getdetails.path:/api/v1/voucher}")
	private String getVoucherDetailsPath;
}

package com.mynt.parcel.host.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.util.UriComponentsBuilder;

import com.mynt.parcel.config.RestTemplateClass;
import com.mynt.parcel.config.VoucherAPIConfig;
import com.mynt.parcel.dto.VoucherResponseDto;
import com.mynt.parcel.exception.HostApiException;
import com.mynt.parcel.host.IVoucherHostDas;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class VoucherHostDasImpl implements IVoucherHostDas{

	private RestTemplateClass restTemplate;
	private VoucherAPIConfig voucherAPIConfig;
	
	@Override
	public VoucherResponseDto validateVoucher(String voucherCode) {
		
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<?> entity = new HttpEntity<>(headers);
		
		String urlPath = voucherAPIConfig.getHost()+voucherAPIConfig.getGetVoucherDetailsPath();
		
		String urlTemplate = UriComponentsBuilder
				.fromHttpUrl(urlPath)
				.queryParam("voucher", "{voucherCode}")
				.encode().toUriString();
		
		Map<String, String> params = new HashMap<>();
		params.put("voucherCode", voucherCode);
		
		try {
			ResponseEntity<VoucherResponseDto> response = restTemplate.exchange(
			        urlTemplate,
			        HttpMethod.GET,
			        entity,
			        VoucherResponseDto.class,
			        params
			);
			
			return response.getBody();
		}catch(HttpClientErrorException ex) {
			throw new HostApiException(ex.getMessage());
		}
	}

}

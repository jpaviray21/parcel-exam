package com.mynt.parcel.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mynt.parcel.dto.CalculateRequestDto;
import com.mynt.parcel.dto.CalculateResponseDto;
import com.mynt.parcel.dto.VoucherResponseDto;
import com.mynt.parcel.entity.RuleEntity;
import com.mynt.parcel.exception.ParcelRejectedException;
import com.mynt.parcel.service.IParcelService;
import com.mynt.parcel.service.IRuleService;
import com.mynt.parcel.service.IVoucherService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ParcelServiceImpl implements IParcelService{

	private IRuleService iRuleService;
	private IVoucherService iVoucherService;

	@Override
	public CalculateResponseDto calculateCost(CalculateRequestDto req) {

		CalculateResponseDto response = new CalculateResponseDto();
		Double volume = calculateVolume(req.getHeight(),req.getLength(),req.getWidth());

		System.out.println("Volume:" + volume);

		List<RuleEntity> sortedweightRules = iRuleService.getWeightRulesSortedByLimitDesc();

		for(RuleEntity rule:sortedweightRules)	{	
			if(rule.getLimits()<req.getWeight() && rule.getCharge()==null) {
				throw new ParcelRejectedException(req.getWeight(), rule.getLimits());

			}else if(rule.getLimits()<req.getWeight() && rule.getCharge()!=null) {
				response.setParcelCategorization(rule.getRuleName());
				response.setDeliveryCost(calculateCharge(rule.getCharge(),req.getWeight()));
				return applyVoucher(req.getVoucher(),response);

			}
		}

		List<RuleEntity> sortedvolumeRules = iRuleService.getVolumeRulesSortedByLimitAsc();
					
		for(RuleEntity rule:sortedvolumeRules)	{		
			
			if(rule.getLimits()==null) {
				System.out.println("Comparing " + sortedvolumeRules.get(sortedvolumeRules.size()-1).getLimits()
				+ " is Less Than " + volume);
				if(sortedvolumeRules.get(sortedvolumeRules.size()-1).getLimits()<volume) {
					response.setParcelCategorization(rule.getRuleName());
					response.setDeliveryCost(calculateCharge(rule.getCharge(),volume));
					return applyVoucher(req.getVoucher(),response);
				}	
			}else {
				System.out.println("Comparing " + rule.getLimits()
				+ " is Greater Than " + volume);
				if(rule.getLimits()>volume) {
					response.setParcelCategorization(rule.getRuleName());
					response.setDeliveryCost(calculateCharge(rule.getCharge(),volume));	
					return applyVoucher(req.getVoucher(),response);
				}
			}
			
		}
		return response;

	}
	
	private CalculateResponseDto applyVoucher(String voucherCode, CalculateResponseDto response) {
		
		if(voucherCode!=null && voucherCode != "") {
			VoucherResponseDto voucherDto = iVoucherService.getVoucherDetails(voucherCode);
			
			response.setDiscount(voucherDto.getDiscount());
			response.setVoucher(voucherDto.getCode());
			response.setVoucherExpiration(voucherDto.getExpiry().toString());
			response.setTotalDeliveryCost(calculateTotalDeliveryCost(response.getDeliveryCost(),voucherDto.getDiscount()));			
		}else {
			response.setTotalDeliveryCost(response.getDeliveryCost());
		}
		return response;
	}


	private double calculateVolume(double height, double length, double width) {	
		return height * length * width;
	}

	private BigDecimal calculateCharge(BigDecimal charge, double multiplier) {
		return  charge.multiply(BigDecimal.valueOf(multiplier));
	}
	
	private BigDecimal calculateTotalDeliveryCost(BigDecimal charge, BigDecimal discount) {
		BigDecimal totalCost = charge.subtract(discount);
		return totalCost.compareTo(BigDecimal.ZERO)<0?BigDecimal.ZERO:totalCost;
	}

}

package com.mynt.parcel.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mynt.parcel.dto.CalculateRequestDto;
import com.mynt.parcel.dto.VoucherResponseDto;
import com.mynt.parcel.entity.RuleEntity;
import com.mynt.parcel.exception.HostApiException;
import com.mynt.parcel.exception.NoRulesException;
import com.mynt.parcel.exception.ParcelRejectedException;
import com.mynt.parcel.service.IRuleService;
import com.mynt.parcel.service.IVoucherService;

public class ParcelServiceImplTest {
	
	ParcelServiceImpl parcelServiceImpl;
	IRuleService iRuleServiceMock;
	IVoucherService iVoucherServiceMock;
	
	@BeforeEach
	public void setup(){
		iRuleServiceMock = mock(IRuleService.class);
		iVoucherServiceMock = mock(IVoucherService.class);
		parcelServiceImpl = new ParcelServiceImpl(iRuleServiceMock, iVoucherServiceMock);
	}

	@Test
	public void testCalculateCost_Weight_NoVoucher_SUCCESS() {

		when(iRuleServiceMock.getWeightRulesSortedByLimitDesc()).thenReturn(getWeightRuleList());
		assertEquals(parcelServiceImpl.calculateCost(succesfulRequestForWeight_NoVoucher()).getTotalDeliveryCost(), new BigDecimal(16.50).setScale(2, RoundingMode.UP));
	}
	
	@Test
	public void testCalculateCost_Volume_NoVoucher_SUCCESS() {

		when(iRuleServiceMock.getVolumeRulesSortedByLimitAsc()).thenReturn(geVolumetRuleList());
		assertEquals(parcelServiceImpl.calculateCost(succesfulRequestForVolume_NoVoucher()).getTotalDeliveryCost(), new BigDecimal(125).setScale(1, RoundingMode.UP));

	}
	
	@Test
	public void testCalculateCost_Weight_Voucher_SUCCESS() {

		when(iRuleServiceMock.getWeightRulesSortedByLimitDesc()).thenReturn(getWeightRuleList());
		when(iVoucherServiceMock.getVoucherDetails(anyString())).thenReturn(voucherDetail());
		assertEquals(parcelServiceImpl.calculateCost(succesfulRequestForWeight_Voucher()).getTotalDeliveryCost(), new BigDecimal(15.50).setScale(2, RoundingMode.UP));
	}
	
	@Test
	public void testCalculateCost_Volume_Voucher_SUCCESS() {

		when(iRuleServiceMock.getVolumeRulesSortedByLimitAsc()).thenReturn(geVolumetRuleList());
		when(iVoucherServiceMock.getVoucherDetails(anyString())).thenReturn(voucherDetail());
		assertEquals(parcelServiceImpl.calculateCost(succesfulRequestForVolume_Voucher()).getTotalDeliveryCost(), new BigDecimal(124).setScale(1, RoundingMode.UP));

	}
	
	@Test
	public void testCalculateCost_Volume_Voucher_UpperLimit_SUCCESS() {

		when(iRuleServiceMock.getVolumeRulesSortedByLimitAsc()).thenReturn(geVolumetRuleList());
		when(iVoucherServiceMock.getVoucherDetails(anyString())).thenReturn(voucherDetail());
		assertEquals(parcelServiceImpl.calculateCost(succesfulRequestForVolumeUpperLimit_Voucher()).getTotalDeliveryCost(), new BigDecimal(124999).setScale(1, RoundingMode.UP));

	}
	
	@Test
	public void testCalculateCost_Weight_Voucher_ButApiIsDown_FAILED() {

		when(iRuleServiceMock.getWeightRulesSortedByLimitDesc()).thenReturn(getWeightRuleList());
		when(iVoucherServiceMock.getVoucherDetails(anyString())).thenThrow(HostApiException.class);
		assertThrows(HostApiException.class,()-> parcelServiceImpl.calculateCost(succesfulRequestForWeight_Voucher()));
	}
	
	@Test
	public void testCalculateCost_Weight_NoVoucher_ExceedWeightLimit_FAILED() {

		when(iRuleServiceMock.getWeightRulesSortedByLimitDesc()).thenReturn(getWeightRuleList());
		assertThrows(ParcelRejectedException.class,()-> parcelServiceImpl.calculateCost(failedExceedLimitRequestForWeight_NoVoucher()));
	}
	
	@Test
	public void testCalculateCost_Volume_VoucherHaveBigDiscount_SUCCESS() {

		when(iRuleServiceMock.getVolumeRulesSortedByLimitAsc()).thenReturn(geVolumetRuleList());
		when(iVoucherServiceMock.getVoucherDetails(anyString())).thenReturn(bigDiscountVoucherDetail());
		assertEquals(parcelServiceImpl.calculateCost(succesfulRequestForVolume_Voucher()).getTotalDeliveryCost(), new BigDecimal(0));

	}
	
	private CalculateRequestDto succesfulRequestForVolume_NoVoucher() {
		return new CalculateRequestDto(5,5,5,5,"");
	}
	
	private CalculateRequestDto succesfulRequestForWeight_NoVoucher() {
		return new CalculateRequestDto(11,5,5,5,"");
	}
	
	private CalculateRequestDto succesfulRequestForVolume_Voucher() {
		return new CalculateRequestDto(5,5,5,5,"MYNT");
	}
	
	private CalculateRequestDto succesfulRequestForVolumeUpperLimit_Voucher() {
		return new CalculateRequestDto(5,50,50,50,"MYNT");
	}
	
	private CalculateRequestDto succesfulRequestForWeight_Voucher() {
		return new CalculateRequestDto(11,5,5,5,"MYNT");
	}
	
	private CalculateRequestDto failedExceedLimitRequestForWeight_NoVoucher() {
		return new CalculateRequestDto(110,5,5,5,null);
	}
	
	private List<RuleEntity> getWeightRuleList(){
		
		List<RuleEntity> rules = new ArrayList<>();
		RuleEntity rule = new RuleEntity(Long.parseLong("1"),"Rule1",50.0,"W",null);
		RuleEntity rule1 = new RuleEntity(Long.parseLong("2"),"Rule1",10.0,"W",new BigDecimal(1.5));
		rules.add(rule);
		rules.add(rule1);
		return rules;
	}
	
	private List<RuleEntity> geVolumetRuleList(){
		
		List<RuleEntity> rules = new ArrayList<>();
		RuleEntity rule2 = new RuleEntity(Long.parseLong("3"),"Rule1",1500.0,"V",new BigDecimal(1.0));
		RuleEntity rule3 = new RuleEntity(Long.parseLong("4"),"Rule1",2500.0,"V",new BigDecimal(1.0));
		RuleEntity rule4 = new RuleEntity(Long.parseLong("5"),"Rule1",null,"V",new BigDecimal(1.0));
		rules.add(rule3);
		rules.add(rule2);
		rules.add(rule4);
		return rules;
	}
	
	private VoucherResponseDto voucherDetail() {
		return new VoucherResponseDto("MYNT",new BigDecimal(1),LocalDateTime.now().minusDays(-1));
	}
	
	private VoucherResponseDto bigDiscountVoucherDetail() {
		return new VoucherResponseDto("MYNT",new BigDecimal(100000000),LocalDateTime.now().minusDays(-1));
	}
}

package com.mynt.parcel.service;

import java.util.List;

import com.mynt.parcel.dto.RuleRequestDto;
import com.mynt.parcel.dto.UpdateRuleRequestDto;
import com.mynt.parcel.entity.RuleEntity;

public interface IRuleService {
	
	public List<RuleEntity> getWeightRulesSortedByLimitDesc();
	
	public List<RuleEntity> getVolumeRulesSortedByLimitAsc();
	
	public List<RuleEntity> getAllRules();
	
	public void saveRule(RuleRequestDto saveRuleRequestDto);
	
	public RuleEntity fetchRule (Long ruleId);
	
	public void updateRule(Long ruleId, UpdateRuleRequestDto saveRuleRequestDto);
	
	public void deleteRule(Long ruleId);

}

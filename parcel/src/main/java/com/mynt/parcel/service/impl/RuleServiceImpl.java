package com.mynt.parcel.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mynt.parcel.constants.ParcelConstants;
import com.mynt.parcel.dto.RuleRequestDto;
import com.mynt.parcel.dto.UpdateRuleRequestDto;
import com.mynt.parcel.entity.RuleEntity;
import com.mynt.parcel.exception.CannotDeleteVolumeLimit;
import com.mynt.parcel.exception.CannotDeleteWeightLimit;
import com.mynt.parcel.exception.InvalidUnitException;
import com.mynt.parcel.exception.NoRulesException;
import com.mynt.parcel.exception.NothingWasUpdated;
import com.mynt.parcel.exception.RuleAlreadyExistsException;
import com.mynt.parcel.exception.RuleDoesNotExistException;
import com.mynt.parcel.exception.RuleLimitExistsException;
import com.mynt.parcel.exception.RuleWeightLimitExceededException;
import com.mynt.parcel.repository.RuleRepository;
import com.mynt.parcel.service.IRuleService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RuleServiceImpl implements IRuleService{

	private RuleRepository ruleRepository;

	@Override
	public List<RuleEntity> getWeightRulesSortedByLimitDesc() {
		List<RuleEntity> rules = ruleRepository.findByUnitOrderByLimitsDesc(ParcelConstants.WEIGHT_CODE.getValue());
		
		if(rules.isEmpty())
			throw new NoRulesException();
		
		return rules;
	}

	@Override
	public List<RuleEntity> getVolumeRulesSortedByLimitAsc() {
		
		List<RuleEntity> rules = ruleRepository.findByUnitOrderByLimitsAsc(ParcelConstants.VOLUME_CODE.getValue());
		
		if(rules.isEmpty())
			throw new NoRulesException();
		
		return rules;
	}

	@Override
	public void saveRule(RuleRequestDto saveRuleRequestDto) {
		
		RuleEntity ruleEntity = new RuleEntity();		

		if(saveRuleRequestDto.getUnit().equalsIgnoreCase(ParcelConstants.WEIGHT_CODE.getValue())) {
			
			List<RuleEntity> rules = ruleRepository
					.findByUnitOrderByLimitsDesc(ParcelConstants.WEIGHT_CODE.getValue());
			
			validateIfRuleNameExists(saveRuleRequestDto.getRuleName(), rules);
			validateLimitExists(saveRuleRequestDto.getLimit(), rules);
			
			RuleEntity weightLimit = ruleRepository
					.findWeightLimit();
						
			ruleEntity.setRuleName(saveRuleRequestDto.getRuleName());
			ruleEntity.setUnit(ParcelConstants.WEIGHT_CODE.getValue());
			ruleEntity.setLimits(saveRuleRequestDto.getLimit());
			ruleEntity.setCharge(saveRuleRequestDto.getCharge());
			ruleEntity.setCreatedBy("ADMIN");
			ruleEntity.setCreatedDate(LocalDateTime.now());
			
			validateIfRuleExceedsWeightLimit(ruleEntity, weightLimit);
		}else if(saveRuleRequestDto.getUnit().equalsIgnoreCase(ParcelConstants.VOLUME_CODE.getValue())) {
			
			List<RuleEntity> rules = ruleRepository
					.findByUnitOrderByLimitsDesc(ParcelConstants.VOLUME_CODE.getValue());
			
			validateIfRuleNameExists(saveRuleRequestDto.getRuleName(), rules);
			
			List<RuleEntity> rulesWithMaxLimits = rules.stream()
					.filter(r->r.getLimits()!=null).toList();
			
			validateLimitExists(saveRuleRequestDto.getLimit(), rulesWithMaxLimits);
			
			RuleEntity lastVolumeWithLimit = rulesWithMaxLimits.get(rulesWithMaxLimits.size()-1);
			
			RuleEntity volumeLimit = ruleRepository
					.findVolumeLimit();
			
			ruleEntity.setRuleName(saveRuleRequestDto.getRuleName());
			ruleEntity.setUnit(ParcelConstants.VOLUME_CODE.getValue());
			ruleEntity.setLimits(saveRuleRequestDto.getLimit());
			ruleEntity.setCharge(saveRuleRequestDto.getCharge());
			ruleEntity.setCreatedBy("ADMIN");
			ruleEntity.setCreatedDate(LocalDateTime.now());
			
			if(ruleEntity.getLimits()>lastVolumeWithLimit.getLimits()) {
				volumeLimit.setLimits(ruleEntity.getLimits());
				ruleRepository.save(volumeLimit);
				ruleEntity.setLimits(null);
			}
		}else {
			throw new InvalidUnitException(saveRuleRequestDto.getUnit());
		}
			
		ruleRepository.save(ruleEntity);		
	}
	
	@Override
	public RuleEntity fetchRule(Long ruleId) {
		Optional<RuleEntity> rule = ruleRepository.findById(ruleId);
		if(rule.isPresent())
			return rule.get();
		else 
			throw new RuleDoesNotExistException(ruleId);
	}
	
	@Override
	public List<RuleEntity> getAllRules() {
		List<RuleEntity> allRules = new ArrayList<>();
		List<RuleEntity> weightRules = getWeightRulesSortedByLimitDesc();
		List<RuleEntity> volumeRules = getVolumeRulesSortedByLimitAsc();
		allRules.addAll(weightRules);
		allRules.addAll(volumeRules);		
		return allRules;
	}
	
	@Override
	public void updateRule(Long ruleId, UpdateRuleRequestDto updateRuleRequestDto) {
		Optional<RuleEntity> rule = ruleRepository.findById(ruleId);
		Boolean updated = false;

		if(!rule.isPresent())
			throw new RuleDoesNotExistException(ruleId);
		
		RuleEntity existingRule = rule.get();
		
		if(rule.get().getUnit().equals(ParcelConstants.WEIGHT_CODE.getValue())) {
			RuleEntity weightLimit = ruleRepository
					.findWeightLimit();
			List<RuleEntity> rules = ruleRepository
					.findByUnitOrderByLimitsDesc(ParcelConstants.WEIGHT_CODE.getValue());
			
			if(updateRuleRequestDto.getLimit()!=null) {
				validateIfRuleExceedsWeightLimit(rule.get(),weightLimit);
				validateLimitExists(updateRuleRequestDto.getLimit(), rules);

				existingRule.setLimits(updateRuleRequestDto.getLimit());
				updated = true;
			}

		}else{			
			if(updateRuleRequestDto.getLimit()!=null) {
				List<RuleEntity> rulesWithMaxLimits = ruleRepository
						.findByUnitOrderByLimitsAsc(ParcelConstants.VOLUME_CODE.getValue()).stream()
						.filter(r->r.getLimits()!=null).toList();
				
				validateLimitExists(updateRuleRequestDto.getLimit(), rulesWithMaxLimits);
				
				RuleEntity lastVolumeWithLimit = rulesWithMaxLimits.get(rulesWithMaxLimits.size()-1);				
				
				if(updateRuleRequestDto.getLimit()>lastVolumeWithLimit.getLimits()) {
					lastVolumeWithLimit.setLimits(updateRuleRequestDto.getLimit());
					lastVolumeWithLimit.setUpdatedBy("ADMIN");
					lastVolumeWithLimit.setUpdatedDate(LocalDateTime.now());
					ruleRepository.save(lastVolumeWithLimit);
					existingRule.setLimits(null);
					updated = true;
				}else {
					existingRule.setLimits(updateRuleRequestDto.getLimit());
					updated = true;	
				}
				
			}
			
		}			
			
		if(updateRuleRequestDto.getRuleName()!=null) {
			validateIfRuleNameExists(updateRuleRequestDto.getRuleName(),
					ruleRepository.findAll());
			existingRule.setRuleName(updateRuleRequestDto.getRuleName());
			updated = true;
		}
			
		if(updateRuleRequestDto.getCharge()!=null) {
			existingRule.setCharge(updateRuleRequestDto.getCharge());
			updated = true;
		}		
		
		if(updated) {
			existingRule.setUpdatedBy("ADMIN");
			existingRule.setUpdatedDate(LocalDateTime.now());
			
			ruleRepository.save(existingRule);	
		}else {
			throw new NothingWasUpdated(ruleId);
		}		
	}
	
	@Override
	public void deleteRule(Long ruleId) {
		Optional<RuleEntity> rule = ruleRepository.findById(ruleId);
		if(!rule.isPresent())
			throw new RuleDoesNotExistException(ruleId);
				
		if(rule.get().getCharge()==null)
			throw new CannotDeleteWeightLimit(ruleId);
		
		if(rule.get().getLimits()==null)
			throw new CannotDeleteVolumeLimit(ruleId);
		
		ruleRepository.deleteById(ruleId);
		
	}
	
	private void validateIfRuleNameExists(String ruleName, List<RuleEntity> rules) {
		if(!rules.stream().filter(r->r.getRuleName().equals(ruleName)).toList().isEmpty())
			throw new RuleAlreadyExistsException(ruleName);
	}
	
	private void validateIfRuleExceedsWeightLimit(RuleEntity rule, RuleEntity ruleLimit) {
		if(rule.getLimits()>ruleLimit.getLimits())
			throw new RuleWeightLimitExceededException(rule.getLimits().toString(), ruleLimit.getLimits().toString());
	}
	
	private void validateLimitExists(Double ruleLimit, List<RuleEntity> rules) {
		if(!rules.stream().filter(r->r.getLimits().equals(ruleLimit)).toList().isEmpty())
			throw new RuleLimitExistsException(ruleLimit.toString());
	}


}

package com.mynt.parcel.entity;

import java.math.BigDecimal;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name="rules")
@Table(name="rules")
@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor
public class RuleEntity extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="native")
	@GenericGenerator(name="native",strategy="native")
	@Column(name="rule_id")
	private Long ruleId;
	private String ruleName;
	private Double limits;
	private String unit;
	private BigDecimal charge;

}

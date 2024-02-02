package com.mynt.parcel.entity;

import java.time.LocalDateTime;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@MappedSuperclass
@Getter@Setter@ToString
public class BaseEntity {

	private LocalDateTime updatedDate;
	private String updatedBy;
	private LocalDateTime createdDate;
	private String createdBy;
}

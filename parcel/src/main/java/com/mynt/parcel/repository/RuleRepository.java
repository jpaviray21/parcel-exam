package com.mynt.parcel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mynt.parcel.entity.RuleEntity;

@Repository
public interface RuleRepository extends JpaRepository<RuleEntity, Long> {
	
	public List<RuleEntity> findByUnitOrderByLimitsDesc(String unit);
	
	public List<RuleEntity> findByUnitOrderByLimitsAsc(String unit);
	
	@Query("SELECT r FROM rules r WHERE r.unit = 'W' AND r.charge is null")
	public RuleEntity findWeightLimit();
	
	@Query("SELECT r FROM rules r WHERE r.unit = 'V'  AND r.limits is null")
	public RuleEntity findVolumeLimit();
	

	/**NAME		|	MIN		|	MAX		|	UNIT	|	CHARGE	|	IsReject	|
	 * 
	 * REJECT	|	50		|			|	W		|			|	YES			|
	 * HEAVY	|	11		|	50		|	W		|	20		|	NO			|
	 * SMALL	|	1		|	1500	|	V		|	0.03	|	NO			|
	 * MEDIUM	|	1500	|	2500	|	V		|	0.04	|	NO			|
	 * LARGE	|	2500	|			|	V		|	0.05	|	NO			|
	 * XLARGE	|	
	 * 
	 */

}

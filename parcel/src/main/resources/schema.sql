CREATE TABLE IF NOT EXISTS `rules` (
  `rule_id` int AUTO_INCREMENT  PRIMARY KEY,
  `rule_name` varchar(100) NOT NULL,
  `limits` float DEFAULT NULL,
  `unit` varchar(1) NOT NULL,
  `charge` float DEFAULT NULL,
  `created_date` date NOT NULL,
  `created_by` varchar(20) NOT NULL,
  `updated_date` date DEFAULT NULL,
  `updated_by` varchar(20) DEFAULT NULL
);

INSERT into rules (rule_name,limits,unit,charge,created_date,created_by) VALUES
('REJECT',50.0,'W',null,CURRENT_TIMESTAMP(),'admin');
INSERT into rules (rule_name,limits,unit,charge,created_date,created_by) VALUES
('HEAVY',10.0,'W',20.0,CURRENT_TIMESTAMP(),'admin');
INSERT into rules (rule_name,limits,unit,charge,created_date,created_by) VALUES
('SMALL',1500.0,'V',0.03,CURRENT_TIMESTAMP(),'admin');
INSERT into rules (rule_name,limits,unit,charge,created_date,created_by) VALUES
('MEDIUM',2500.0,'V',0.04,CURRENT_TIMESTAMP(),'admin');
INSERT into rules (rule_name,limits,unit,charge,created_date,created_by) VALUES
('LARGE',null,'V',0.05,CURRENT_TIMESTAMP(),'admin');




	/**NAME		|	MIN		|	MAX		|	UNIT	|	CHARGE	|	IsReject	|
	 * 
	 * REJECT	|	50		|			|	W		|			|	YES			|
	 * HEAVY	|	10		|	50>		|	W		|	20		|	NO			|
	 * SMALL	|			|	1500>	|	V		|	0.03	|	NO			|
	 * MEDIUM	|			|	2500>	|	V		|	0.04	|	NO			|
	 * LARGE	|			|			|	V		|	0.05	|	NO			|
	 * 
	 * 
	 */
package com.ashokit.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "STATE_MASTER")
public class State implements Serializable {

	@Id
	@GeneratedValue
	@Column(name = "STATE_ID")
	private Integer stateId;
	
	@Column(name = "STATE_NAME")
	private String stateName;
	
	@Column(name = "COUNTRY_ID")
	private Integer countryId;
}

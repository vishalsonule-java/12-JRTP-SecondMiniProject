package com.ashokit.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "CITY_MASTER")
public class City implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6848572846958111985L;

	@Id
	@GeneratedValue
	@Column(name = "CITY_ID")
	private Integer cityId;

	@Column(name = "CITY_NAME")
	private String cityName;
	
	@Column(name="STATE_ID")
	private Integer stateId;

}

package com.ashokit.entity;

import java.io.Serializable;
import java.util.List;

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
@Table(name = "COUNTRY_MASTER")
public class Country implements Serializable {
	@Id
	@GeneratedValue
	@Column(name = "COUNTRY_ID")
	private Integer countryId;

	@Column(name = "COUNTRY_NAME")
	private String countryName;
}

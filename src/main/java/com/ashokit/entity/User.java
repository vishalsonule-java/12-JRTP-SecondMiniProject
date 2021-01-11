package com.ashokit.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "USER_MASTER")
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2754213012092011898L;

	@Id
	@Column(name = "USER_ID")
	@GeneratedValue
	private Integer userId;

	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "LAST_NAME")
	private String lastName;

	@Column(name = "EMAIL")
	private String email;
	
	@Column(name="PASSWORD")
	private String password;

	@Column(name = "MOBILE_NO")
	private Long mobileNo;

	@Column(name = "DATE_OF_BIRTH")
	private Date dob;

	@Column(name = "GENDER")
	private String gender;

	@Column(name = "COUNTRY")
	private Integer country;

	@Column(name = "STATE")
	private Integer state;

	@Column(name = "CITY")
	private Integer city;
	
	@Column(name="STATUS")
	private String status;
}

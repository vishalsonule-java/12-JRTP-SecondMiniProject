package com.ashokit.repo;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ashokit.entity.City;

public interface CityRepository extends JpaRepository<City,Serializable> {
	
	public List<City> findByStateId(Integer state);

}

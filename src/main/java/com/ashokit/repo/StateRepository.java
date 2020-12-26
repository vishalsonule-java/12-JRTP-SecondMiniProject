package com.ashokit.repo;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ashokit.entity.State;

public interface StateRepository extends JpaRepository<State, Serializable> {

	public List<State> findByCountryId(Integer country);

}

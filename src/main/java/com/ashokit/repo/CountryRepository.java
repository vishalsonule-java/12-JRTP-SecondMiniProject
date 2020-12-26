package com.ashokit.repo;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ashokit.entity.Country;

public interface CountryRepository extends JpaRepository<Country, Serializable> {

}

package com.rest.ets.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.ets.entity.Batch;

public interface BatchRepository extends JpaRepository<Batch, String>{

}

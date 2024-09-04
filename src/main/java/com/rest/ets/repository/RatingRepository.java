package com.rest.ets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rest.ets.entity.Rating;
@Repository
public interface RatingRepository extends JpaRepository<Rating, String>{

}

package com.rest.ets.service;

import org.springframework.stereotype.Service;

import com.rest.ets.exception.RatingNotFoundByIdException;
import com.rest.ets.mapper.RatingMapper;
import com.rest.ets.repository.RatingRepository;
import com.rest.ets.requestdto.RatingRequest;
import com.rest.ets.responsedto.RatingResponse;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RatingService {
	
	private RatingRepository ratingRepository;
	private RatingMapper ratingMapper;

	

	public RatingResponse updateStudentRating(RatingRequest request, String ratingId) {
		return ratingRepository.findById(ratingId).map(rating->{
			rating=ratingMapper.mapToRatingEntity(request, rating);
			rating=ratingRepository.save(rating);
			return ratingMapper.mapToRatingResponseEntity(rating);
			}).orElseThrow(()->new RatingNotFoundByIdException("failed to update student rating"));
		
	}

}

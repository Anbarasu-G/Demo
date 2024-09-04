package com.rest.ets.mapper;

import org.springframework.stereotype.Component;

import com.rest.ets.entity.Rating;
import com.rest.ets.requestdto.RatingRequest;
import com.rest.ets.responsedto.RatingResponse;

@Component
public class RatingMapper {
	
	public Rating mapToRatingEntity(RatingRequest ratingRequest,Rating rating) {
		rating.setRating(ratingRequest.getRating());
		rating.setFeedback(ratingRequest.getFeedback());
		return rating;
	}
	
	public RatingResponse mapToRatingResponseEntity(Rating rating) {
		RatingResponse response=new RatingResponse();
		response.setRatingId(rating.getRatingId());
		response.setSubject(rating.getSubject());
		response.setRating(rating.getRating());
		response.setFeedback(rating.getFeedback());
	
		return response;
	}

}

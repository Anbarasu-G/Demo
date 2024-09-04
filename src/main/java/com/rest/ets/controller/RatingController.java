package com.rest.ets.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rest.ets.exception.RatingNotFoundByIdException;
import com.rest.ets.requestdto.RatingRequest;
import com.rest.ets.responsedto.RatingResponse;
import com.rest.ets.service.RatingService;
import com.rest.ets.util.AppResponseBuilder;
import com.rest.ets.util.ResponseStructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class RatingController {
	private RatingService ratingService;
	private AppResponseBuilder responseBuilder;
	
	@Operation(description = "This end point is used to update the rating by using rating id to the database",responses = {
			@ApiResponse(responseCode = "200",description = "update rating successfully"),
			@ApiResponse(responseCode = "404",description = "failed to update the batch",content = {@Content(schema = @Schema(anyOf = RatingNotFoundByIdException.class))})
	})
	@PutMapping("/ratings/{ratingId}")
	public ResponseEntity<ResponseStructure<RatingResponse>> updateStudentRating(@RequestBody @Valid RatingRequest request,@PathVariable String ratingId){
		RatingResponse ratingResponse = ratingService.updateStudentRating(request,ratingId);
		return responseBuilder.success(HttpStatus.OK, "rating updated successfully", ratingResponse);
	}

}

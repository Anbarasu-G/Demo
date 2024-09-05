package com.rest.ets.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rest.ets.enums.Stack;
import com.rest.ets.enums.UserRole;
import com.rest.ets.exception.UserNotFoundByIdException;
import com.rest.ets.requestdto.StudentRequest;
import com.rest.ets.requestdto.TrainerRequest;
import com.rest.ets.responsedto.RatingResponse;
import com.rest.ets.responsedto.StudentResponse;
import com.rest.ets.responsedto.UserResponse;
import com.rest.ets.security.RegistrationRequest;
import com.rest.ets.service.UserService;
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
public class UserController {
	private UserService userService;
	private AppResponseBuilder responseBuilder;
	
	@Operation(description = "This end point is used to save the admin to the database",responses = {
			@ApiResponse(responseCode = "201",description = "admin is created successfully"),
			@ApiResponse(responseCode = "500",description = "internal server error",content = {@Content(schema = @Schema(anyOf = RuntimeException.class))})})
	@PostMapping("/admins/register")
	public ResponseEntity<ResponseStructure<UserResponse>> saveAdmin(@RequestBody @Valid RegistrationRequest registrationRequest) {
		UserResponse adminResponse=userService.registerUser(registrationRequest,UserRole.ADMIN);
		return responseBuilder.success(HttpStatus.CREATED,"Admin created Successfully", adminResponse);
	}
	
	@Operation(description = "This end point is used to save the hr to the database",responses = {
			@ApiResponse(responseCode = "201",description = "hr is created successfully"),
			@ApiResponse(responseCode = "500",description = "internal server error",content = {@Content(schema = @Schema(anyOf = RuntimeException.class))})})
	@PostMapping("/hrs/register")
	public ResponseEntity<ResponseStructure<UserResponse>>saveHr(@RequestBody @Valid RegistrationRequest registrationRequest){
		UserResponse hrResponse=userService.registerUser(registrationRequest,UserRole.HR);
		return responseBuilder.success(HttpStatus.CREATED, "Hr created Successfully", hrResponse);
	}
	
	@Operation(description = "This end point is used to save the trainer to the database",responses = {
			@ApiResponse(responseCode = "201",description = "trainer is created successfully"),
			@ApiResponse(responseCode = "500",description = "internal server error",content = {@Content(schema = @Schema(anyOf = RuntimeException.class))})})
	@PostMapping("/trainers/register")
	public ResponseEntity<ResponseStructure<UserResponse>> saveTrainer(@RequestBody @Valid RegistrationRequest registrationRequest){
		UserResponse response=userService.registerUser(registrationRequest,UserRole.TRAINER);
		return responseBuilder.success(HttpStatus.CREATED, "Trainer created successfully", response);
	}
	
	@Operation(description = "This end point is used to add the subjects after registration or update the trainer details to the database",responses = {
			@ApiResponse(responseCode = "200",description = "updated trainer successfully"),
			@ApiResponse(responseCode = "404",description = "failed to update the trainer",content = {@Content(schema = @Schema(anyOf = UserNotFoundByIdException.class))})})
	@PutMapping("/trainers/{userId}")
	public ResponseEntity<ResponseStructure<UserResponse>> updateTrainer(@RequestBody  TrainerRequest trainerRequest,@PathVariable String userId){
		UserResponse response=userService.updateTrainer(trainerRequest,userId);
		return responseBuilder.success(HttpStatus.OK, "Trainer updated", response);
	}
	
	@Operation(description = "This end point is used to save the student to the database",responses = {
			@ApiResponse(responseCode = "201",description = "student is created successfully"),
			@ApiResponse(responseCode = "500",description = "internal server error",content = {@Content(schema = @Schema(anyOf = RuntimeException.class))})})
	@PostMapping("/students/register")
	public ResponseEntity<ResponseStructure<UserResponse>> saveStudent(@RequestBody @Valid RegistrationRequest registrationRequest){
		UserResponse response=userService.registerUser(registrationRequest,UserRole.STUDENT);
		return responseBuilder.success(HttpStatus.CREATED, "Student created successfully", response);
	}
	
	@Operation(description = "This end point is used to add the additional details like yop, degree, stream and so on while registering and also can update the details"
			+ "of the student to the database",responses = {
			@ApiResponse(responseCode = "201",description = "student is updated successfully"),
			@ApiResponse(responseCode = "404",description = "student not found by the given id",content = {@Content(schema = @Schema(anyOf = UserNotFoundByIdException.class))})})
	@PutMapping("/students/{userId}")
	public ResponseEntity<ResponseStructure<StudentResponse>> updateStudent(@RequestBody @Valid StudentRequest studentRequest,@PathVariable String userId){
		StudentResponse studentResponse=userService.updateStudent(studentRequest,userId);
		return responseBuilder.success(HttpStatus.OK, "Student Updated", studentResponse);
	}
	
	@Operation(description = "This end point is used to add the stack to the student to the database",responses = {
			@ApiResponse(responseCode = "201",description = "added the stack successfully"),
			@ApiResponse(responseCode = "404",description = "student not found by the given id",content = {@Content(schema = @Schema(anyOf = UserNotFoundByIdException.class))})})
	@PatchMapping("/students/{userId}")
	public ResponseEntity<ResponseStructure<StudentResponse>> updateStudentStack(@RequestParam Stack stack,@PathVariable String userId){
		StudentResponse response=userService.updateStudentStack(stack, userId);
		return responseBuilder.success(HttpStatus.OK, "Student stack is Updated", response);
	}
	
	@Operation(description = "This end point is used view the rating of the students by their respective id's",responses = {
			@ApiResponse(responseCode = "302",description = "rating found successfully"),
			@ApiResponse(responseCode = "404",description = "student not found by the given id",content = {@Content(schema = @Schema(anyOf = UserNotFoundByIdException.class))})})
	@GetMapping("/students/{studentId}/ratings")
	public ResponseEntity<ResponseStructure<List<RatingResponse>>> viewRating(@PathVariable String studentId){
		List<RatingResponse> responses=userService.viewRating(studentId);
		return responseBuilder.success(HttpStatus.FOUND, "found the ratings of the student", responses);
	}

}

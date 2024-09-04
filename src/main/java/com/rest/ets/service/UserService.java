package com.rest.ets.service;

import java.util.List;


import org.springframework.stereotype.Service;

import com.rest.ets.entity.Admin;
import com.rest.ets.entity.HR;
import com.rest.ets.entity.Rating;
import com.rest.ets.entity.Student;
import com.rest.ets.entity.Trainer;
import com.rest.ets.entity.User;
import com.rest.ets.enums.Stack;
import com.rest.ets.enums.UserRole;
import com.rest.ets.exception.UserNotFoundByIdException;
import com.rest.ets.mapper.RatingMapper;
import com.rest.ets.mapper.UserMapper;
import com.rest.ets.repository.RatingRepository;
import com.rest.ets.repository.UserRepository;
import com.rest.ets.requestdto.StudentRequest;
import com.rest.ets.requestdto.TrainerRequest;
import com.rest.ets.responsedto.RatingResponse;
import com.rest.ets.responsedto.StudentResponse;
import com.rest.ets.responsedto.TrainerResponse;
import com.rest.ets.responsedto.UserResponse;
import com.rest.ets.security.RegistrationRequest;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {
	private UserRepository userRepository;
	private UserMapper mapper;
	private RatingRepository ratingRepository;
	private RatingMapper ratingMapper;

	public UserResponse saveUser(RegistrationRequest registrationRequest,UserRole role) {
		User user = null;
		switch (role) {
		case ADMIN -> user = new Admin();
		case HR -> user = new HR();
		case STUDENT -> user = new Student();
		case TRAINER -> user = new Trainer();
		default -> throw new IllegalArgumentException("Unexpected value: " + role);
		}

		if(user != null) {
			user = mapper.mapToUserEntity(registrationRequest, user);
			user.setRole(role);
			user = userRepository.save(user);
		}

		return mapper.mapToUserResponse(user);
	}

	public TrainerResponse updateTrainer(TrainerRequest trainerRequest,String userId) {		
		return userRepository.findById(userId).map((user)->{
			user=mapper.mapToTrainerEntity(trainerRequest,(Trainer) user);
			user=userRepository.save(user);
			return mapper.mapToTrainerResponse((Trainer)user);
		}).orElseThrow(()->new UserNotFoundByIdException("failed to update the trainer"));
	}

	public StudentResponse updateStudent(StudentRequest studentRequest, String userId) {
		return userRepository.findById(userId).map((user)->{
			user=mapper.mapToStudentEntity(studentRequest,(Student) user);
			user=userRepository.save(user);
			return mapper.mapToStudentResponse((Student)user);
		}).orElseThrow(()->new UserNotFoundByIdException("failed to update the student"));
	}

	public StudentResponse updateStudentStack(Stack stack, String userId) {
		return	userRepository.findById(userId).map(user->{
			Student student=(Student)user;
			stack.getSubjects().forEach(subject->{
				Rating rating = new Rating();
				rating.setSubject(subject);
				rating.setStudent(student);
				rating=ratingRepository.save(rating);
			});
			student.setStack(stack);
			user=userRepository.save(student);
			return mapper.mapToStudentResponse(student);
		}).orElseThrow(()->new UserNotFoundByIdException("faied to update stack to the student"));
	}

	public List<RatingResponse> viewRating(String userId) {
		return userRepository.findById(userId).map(user->{
			Student student=(Student)user;
			return student.getRatings()
					.stream()
					.map(rating->ratingMapper.mapToRatingResponseEntity(rating))
					.toList();
		}).orElseThrow(()->new UserNotFoundByIdException("student is not found by the given id"));


	}


}

package com.rest.ets.mapper;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.rest.ets.entity.Student;
import com.rest.ets.entity.Trainer;
import com.rest.ets.entity.User;
import com.rest.ets.requestdto.StudentRequest;
import com.rest.ets.requestdto.TrainerRequest;
import com.rest.ets.responsedto.StudentResponse;
import com.rest.ets.responsedto.TrainerResponse;
import com.rest.ets.responsedto.UserResponse;
import com.rest.ets.security.RegistrationRequest;

@Component
@AllArgsConstructor
public class UserMapper {
	private PasswordEncoder passwordEncoder;
public User mapToUserEntity(RegistrationRequest registrationRequest, User user) {
		
		user.setUsername(registrationRequest.getUsername());
		user.setEmail(registrationRequest.getEmail());
		user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
		return user;
	}
	
	public UserResponse mapToUserResponse(User user) {
		UserResponse userResponse=new UserResponse();
		userResponse.setUsername(user.getUsername());
		userResponse.setEmail(user.getEmail());
		userResponse.setRole(user.getRole());
		userResponse.setCreatedDate(user.getCreatedDate());
		userResponse.setModifiedDate(user.getModifiedDate());
		return userResponse;
	}
	public Trainer mapToTrainerEntity(TrainerRequest trainerRequest,Trainer trainer) {
		if(trainerRequest.getUsername()!=null)
		trainer.setUsername(trainerRequest.getUsername());
		if(trainerRequest.getEmail()!=null)
		trainer.setEmail(trainerRequest.getEmail());
		trainer.setSubjects(trainerRequest.getSubjects());
		return trainer;
	}
	public TrainerResponse mapToTrainerResponse(Trainer trainer) {
		TrainerResponse response=new TrainerResponse();
		response.setUsername(trainer.getUsername());
		response.setEmail(trainer.getEmail());
		response.setSubjects(trainer.getSubjects());
		response.setModifiedDate(trainer.getModifiedDate());
		return response;
	}
	public Student mapToStudentEntity(StudentRequest studentRequest, Student student) {
		if(studentRequest!=null)
		student.setUsername(studentRequest.getUsername());
		if(studentRequest!=null)
		student.setEmail(studentRequest.getEmail());
		student.setDegree(studentRequest.getDegree());
		student.setStream(studentRequest.getStream());
		student.setYearOfPassout(studentRequest.getYearOfPassout());
		student.setDegreePercentage(studentRequest.getDegreePercentage());
		student.setTwelthPercentage(studentRequest.getTwelthPercentage());
		student.setTenthPercentage(studentRequest.getTenthPercentage());
		return student;
	}
	public StudentResponse mapToStudentResponse(Student student) {
		StudentResponse studentResponse=new StudentResponse();
		studentResponse.setUsername(student.getUsername());
		studentResponse.setEmail(student.getEmail());
		studentResponse.setDegree(student.getDegree());
		studentResponse.setStream(student.getStream());
		studentResponse.setDegree(student.getDegree());
		studentResponse.setYearOfPassout(student.getYearOfPassout());
		studentResponse.setDegreePercentage(student.getDegreePercentage());
		studentResponse.setTwelvethPercentage(student.getTwelthPercentage());
		studentResponse.setTenthPercentage(student.getTenthPercentage());
		return studentResponse;
	}

}

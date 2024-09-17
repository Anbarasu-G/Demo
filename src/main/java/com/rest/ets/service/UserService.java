package com.rest.ets.service;

import java.util.Date;
import java.util.List;
import java.util.Random;


import com.rest.ets.exception.InvalidOtpException;
import com.rest.ets.exception.RegistrationSessionExpiredException;
import com.rest.ets.requestdto.LoginRequest;
import com.rest.ets.requestdto.OtpRequest;
import com.rest.ets.security.JWT_Service;
import com.rest.ets.util.CacheHelper;
import com.rest.ets.util.MailSenderService;
import com.rest.ets.util.MessageModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

@Slf4j
@Service
@AllArgsConstructor
public class UserService {
	private UserRepository userRepository;
	private UserMapper mapper;
	private RatingRepository ratingRepository;
	private RatingMapper ratingMapper;
	private MailSenderService mailSender;

	private Random random;
	private CacheHelper cacheHelper;
	private AuthenticationManager authenticationManager;
	private JWT_Service jwtService;

	public UserResponse registerUser(RegistrationRequest registrationRequest, UserRole role) {
		User user = null;
		switch (role) {
			case ADMIN -> user = new Admin();
			case HR -> user = new HR();
			case STUDENT -> user = new Student();
			case TRAINER -> user = new Trainer();
			default -> throw new IllegalArgumentException("Unexpected value: " + role);
		}
		int otp = 0 ;
		if(user != null) {
			user = mapper.mapToUserEntity(registrationRequest, user);
			user.setRole(role);
			otp = random.nextInt(100000, 999999);

			cacheHelper.userCache(user);
			cacheHelper.otpCache(otp, user.getEmail());
		}
		sendVerificationOtpToUsers(user.getEmail(), otp );
		return mapper.mapToUserResponse(user);
	}

	public UserResponse verifyOtp(OtpRequest otpRequest) {

		Integer otp = cacheHelper.getOtp(otpRequest.getEmail());

		if(!otp.equals(otpRequest.getOtp()))
			throw new InvalidOtpException("Incorrect OTP");

		User user = cacheHelper.getRegisteringEmail(otpRequest.getEmail());
		if(!user.getEmail().equals(otpRequest.getEmail()))
			throw new RegistrationSessionExpiredException(
					"Registration Session Expired, Please try again");

		userRepository.save(user);
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

	private  void sendVerificationOtpToUsers(String email, int otp)
	{
		String text ="<!DOCTYPE html>\n" +
				"<html lang=\"en\">\n" +
				"\n" +
				"<head>\n" +
				"    <meta charset=\"UTF-8\">\n" +
				"    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
				"    <title>Document</title>\n" +
				"</head>\n" +
				"<body>\n" +
				"    <p style=\"color: black;background-color: \n" +
				"    white; border: 1px solid outset;\">\n" +
				"    Hi, EDU Tracking System welcomes you \uD83D\uDE0A,\n" +
				"    Please Enter the OTP to verify your Email </p>\n" +
				"    <h4>"+ otp +"</h4>\n" +
				"\n" +
				"</body>\n" +
				"\n" +
				"</html>";
		MessageModel messageModel = new MessageModel();

		messageModel.setTo(email);
		messageModel.setSendDate(new Date());
		messageModel.setText(text);
		messageModel.setSubject("Email Verfication");
		mailSender.sendemail(messageModel);

	}
    public String userLogin(LoginRequest loginRequest){

		log.info(loginRequest.getEmail());
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken
				(loginRequest.getEmail(), loginRequest.getPassword());
		Authentication authentication = authenticationManager.authenticate(authenticationToken);

		if(authentication.isAuthenticated()){
			String token = userRepository.findByEmail(loginRequest.getEmail())
					.map(user -> {
						return jwtService.createJwt(user.getUserId(), user.getEmail(), user.getRole().name());
					}).orElseThrow(() -> new UsernameNotFoundException("UserName Not Found "));
			return  token;
		}
		return null;
	}

}

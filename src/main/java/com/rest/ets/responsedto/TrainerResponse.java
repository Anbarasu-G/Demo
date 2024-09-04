package com.rest.ets.responsedto;

import java.util.List;

import com.rest.ets.enums.Subject;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrainerResponse extends UserResponse{
	
	private List<Subject> subjects;

}

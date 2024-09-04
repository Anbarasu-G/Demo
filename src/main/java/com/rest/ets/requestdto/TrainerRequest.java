package com.rest.ets.requestdto;

import java.util.List;

import com.rest.ets.enums.Subject;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TrainerRequest extends UserRequest{
	@NotNull
	private List<Subject> subjects;

}

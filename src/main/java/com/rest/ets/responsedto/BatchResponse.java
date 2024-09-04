package com.rest.ets.responsedto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.rest.ets.enums.Subject;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BatchResponse {
	private String batchId;
	private String title;
	private List<Subject> subjects;
	private LocalDate startingDate;
	private LocalTime beginsAt;
	private LocalTime endsAt;
	

}

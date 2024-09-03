package com.rest.ets.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import com.rest.ets.enums.Subject;

@Entity
@Table(name = "trainers")
@Getter
@Setter
public class Trainer extends User{
     private List<Subject> subjects;
}

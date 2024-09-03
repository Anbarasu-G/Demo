package com.rest.ets.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "hrs")
@Getter
@Setter
public class HR extends User{

}

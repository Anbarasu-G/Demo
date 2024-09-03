package com.rest.ets.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "super_admins")
@Getter
@Setter
public class SuperAdmin extends User{

}

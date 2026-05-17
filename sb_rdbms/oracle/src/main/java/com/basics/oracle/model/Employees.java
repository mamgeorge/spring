package com.basics.oracle.model;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor// @EqualsAndHashCode
@Entity @Table( name = "EMPLOYEES", schema = "SYSTEM" )
public class Employees {

	@Id @GeneratedValue( strategy = GenerationType.SEQUENCE )
	@Column( name = "EMPLOYEE_ID" ) private Integer employeeId;

	@Column( name = "FIRST_NAME" ) private String firstName;
	@Column( name = "LAST_NAME" ) private String lastName;
	@Column( name = "EMAIL" ) private String email;
	@Column( name = "PHONE" ) private String phone;
	@Column( name = "HIRE_DATE" ) private Date hireDate;
	@Column( name = "MANAGER_ID" ) private Integer managerId;
	@Column( name = "JOB_TITLE" ) private String jobTitle;
}

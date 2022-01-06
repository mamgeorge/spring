package com.humanities.history.controller;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;

@Getter @Setter @EqualsAndHashCode @NoArgsConstructor
@Entity @Table(name = "history")
public class History {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long	id;
	private String	time;
	private String	name;
}

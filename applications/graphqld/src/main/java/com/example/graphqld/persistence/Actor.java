package com.example.graphqld.persistence;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Actor {

	// actor_id, first_name, last_name, last_update

	private Integer actor_id;
	private String first_name;
	private String last_name;
	private String last_update; // Timestamp
}

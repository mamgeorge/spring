package com.example.ntier.configs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class Message {

	private final String message;

	public Message(String message) { this.message=message; }
}
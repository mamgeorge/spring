package com.example.ntier.configs;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class Message {

	private final String messageVal;

	public Message(String messageVal) { this.messageVal= messageVal; }
}
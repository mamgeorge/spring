package com.example.ntier.configs;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
public class NtierController {

	@GetMapping( { "/" } ) String root( ) { return Instant.now().toString(); }

	@GetMapping( "/getMessage" ) Message getMessage( ) { return new Message(Instant.now().toString()); }
}

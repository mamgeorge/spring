package com.example.ntier.configs;

import com.example.ntier.persistence.User;
import com.example.ntier.persistence.UserService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController @RequestMapping( path = "/api")
public class NtierController {

	private UserService userService;

	@Autowired public NtierController(UserService userService) {
		this.userService=userService;
	}

	@GetMapping( { "/" } ) String root( ) { return Instant.now().toString(); }

	@GetMapping( "/getMessage" ) Message getMessage( ) { return new Message(Instant.now().toString()); }

	@GetMapping( "/getAllUsers" )
	public List<User> getAllUsers() { return userService.getAllUsers(); }

	@GetMapping( "/getUser/{userUid}" )
	public ResponseEntity<?> getUser(@PathVariable("userUid") UUID userUid) {

		/*
			Optional<User> optional = userService.getUser(userUid);
			if(optional.isPresent())
			{ return ResponseEntity.ok(optional.get()); } else
			{ return ResponseEntity.status(NOT_FOUND).body(new ErrorMessages("userUid: " + userUid + " not found!")); }
		*/
		return userService.getUser(userUid)
			.<ResponseEntity<?>>map(ResponseEntity::ok)
			.orElseGet(()-> ResponseEntity.status(NOT_FOUND)
				.body(new ErrorMessages("userUid: " + userUid + " not found!")));
	}

	@PostMapping( "/insertUser" )
	public ResponseEntity<Integer> insertUser(User user) {

		int intResult = userService.insertUser(user);
		if (intResult==1) { return ResponseEntity.ok().build(); }
		return ResponseEntity.badRequest().build();
	}

	// utility
	@Getter @Setter @AllArgsConstructor @ToString
	class ErrorMessages{ private String errorMessage; }
}

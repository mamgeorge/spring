package com.example.ntier.configs;

import com.example.ntier.persistence.User;
import com.example.ntier.persistence.UserService;
import jakarta.ws.rs.QueryParam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController @RequestMapping( path = "/api" )
public class NtierController { // UserResource

	private final UserService userService;

	@Autowired public NtierController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping( { "/" } ) String root( ) { return Instant.now().toString(); }

	@GetMapping( "/getMessage" ) Message getMessage( ) { return new Message(Instant.now().toString()); }

	@GetMapping( path = "/getAllUsers", produces = APPLICATION_JSON_VALUE )
	public List<User> getAllUsers(@QueryParam( "gender" ) String gender) {
		return userService.getAllUsers(Optional.ofNullable(gender));
	}

	@GetMapping( path = "/getUser/{userUid}", produces = APPLICATION_JSON_VALUE )
	public ResponseEntity<?> getUser(@PathVariable( "userUid" ) UUID userUid) {

		return userService.getUser(userUid)
			.<ResponseEntity<?>>map(ResponseEntity::ok)
			.orElseGet(( ) -> ResponseEntity.status(NOT_FOUND)
				.body(new ErrorMessages("userUid: " + userUid + " not found!")));
	}

	@PostMapping( path = "/insertUser", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE )
	public ResponseEntity<Integer> insertUser(@RequestBody User user) {

		ResponseEntity<Integer> response;
		int intResult = userService.insertUser(user);
		if ( intResult == 1 ) { response = ResponseEntity.ok().build(); }
		else { response = ResponseEntity.badRequest().build(); }
		return response;
	}

	@PutMapping( path = "/updateUser", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE )
	public ResponseEntity<Integer> updateUser(@RequestBody User user) {

		ResponseEntity<Integer> response;
		int intResult = userService.updateUser(user);
		if ( intResult == 1 ) { response = ResponseEntity.ok().build(); }
		else { response = ResponseEntity.badRequest().build(); }
		return response;
	}

	@DeleteMapping( path = "/deleteUser/{userUid}", produces = APPLICATION_JSON_VALUE )
	public ResponseEntity<Integer> deleteUser(@PathVariable( "userUid" ) UUID userUid) {

		ResponseEntity<Integer> response;
		int intResult = userService.removeUser(userUid);
		if ( intResult == 1 ) { response = ResponseEntity.ok().build(); }
		else { response = ResponseEntity.badRequest().build(); }
		return response;
	}

	// utility
	@Getter @Setter @AllArgsConstructor @ToString
	class ErrorMessages { private String errorMessage; }
}

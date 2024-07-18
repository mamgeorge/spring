package com.example.ntier.configs;

import com.example.ntier.persistence.User;
import com.example.ntier.persistence.UserService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController @RequestMapping( path = "/api" )
public class NtierController { // UserResource

	private final UserService userService;
	private final Random random = new Random();

	@Autowired public NtierController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping( { "/" } ) String root( ) { return Instant.now().toString(); }

	@GetMapping( path = "/getAllUsers", produces = APPLICATION_JSON_VALUE )
	public List<User> getAllUsers(@RequestParam( value = "gender", required=false ) String gender) {
		return userService.getAllUsers(Optional.ofNullable(gender));
	}

	@GetMapping( path = "/getUser/{userUid}", produces = APPLICATION_JSON_VALUE )
	public ResponseEntity<User> getUser(@PathVariable( value = "userUid", required=false ) Optional<String> optionalId) {

		User user = new User(UUID.randomUUID(),"","",null,0,"");
		ResponseEntity<User> response = new ResponseEntity<>(user, NOT_FOUND);
		if(optionalId.isPresent()) {
			Optional<UUID> optionalUuid;
			try { optionalUuid = Optional.of(UUID.fromString(optionalId.get()));}
			catch (IllegalArgumentException ex) {
				optionalUuid=Optional.of(UUID.randomUUID());
				System.out.println("ERROR: " + ex.getMessage());
				System.out.println("optionalUuid: " + optionalUuid);
			}
			if(optionalUuid.isPresent()){
			Optional<User> optionalUser = userService.getUser(optionalUuid.get());
			if ( optionalUser.isPresent() ) {
				user = optionalUser.get();
				response = new ResponseEntity<>(user, OK);
			}
			}
		}
		return response;
	}

	@GetMapping( path = "/getUserRnd", produces = APPLICATION_JSON_VALUE )
	public ResponseEntity<User> getUserRnd( ) {

		List<User> list = userService.getAllUsers(Optional.empty());
		User user = null;
		if ( list.isEmpty() ) { System.out.println("NO ITEMS!"); } else
		{ user = list.get(random.nextInt(list.size())); }
		return new ResponseEntity<>(user, OK);
	}

	@PostMapping( path = "/getUserPst", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE )
	public ResponseEntity<User> getUserPst(@RequestBody User userPst) {

		Optional<String> optional = Optional.ofNullable(userPst.getGender().toString());
		System.out.println("optional: " + optional + "\n" + userPst);
		ResponseEntity<User> response;

		List<User> list = userService.getAllUsers(optional);
		if ( list.isEmpty() ) { response = new ResponseEntity<>(null, NOT_FOUND); }
		else {
			User user = list.get(random.nextInt(list.size()));
			response = new ResponseEntity<>(user, OK);
		}
		return response;
	}

	@PostMapping( path = "/insertUser", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE )
	public ResponseEntity<Integer> insertUser(@RequestBody User user) {

		ResponseEntity<Integer> response;
		System.out.println("user insert: " + user);

		int intResult = userService.insertUser(user);
		if ( intResult == 1 ) { response = ResponseEntity.ok().build(); }
		else { response = ResponseEntity.badRequest().build(); }
		return response;
	}

	@PutMapping( path = "/updateUser", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE )
	public ResponseEntity<Integer> updateUser(@RequestBody User user) {

		HttpStatus httpStatus = BAD_REQUEST;
		int intResult = userService.updateUser(user);
		if ( intResult == 1 ) { httpStatus = OK; }
		return new ResponseEntity<>(intResult, httpStatus);
	}

	@DeleteMapping( path = "/deleteUser/{userUid}", produces = APPLICATION_JSON_VALUE )
	public ResponseEntity<Integer> deleteUser(@PathVariable( "userUid" ) UUID userUid) {

		ResponseEntity<Integer> response;
		System.out.println("userUid delete: " + userUid);

		int intResult = userService.removeUser(userUid);
		if ( intResult == 1 ) { response = ResponseEntity.ok().build(); }
		else { response = ResponseEntity.badRequest().build(); }
		return response;
	}

	// utility
	@Getter @Setter @AllArgsConstructor @ToString
	static class ErrorMessages { private String errorMessage; }
}

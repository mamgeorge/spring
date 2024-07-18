package com.example.ntier.persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.example.ntier.persistence.User.Gender.FEMALE;
import static com.example.ntier.persistence.User.Gender.MALE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith( { MockitoExtension.class } )
class UserServiceTest {

	@Mock private UserDaoData userDaoData;
	private UserService userService;

	@BeforeEach void setUp( ) { userService = new UserService(userDaoData); }

	@Test void getAllUsers( ) {

		User user = new User(UUID.randomUUID(), "Hal", "Jordan", MALE, 30, "Hal.Jordan@email.com");
		List<User> userMocks = new ArrayList<>();
		userMocks.add(user);
		given(userDaoData.selectAllUsers()).willReturn(userMocks);

		List<User> users = userService.getAllUsers(Optional.empty());
		assertThat(users).hasSizeGreaterThan(0);
	}

	@Test void getAllUsers_filtered( ) {

		User user1 = new User(UUID.randomUUID(), "Hal", "Jordan", MALE, 30, "Hal.Jordan@email.com");
		User user2 = new User(UUID.randomUUID(), "Halley", "Kim", FEMALE, 19, "Halley.Kim@email.com");
		List<User> users = new ArrayList<>();
		users.add(user1);
		users.add(user2);

		given(userDaoData.selectAllUsers()).willReturn(users);

		List<User> userLists = userService.getAllUsers(Optional.of("female"));
		System.out.println(userLists);
		assertThat(userLists).hasSizeGreaterThan(0);

		userLists = userService.getAllUsers(Optional.of("xxxxx"));
		System.out.println(userLists);
		assertThat(userLists).isEmpty();

		userLists = userService.getAllUsers(Optional.empty());
		System.out.println(userLists);
		assertThat(userLists).hasSizeGreaterThan(0);
	}

	@Test void getUser( ) {

		UUID usedUid = UUID.randomUUID();
		User user = new User(usedUid, "Hal", "Jordan", MALE, 30, "Hal.Jordan@email.com");
		given(userDaoData.selectUser(usedUid)).willReturn(Optional.of(user));

		Optional<User> optional = userService.getUser(usedUid);
		assertThat(optional).isPresent();
		assertThat(optional.get().getUserUid()).isNotNull();
		assertThat(optional.get().getUserUid()).isEqualTo(usedUid);
	}

	@Test void removeUser( ) {

		UUID usedUid = UUID.randomUUID();
		User user = new User(usedUid, "Hal", "Jordan", MALE, 30, "Hal.Jordan@email.com");

		given(userDaoData.selectUser(usedUid)).willReturn(Optional.of(user));
		given(userDaoData.deleteUser(usedUid)).willReturn(1);

		//	ArgumentCaptor<UUID> argumentCaptor = ArgumentCaptor.forClass(UUID.class);
		int intRsp = userService.removeUser(usedUid);

		verify(userDaoData).selectUser(usedUid); // verify(userDaoData, atLeast(1))
		verify(userDaoData).deleteUser(usedUid);
		//	verify(userDaoData).deleteUser(argumentCaptor.capture());

		//	UUID uuid = argumentCaptor.getValue();
		//	assertThat(uuid).isEqualTo(usedUid);
		assertThat(intRsp).isEqualTo(1);
	}

	@Test void updateUser( ) {

		UUID usedUid = UUID.randomUUID();
		User user = new User(usedUid, "Hal", "Jordan", MALE, 30, "Hal.Jordan@email.com");

		given(userDaoData.selectUser(usedUid)).willReturn(Optional.of(user));
		given(userDaoData.updateUser(user)).willReturn(1);

		ArgumentCaptor<User> argumentCaptor = ArgumentCaptor.forClass(User.class);
		int intRsp = userService.updateUser(user);

		verify(userDaoData).selectUser(usedUid); // verify(userDaoData, atLeast(1))
		verify(userDaoData).updateUser(argumentCaptor.capture());

		User used = argumentCaptor.getValue();
		assertThat(user).isEqualTo(used);
		assertThat(intRsp).isEqualTo(1);
	}

	@Test void insertUser( ) {

		User user = new User(null, "Hal", "Jordan", MALE, 30, "Hal.Jordan@email.com");

		int intResult = 1;
		//	given(userDaoData.insertUser(any(UUID.class), eq(user))).willReturn(intResult);
		given(userDaoData.insertUser(any(UUID.class), any(User.class))).willReturn(intResult);

		ArgumentCaptor<User> argumentCaptor = ArgumentCaptor.forClass(User.class);
		int intResponse = userService.insertUser(user);
		verify(userDaoData).insertUser(any(UUID.class), argumentCaptor.capture());

		User used = argumentCaptor.getValue();
		assertThat(used.getUserUid()).isNotNull();
		assertThat(intResult).isEqualTo(intResponse);
	}
}

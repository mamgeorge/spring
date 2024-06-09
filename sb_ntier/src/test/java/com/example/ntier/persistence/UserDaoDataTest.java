package com.example.ntier.persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.example.ntier.persistence.User.Gender.MALE;
import static org.assertj.core.api.Assertions.assertThat;

class UserDaoDataTest {

	private UserDaoData userDaoData;

	@BeforeEach void setUp( ) { userDaoData = new UserDaoData(); }

	@Test void selectAllUsers( ) {

		List<User> users = userDaoData.selectAllUsers();
		User user = users.get(0);

		users.forEach(System.out::println);
		users.forEach(usr -> { System.out.print( usr.getLastName() + " "); });

		assertThat(users).hasSizeGreaterThan(0);
		assertThat(user.getUserUid()).isNotNull();
	}

	@Test void selectUser( ) {

		UUID userUid = UUID.randomUUID();
		User user = new User(userUid, "Hal", "Jordan", MALE, 30, "Hal.Jordan@email.com");
		userDaoData.insertUser(userUid, user);
		Optional<User> optional = userDaoData.selectUser(userUid);
		Optional<User> optionalNot = userDaoData.selectUser(UUID.randomUUID());

		assertThat(optional.isPresent()).isTrue();
		assertThat(optionalNot.isPresent()).isFalse();
		assertThat(userDaoData.selectAllUsers()).hasSizeGreaterThan(1);
		assertThat(optional.get()).isEqualTo(user);
	}

	@Test void deleteUser( ) {

		List<User> users = userDaoData.selectAllUsers();
		User userZed = users.get(0);
		int usersInt = users.size();

		userDaoData.deleteUser(userZed.getUserUid());
		int usersNew = userDaoData.selectAllUsers().size();

		assertThat(usersInt).isGreaterThan(usersNew);
	}

	@Test void updateUser( ) {

		List<User> usersOrg = userDaoData.selectAllUsers();
		User userZed = usersOrg.get(0);
		UUID userUid = userZed.getUserUid();
		User userNew = new User(userUid, "Hal", "Jordan", MALE, 30, "Hal.Jordan@email.com");

		userDaoData.updateUser(userNew);
		List<User> usersAlt = userDaoData.selectAllUsers();
		Optional<User> optional = userDaoData.selectUser(userUid);

		usersOrg.forEach(usr -> { System.out.print(usr.getLastName() + " "); });
		usersAlt.forEach(usr -> { System.out.print(usr.getLastName() + " "); });

		assertThat(optional.isPresent()).isTrue();
		assertThat(userDaoData.selectAllUsers()).hasSizeGreaterThan(0);
		assertThat(optional.get()).isEqualTo(userNew);
	}

	@Test void insertUser( ) {

		int usersInt = userDaoData.selectAllUsers().size();
		UUID userUid = UUID.randomUUID();
		User user = new User(userUid, "Hal", "Jordan", MALE, 30, "Hal.Jordan@email.com");
		userDaoData.insertUser(userUid, user);
		int usersNew = userDaoData.selectAllUsers().size();

		assertThat(usersInt).isLessThan(usersNew);
	}
}
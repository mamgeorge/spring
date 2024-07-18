package com.example.ntier.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

// Business Layer
@Service
public class UserService {

	private final UserDao userDao;

	@Autowired public UserService(UserDao userDao) { this.userDao = userDao; }

	public List<User> getAllUsers(Optional<String> gender) {

		List<User> users = userDao.selectAllUsers();

		if ( gender.isPresent() ) {

			String genderUpper = gender.get().toUpperCase();
			List<User> usersNew = new ArrayList<>();
			users.forEach(user -> {

				String genderTemp = user.getGender().toString();
				if ( genderTemp.equals(genderUpper) ) { usersNew.add(user); }
			});

			users = usersNew;
		}
		else { System.out.print("No filter requested :) "); }
		System.out.println("Returning users: " + users.size());

		return users;
	}

	public Optional<User> getUser(UUID userUid) { return userDao.selectUser(userUid); }

	public int removeUser(UUID userUid) {
		int intRes = -1;
		Optional<User> userOptional = getUser(userUid);
		if ( userOptional.isPresent() ) { intRes = userDao.deleteUser(( userUid )); }
		return intRes;
	}

	public int updateUser(User user) {
		int intRes = -1;
		Optional<User> userOptional = getUser(user.getUserUid());
		if ( userOptional.isPresent() ) { intRes = userDao.updateUser(user); }
		return intRes;
	}

	public int insertUser(User user) {

		UUID userUid = UUID.randomUUID();
	//	validateUserInsert(user);
		User userNew = User.newUser(userUid, user);
		return userDao.insertUser(userUid, userNew);
	}

	private static void validateUserInsert(User user) {

		requireNonNull(user.getFirstName(), "insertUser getFirstName required");
		requireNonNull(user.getLastName(), "insertUser getLastName required");
		requireNonNull(user.getAge(), "insertUser getAge required");
		requireNonNull(user.getGender(), "insertUser getGender required");
		requireNonNull(user.getEmail(), "insertUser getEmail required");
	}
}

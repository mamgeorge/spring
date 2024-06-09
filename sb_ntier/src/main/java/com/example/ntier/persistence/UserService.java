package com.example.ntier.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

// Business Layer
@Service
public class UserService {

	private UserDao userDao;

	@Autowired public UserService(UserDao userDao) { this.userDao = userDao; }

	public List<User> getAllUsers( ) { return userDao.selectAllUsers(); }

	public Optional<User> getUser(UUID userUid) { return userDao.selectUser(userUid); }

	public int removeUser(UUID userUid) {
		int intRes = -1;
		Optional<User> userOptional = getUser(userUid);
		if(userOptional.isPresent()) { intRes = userDao.deleteUser((userUid)); }
		return intRes;
	}

	public int updateUser(User user) {
		int intRes = -1;
		Optional<User> userOptional = getUser(user.getUserUid());
		if(userOptional.isPresent()) { intRes = userDao.updateUser(user); }
		return intRes;
	}

	public int insertUser(User user) {

		UUID uuid = UUID.randomUUID();
		user.setUserUid(uuid);
		return userDao.insertUser(uuid, user);
	}
}

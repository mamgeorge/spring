package com.example.demo.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserDao {

	List<User> selectAllUsers( );
	Optional<User> selectUser(UUID userUid);
	int deleteUser(UUID userUid);
	int updateUser(User user);
	int insertUser(UUID userUid, User user);
}

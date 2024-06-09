package com.example.demo.persistence;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static com.example.demo.persistence.User.Gender.FEMALE;
import static com.example.demo.persistence.User.Gender.MALE;

@Repository
public class UserDaoData implements UserDao {

	private final Map<UUID, User> database;

	public UserDaoData() {

		database = new HashMap();
		UUID userUid;
		userUid = UUID.randomUUID();
		database.put(userUid, new User(userUid, "John", "Robinson", MALE, 40, "John.Robinson@email.com"));
		userUid = UUID.randomUUID();
		database.put(userUid, new User(userUid, "Bruce", "Wayne", MALE, 20, "Bruce.Wayne@email.com"));
		userUid = UUID.randomUUID();
		database.put(userUid, new User(userUid, "James", "Kirk", MALE, 30, "James.Kirk@email.com"));
		userUid = UUID.randomUUID();
		database.put(userUid, new User(userUid, "Diana", "Prince", FEMALE, 50, "Diana.Prince@email.com"));
	}

	@Override public List<User> selectAllUsers( ) { return new ArrayList<>(database.values()); }
	@Override public Optional<User> selectUser(UUID userUid) { return Optional.ofNullable(database.get(userUid)); }
	@Override public int deleteUser(UUID userUid) { database.remove(userUid); return 1; }
	@Override public int updateUser(User user) { database.put(user.getUserUid(), user); return 1; }
	@Override public int insertUser(UUID userUid, User user) { database.put(userUid, user); return 1; }
}

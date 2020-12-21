package JavaCA.service;

import java.util.ArrayList;
import java.util.List;

import JavaCA.model.User;

public interface UserInterface {
	
	void createUser(User user);
	void updateUser(User user);
	List<User> listAllUser();
	void deleteUser(User user);
	boolean authenticate(User user);
	User findByName(String name);
	User findById(long id);
	User findByUsername(String username);
	ArrayList<String> getRoleTypes();
}

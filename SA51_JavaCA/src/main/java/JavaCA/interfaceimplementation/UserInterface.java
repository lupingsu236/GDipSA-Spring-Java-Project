package JavaCA.interfaceimplementation;

import java.util.ArrayList;
import java.util.List;

import JavaCA.model.User;

public interface UserInterface {
	
	public void createUser(User user);
	public void updateUser(User user);
	public List<User> listAllUser();
	public void deleteUser(User user);
	public boolean authenticate(User user);
	public User findByUsername(String name);
	public ArrayList<User> findAllUsers();
}

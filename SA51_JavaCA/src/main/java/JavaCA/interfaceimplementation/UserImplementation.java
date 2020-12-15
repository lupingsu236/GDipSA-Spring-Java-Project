package JavaCA.interfaceimplementation;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import JavaCA.model.User;
import JavaCA.repo.UserRepository;

@Service
@Transactional
public class UserImplementation implements UserInterface {

	@Autowired
	UserRepository urepo;
	
	@Override
	public void createUser(User user) {
		// TODO Auto-generated method stub
		urepo.save(user);
	}

	@Override
	public void updateUser(User user) {
		// TODO Auto-generated method stub
		urepo.save(user);
	}

	@Override
	public List<User> listAllUser() {
		// TODO Auto-generated method stub
		return urepo.findAll();
	}

	@Override
	public void deleteUser(User user) {
		// TODO Auto-generated method stub
		urepo.delete(user);
	}

	@Override
	public boolean authenticate(User user) {
		// TODO Auto-generated method stub
		User dbuser = urepo.findUserByUsername(user.getUsername());
		if (dbuser == null) return false;
		if (dbuser.getUsername().equals(user.getUsername()) && dbuser.getPassword().equals(user.getPassword()))
			return true;
		else
			return false;
	}

	@Override
	public User findByName(String name) {
		// TODO Auto-generated method stub
		return urepo.findUserByUsername(name);
	}
	
}

package JavaCA.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import JavaCA.model.RoleType;
import JavaCA.model.User;
import JavaCA.repo.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository urepo;
	
	@Override
	public void createUser(User user) {
		urepo.save(user);
	}

	@Override
	public void updateUser(User user) {
		urepo.save(user);
	}

	@Override
	public List<User> listAllUser() {
		return urepo.findAll();
	}

	@Override
	public void deleteUser(User user) {
		urepo.delete(user);
	}

	@Override
	public boolean authenticate(User user) {
		User dbuser = urepo.findUserByUsername(user.getUsername());
		if (dbuser == null) return false;
		if (dbuser.getUsername().equals(user.getUsername()) && dbuser.getPassword().equals(user.getPassword()))
			return true;
		else
			return false;
	}

	@Override
	public User findByName(String name) {
		return urepo.findUserByUsername(name);
	}

	@Override
	public User findById(long id) {
		return urepo.findById(id).get();
	}

	@Override
	public User findByUsername(String username) {
		return urepo.findUserByUsername(username);
	}
	
	@Override
	public User findByEmail(String email) 
	{
		return urepo.findUserByEmail(email);
	}

	@Override
	public ArrayList<String> getRoleTypes() {
		ArrayList<String> roleTypes = new ArrayList<>();
		roleTypes.add(RoleType.ADMIN.toString());
		roleTypes.add(RoleType.MECHANIC.toString());
		return roleTypes;
	}
	
	@Override
	public boolean verifyAdmin(HttpSession session)
	{
		User u = (User) session.getAttribute("usession");
		if (u == null) {
			return false;
		}
		else if (u.getRole() == RoleType.ADMIN) {
			return true;
		}
		return false;
	}

	@Override
	public boolean verifyLogin(HttpSession session) {
		if (session.getAttribute("usession")!=null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isUsernameUsed(String username) {
		User u = findByName(username);
		if (u!=null)
		{
			return true;
		}
		return false;
	}	
	
}

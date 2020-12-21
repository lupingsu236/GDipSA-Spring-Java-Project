package JavaCA.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class User 
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@NotBlank
	private String fullName;
	@NotBlank
	private String username;
	
	private String password;

	@NotBlank
	private String roletype;
	@NotBlank
	@Email
	private String email;
	private RoleType role;
	
	private String active;
	private ActiveType activetype;
	@OneToMany(mappedBy = "user")
	private List<Transaction> transactions;
	
	public User(){}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public User(@NotBlank String fullName, @NotBlank String username, String password, @NotBlank @Email String email,
			RoleType role, ActiveType activetype) {
		super();
		this.fullName = fullName;
		this.username = username;
		this.password = password;
		this.email = email;
		this.role = role;
		this.activetype=activetype;
	}

	public ActiveType getActivetype() {
		return activetype;
	}

	public void setActivetype(ActiveType activetype) {
		this.activetype = activetype;
	}

	public String getEmail() {
		return email;
	}


	public String getRoletype() {
		return roletype;
	}

	public void setRoletype(String roletype) {
		this.roletype = roletype;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}


	public RoleType getRole() {
		return role;
	}

	public void setRole(RoleType role) {
		this.role = role;
	}
	
	public String getRoleName() {
		return this.getRole().toString();
	}
}

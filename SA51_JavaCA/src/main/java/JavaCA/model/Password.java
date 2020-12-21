package JavaCA.model;

public class Password {
	private String oldpassword;
	private String newpassword;
	private String conpassword;
	
	public Password() {}
	
	public Password(String oldpassword, String newpassword, String conpassword) {
		super();
		this.oldpassword = oldpassword;
		this.newpassword = newpassword;
		this.conpassword = conpassword;
	}

	public String getConpassword() {
		return conpassword;
	}

	public void setConpassword(String conpassword) {
		this.conpassword = conpassword;
	}

	public String getOldpassword() {
		return oldpassword;
	}
	public void setOldpassword(String oldpassword) {
		this.oldpassword = oldpassword;
	}
	public String getNewpassword() {
		return newpassword;
	}
	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}	
}

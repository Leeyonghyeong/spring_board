package org.spring.vo;

import java.util.Date;

public class UserVO {
	private String userid; 
	private String password; 
	private String username; 
	private String email; 
	private Date regdate;
	private Date updatedate;
	
	public UserVO() {
		// TODO Auto-generated constructor stub
	}

	public UserVO(String userid, String password, String username, String email, Date regdate, Date updatedate) {
		this.userid = userid;
		this.password = password;
		this.username = username;
		this.email = email;
		this.regdate = regdate;
		this.updatedate = updatedate;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getRegdate() {
		return regdate;
	}

	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}

	public Date getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}

	@Override
	public String toString() {
		return "UserVO [userid=" + userid + ", password=" + password + ", username=" + username + ", email=" + email
				+ ", regdate=" + regdate + ", updatedate=" + updatedate + "]";
	}
	
	
}

package org.ecommerce.dto;

import java.io.Serializable;

public class UserBankLoginRequest implements Serializable{
	
	private static final long serialVersionUID = -3464690828186771070L;
	private String username;
	private String password;

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

}
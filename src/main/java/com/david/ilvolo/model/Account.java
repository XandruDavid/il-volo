package com.david.ilvolo.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Account {

	private String username;
	private String password;
	private String email;

	@Column
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}

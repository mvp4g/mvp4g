package com.mvp4g.example.client.bean;

import com.google.gwt.user.client.rpc.IsSerializable;

public class UserBean implements IsSerializable{

	private Integer id = null;
	private String firstName = null;
	private String lastName = null;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}

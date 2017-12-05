package com.bigerdranch.android.test.dao;

import android.database.sqlite.SQLiteDatabase;

public class PwdData {
	private String id;
	private String name;
	private String username;
	private String password;

	public PwdData() {
	}

	public PwdData(JsonTodata jsonTodata) {
		this.name = jsonTodata.getName();
		this.username = jsonTodata.getUsername();
		this.password = jsonTodata.getPassword();
	}

	public PwdData(String id, String name, String username, String password) {
		this.id = id;
		this.name = name;
		this.username = username;
		this.password = password;
	}

	public PwdData(String name, String username, String password) {
		this.name = name;
		this.username = username;
		this.password = password;
	}

	@Override
	public String toString() {
		return "{"+"\"http\":\"" + name +"\",\"url\":\"" + username  +"\",\"all\":\"" + password + "\"}";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
}

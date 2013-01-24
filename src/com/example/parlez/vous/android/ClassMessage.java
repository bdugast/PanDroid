package com.example.parlez.vous.android;

public class ClassMessage {

	private String user;
	private String message;
	
	public ClassMessage(String u,String m)
	{
		this.user = u;
		this.message = m;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}

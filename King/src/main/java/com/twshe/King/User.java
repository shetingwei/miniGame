package com.twshe.King;

import java.util.UUID;

public class User {
	
	private String name;
	private String sessionKey;
	
	public User(String name) {
		this.name = name;
		this.sessionKey = UUID.randomUUID().toString();
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getSessionKey() {
		return this.sessionKey;
	}
	
	public String newSessionKey() {
		this.sessionKey = UUID.randomUUID().toString();
		return this.sessionKey;
	}
	
	@Override
	public int hashCode() {
		return this.name.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(! (obj instanceof User)) {
			return false;
		}else {
			User user = (User) obj;
			if(this.name.equals(user.getName())) {
				return true;
			}else {
				return false;
			}
		}
	}
	
}

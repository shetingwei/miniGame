package com.twshe.King;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class User {
	
	private String name;
	private Map<Level, Integer> records;
	private String sessionKey;
	
	public User(String name) {
		this.name = name;
		this.records = new HashMap<Level, Integer>();
		this.sessionKey = UUID.randomUUID().toString();
	}
	
	public String getName() {
		return this.name;
	}
	
	public Map<Level, Integer> getRecords() {
		return this.records;
	}
	
	public Integer getRecord(Level level) {
		return this.records.get(level);
	}
	
	public String getSessionKey() {
		return this.sessionKey;
	}
	
	public String newSessionKey() {
		this.sessionKey = UUID.randomUUID().toString();
		return this.sessionKey;
	}
	
	public void updateRecord(Level level, Integer score) {
		Integer record =  this.records.get(level);
		if(record != null) {
			this.records.put(level, Math.max(score, record));
		}else {
			this.records.put(level, score);
		}
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

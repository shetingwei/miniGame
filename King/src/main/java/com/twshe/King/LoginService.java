package com.twshe.King;

public interface LoginService {
	public String login(String name) throws IllegalStateException;
	public boolean checkTimeOut(String name);
}

package com.twshe.King;

import java.time.Clock;

public interface LoginService {
	public String login(String name, Clock clock) throws IllegalStateException;
	public String logout(String name);
	public boolean checkTimeOut(String name);
	public void display();
}

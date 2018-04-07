package com.twshe.King;

import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LoginServiceImpl implements LoginService {
	
	private List<User> users = new ArrayList<User>();
	private Map<String, Instant> sessions = new ConcurrentHashMap<String, Instant>();
	private final int TIME_LIMIT = 60;

	private static LoginServiceImpl loginServiceImpl = new LoginServiceImpl();

	private LoginServiceImpl() {

	}

	public static LoginServiceImpl getInstance() {
		return loginServiceImpl;
	}

	public String login(String name, Clock clock) throws IllegalStateException {
		synchronized (users) {
			User user = new User(name);
			int index = users.indexOf(user);

			if (index >= 0) {
				throw new IllegalStateException("You have already login from the other device.");
			} else {
				users.add(user);
				sessions.put(user.getSessionKey(), clock.instant());
				return user.getSessionKey();
			}
		}
	}

	public String logout(String name) {
		synchronized (users) {
			User user = new User(name);
			int index = users.indexOf(user);
			if (index >= 0) {
				String oldSessionKey = users.get(index).getSessionKey();
				sessions.remove(oldSessionKey);
				users.remove(index);
				return oldSessionKey;
			} else {
				return null;
			}
		}
	}

	public boolean checkTimeOut(String name) {
		User user = new User(name);
		int index = users.indexOf(user);
		if (index >= 0) {
			String sessionKey = users.get(index).getSessionKey();
			Instant instant = Instant.now().minus(TIME_LIMIT, ChronoUnit.MINUTES);
			if (sessions.get(sessionKey).isBefore(instant)) {
				return true;
			} else {
				return false;
			}
		} else {
			return true;
		}
	}

	public void display() {
		System.out.println("----------All----------");
		for (User user : users) {
			System.out.println(user.getName());
		}
		System.out.println("-----------------------");
	}
}

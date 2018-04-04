package com.twshe.King;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

public class Game {

	private static List<User> users = new ArrayList<User>();
	private static Map<String, Timestamp> sessions = new HashMap<String, Timestamp>();

	public String login(String name) {
	
		synchronized (users) {
			User user = new User(name);
			int index = users.indexOf(user);

			if (index >= 0) {
				if (sessions.containsKey(users.get(index).getSessionKey())) {
					System.out.println("You have already login from the other device.");
				} else {
					sessions.put(users.get(index).getSessionKey(), new Timestamp(System.currentTimeMillis()));
				}
				return users.get(index).getSessionKey();
			} else {// New user
				users.add(user);
				sessions.put(user.getSessionKey(), new Timestamp(System.currentTimeMillis()));
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
				users.get(index).newSessionKey();
				return oldSessionKey;
			} else {
				return null;
			}
		}
	}

	private boolean checkTimeOut(String sessionKey) {
		long diff = (new Timestamp(System.currentTimeMillis())).getTime() - sessions.get(sessionKey).getTime();
		if (diff >= 10 * 60 * 1000) {
			// if(diff >= 500) {
			return true;
		} else {
			return false;
		}
	}

	public void postUserScoreToLevel(String name, Level level, int score) throws TimeoutException {
		User user = new User(name);
		int index = users.indexOf(user);
		String sessionKey = users.get(index).getSessionKey();
		if (!checkTimeOut(sessionKey)) {
			users.get(index).updateRecord(level, score);
		} else {
			throw new TimeoutException("Please login again.");
		}
	}

	public String getHighScoreList(Level level) {

		String result = "";

		List<User> list = users.stream().filter(u -> u.getRecord(level) != null).limit(15).collect(Collectors.toList());

		Comparator<User> comparator = new Comparator<User>() {
			public int compare(User user1, User user2) {
				return user2.getRecord(level) - user1.getRecord(level);
			}
		};

		Collections.sort(list, comparator);

		for (int i = 0; i < list.size(); i++) {
			User user = list.get(i);
			if (i != list.size() - 1) {
				result += user.getName() + " " + user.getRecord(level) + ",";
			} else {
				result += user.getName() + " " + user.getRecord(level);
			}
		}

		return result;
	}

	public void display() {

		synchronized (users) {
			System.out.println("----------All----------");
			for (User user : users) {
				System.out.println(user.getName() + ", " + user.getRecords());
			}
			System.out.println("-----------------------");
		}
	}

}

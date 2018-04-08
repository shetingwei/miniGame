package com.twshe.King;

import java.util.concurrent.TimeoutException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Clock;

public class Game {

	private LoginService loginService = LoginServiceImpl.getInstance();
	private PostScoreService postScoreService = PostScoreServiceImpl.getInstance();

	public String login(String name) throws IllegalStateException {
		try {
			return loginService.login(name);
		} catch (IllegalStateException ex) {
			throw ex;
		}
	}

	private String login(String name, Clock clock) throws IllegalStateException, NoSuchMethodException,
			IllegalArgumentException, InvocationTargetException, IllegalAccessException {

		Method method = loginService.getClass().getDeclaredMethod("login", new Class[] { String.class, Clock.class });
		method.setAccessible(true);
		return (String) method.invoke(loginService, name, clock);

	}

	private boolean checkTimeOut(String name) {
		return loginService.checkTimeOut(name);
	}

	public void postUserScoreToLevel(String name, Level level, int score) throws TimeoutException {
		if (!checkTimeOut(name)) {
			postScoreService.postUserScoreToLevel(name, level, score);
		} else {
			throw new TimeoutException("Please login again.");
		}
	}

	public String getHighScoreList(Level level) {
		return postScoreService.getHighScoreList(level);
	};

}

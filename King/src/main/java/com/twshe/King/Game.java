package com.twshe.King;

import java.util.concurrent.TimeoutException;
import java.time.Clock;

public class Game {
	
	private LoginService loginService = LoginServiceImpl.getInstance();
	private PostScoreService postScoreService = PostScoreServiceImpl.getInstance();

	public String login(String name, Clock clock) throws IllegalStateException {
		try {
			return loginService.login(name,clock);
		}catch(IllegalStateException ex) {
			throw ex;
		}
	}

	public String logout(String name) {
		return loginService.logout(name);
	}

	private boolean checkTimeOut(String name) {
		return loginService.checkTimeOut(name);
	}
	
	/*public void display() {
		loginService.display();
	}*/

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

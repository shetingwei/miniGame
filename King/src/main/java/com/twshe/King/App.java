package com.twshe.King;

import java.time.Clock;
import java.util.concurrent.TimeoutException;

public class App {
	public static void main(String[] args) {
		Game game = new Game();
		game.login("Ting", Clock.systemUTC());
		game.login("Thomas", Clock.systemUTC());
		game.display();
		try {
			game.postUserScoreToLevel("Ting", Level.EASY, 1000);
			game.postUserScoreToLevel("Thomas", Level.EASY, 5000);
			game.postUserScoreToLevel("Ting", Level.EASY, 2000);
			System.out.println(game.getHighScoreList(Level.EASY));
		}catch(TimeoutException ex) {
			System.out.println(ex.getMessage());
		}
	}
}

package com.twshe.King;

import java.time.Clock;
import java.util.concurrent.TimeoutException;

public class App {
	public static void main(String[] args) {
		Game game1 = new Game();
		Game game2 = new Game();
		game1.login("Ting", Clock.systemUTC());
		game2.login("Thomas", Clock.systemUTC());
		try {
			Thread thread1 = new Thread() {
				public void run() {
					try {
						game1.postUserScoreToLevel("Ting", Level.EASY, 10000);
					} catch (TimeoutException ex) {
						System.out.println(ex.getMessage());
					}
				}
			};

			Thread thread2 = new Thread() {
				public void run() {
					try {
							game2.postUserScoreToLevel("Thomas", Level.EASY, 20000);
					} catch (TimeoutException ex) {
						System.out.println(ex.getMessage());
					}
				}
			};
			thread1.start();
			thread2.start();
			thread1.join();
			thread2.join();
			
			System.out.println(game1.getHighScoreList(Level.EASY));
		}catch (InterruptedException ex) {
			System.out.println(ex.getMessage());
		}
	}
}

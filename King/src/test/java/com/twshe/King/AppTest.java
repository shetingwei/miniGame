package com.twshe.King;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeoutException;

import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AppTest {

	Game game1 = new Game();
	Game game2 = new Game();

	/*
	 * @Before public void setUp() { //game1.start(); //game2.start(); }
	 */

	@Test
	public void test1Login() {

		Set<String> sessionKeys = new HashSet<String>();

		Thread thread1 = new Thread() {
			public void run() {
				for (int i = 1; i <= 10; i++) {
					sessionKeys.add(game1.login("Ting" + i));
				}
			}
		};

		Thread thread2 = new Thread() {
			public void run() {
				for (int i = 1; i <= 10; i++) {
					sessionKeys.add(game2.login("Thomas" + i));
				}
			}
		};

		thread1.start();
		thread2.start();
		
		try {
			thread1.join();
			thread2.join();
			assertTrue(sessionKeys.size() == 20);
		} catch (InterruptedException ex) {
			System.out.println(ex.getMessage());
		}
	}

	@Test
	public void test2PostUserScoreToLevel() {
		try {
			for (int i = 1; i <= 10; i++) {
				game1.postUserScoreToLevel("Ting1", Level.EASY, 1000 + i * 10);
			}
			assertTrue(true);
		} catch (TimeoutException ex) {
			assertThat(ex.getMessage(), is("Please login again."));
		}
	}

	@Test
	public void test3GetHighScoreList() {

		Thread thread1 = new Thread() {
			public void run() {
				try {
					game1.postUserScoreToLevel("Ting1", Level.EASY, 3000);
				} catch (TimeoutException ex) {
					System.out.println(ex.getMessage());
				}
			}
		};

		Thread thread2 = new Thread() {
			public void run() {
				try {
					game2.postUserScoreToLevel("Thomas1", Level.EASY, 5000);
				} catch (TimeoutException ex) {
					System.out.println(ex.getMessage());
				}
			}
		};

		thread1.start();
		thread2.start();

		try {
			thread1.join();
			thread2.join();
			assertEquals("Thomas1 5000,Ting1 3000", game1.getHighScoreList(Level.EASY));
		} catch (InterruptedException ex) {
			System.out.println(ex.getMessage());
		}
	}

}

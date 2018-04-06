package com.twshe.King;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeoutException;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AppTest {

	private Game game1;
	private Game game2;
	private static Set<String> sessionKeys = new HashSet<String>();
	
	@Before
	public void setUp() {
		game1 = new Game();
		game2 = new Game();
	}
	
	@Test
	public void test1Login() {
		
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

	@Test(expected = IllegalStateException.class)
	public void test2DuplicatLogin() {
		sessionKeys.add(game1.login("John"));
		sessionKeys.add(game2.login("John"));
	}
	
	@Test
	public void test3PostUserScoreToLevel() {
		try {
			for (int i = 1; i <= 10; i++) {
				game1.postUserScoreToLevel("Ting1", Level.EASY, 1000 + i * 10);
			}
			assertTrue("Be able to post many times.", true);
		} catch (TimeoutException ex) {
			System.out.println(ex.getMessage());
			assertTrue(ex.toString(), false);
		}
	}

	@Test
	public void test4GetHighScoreList() {

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
			assertTrue(ex.toString(), false);
		}
	}
	
	@Test(expected = TimeoutException.class)
	public void test5TimeoutCheck() throws TimeoutException {
		try {
			//Thread.sleep(10 * 60 * 1000);
			Thread.sleep(5000);
			game1.postUserScoreToLevel("Ting1", Level.EASY, 5000);
		}catch(InterruptedException ex) {
			System.out.println(ex.getMessage());
		}catch(TimeoutException ex) {
			throw ex;
		}
	}
	
	@Test
	public void test6Logout() {
		String session = game1.logout("Ting1");
		if(sessionKeys.contains(session)) {
			sessionKeys.remove(session);
			sessionKeys.add(game2.login("Ting1"));
			assertTrue("Be able to do logout and login again.", true);
		}else {
			assertTrue("delete wrong session.", false);
		}
	}
	
	@Test
	public void test7UpdateSession() {
		try {
			game1.postUserScoreToLevel("Ting1", Level.EASY, 10000);
			assertTrue("Be able to post score again after login.", true);
		} catch (TimeoutException ex) {
			assertTrue("Not able to post score again after login.", false);
		}
	}

}

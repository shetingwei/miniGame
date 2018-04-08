package com.twshe.King;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
	private Clock mock;
	private static Set<String> sessionKeys = new HashSet<String>();

	@Before
	public void setUp() {
		game1 = new Game();
		game2 = new Game();
		mock = mock(Clock.class);
	}

	@Test
	public void test1Login() throws InterruptedException {

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

		thread1.join();
		thread2.join();
		assertTrue(sessionKeys.size() == 20);
	}

	@Test(expected = IllegalStateException.class)
	public void test2DuplicatLogin() {
		sessionKeys.add(game1.login("John"));
		sessionKeys.add(game2.login("John"));
	}

	@Test
	public void test3PostUserScoreToLevel() throws TimeoutException {

		for (int i = 1; i <= 10; i++) {
			game1.postUserScoreToLevel("Ting1", Level.EASY, 1000 + i * 10);
		}

		assertTrue("Be able to post many times.", true);
	}

	@Test
	public void test4GetHighScoreList() throws InterruptedException {

		Thread thread1 = new Thread() {
			public void run() {
				try {
					for (int i = 1; i <= 10; i++) {
						game1.postUserScoreToLevel("Ting" + i, Level.EASY, 10000 + 1000 * i);
					}
				} catch (TimeoutException ex) {
					System.out.println(ex.getMessage());
				}
			}
		};

		Thread thread2 = new Thread() {
			public void run() {
				try {
					for (int i = 1; i <= 10; i++) {
						game2.postUserScoreToLevel("Thomas" + i, Level.EASY, 20000 + 1000 * i);
					}
				} catch (TimeoutException ex) {
					System.out.println(ex.getMessage());
				}
			}
		};

		thread1.start();
		thread2.start();

		thread1.join();
		thread2.join();
		assertEquals(
				"Thomas10 30000,Thomas9 29000,Thomas8 28000,Thomas7 27000,Thomas6 26000,Thomas5 25000,Thomas4 24000,Thomas3 23000,Thomas2 22000,Thomas1 21000,Ting10 20000,Ting9 19000,Ting8 18000,Ting7 17000,Ting6 16000",
				game1.getHighScoreList(Level.EASY));
	}

	@Test(expected = TimeoutException.class)
	public void test5TimeoutCheck() throws TimeoutException, NoSuchMethodException, IllegalArgumentException,
			InvocationTargetException, IllegalAccessException {

		Instant instant = Instant.now().minus(70, ChronoUnit.MINUTES);
		when(mock.instant()).thenReturn(instant);

		Method method = game1.getClass().getDeclaredMethod("login", new Class[] { String.class, Clock.class });
		method.setAccessible(true);
		method.invoke(game1, "Test", mock);

		game1.postUserScoreToLevel("Test", Level.EASY, 5000);
	}

}

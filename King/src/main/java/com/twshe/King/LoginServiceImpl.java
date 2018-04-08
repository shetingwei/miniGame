package com.twshe.King;

import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class LoginServiceImpl implements LoginService {

	private Map<String, String> users = new ConcurrentHashMap<String, String>();
	private Map<String, Instant> sessions = new ConcurrentHashMap<String, Instant>();
	private final int TIME_LIMIT = 60;
	private Clock clock;
	
	private static LoginServiceImpl loginServiceImpl = new LoginServiceImpl();

	private LoginServiceImpl() {
		clock = Clock.systemUTC();
	}
	
	public static LoginServiceImpl getInstance() {
		return loginServiceImpl;
	}

	public String login(String name) throws IllegalStateException {
		String sessionKey = UUID.randomUUID().toString();
		String _sessionKey = users.putIfAbsent(name, sessionKey);
		if (_sessionKey != null) {
			throw new IllegalStateException("You have already login from the other device.");
		} else {
			sessions.putIfAbsent(sessionKey, clock.instant());
			return sessionKey;
		}
	}
	
	private String login(String name, Clock clock) throws IllegalStateException {
		String sessionKey = UUID.randomUUID().toString();
		String _sessionKey = users.putIfAbsent(name, sessionKey);
		if (_sessionKey != null) {
			throw new IllegalStateException("You have already login from the other device.");
		} else {
			sessions.putIfAbsent(sessionKey, clock.instant());
			return sessionKey;
		}
	}

	public boolean checkTimeOut(String name) {
		String sessionKey = users.get(name);
		Instant instant = clock.instant().minus(TIME_LIMIT, ChronoUnit.MINUTES);
		if (sessions.get(sessionKey).isBefore(instant)) {
			return true;
		} else {
			return false;
		}
	}
}

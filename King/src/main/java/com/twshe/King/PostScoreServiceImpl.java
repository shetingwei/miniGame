package com.twshe.King;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PostScoreServiceImpl implements PostScoreService {

	private class UserScore implements Comparable<UserScore> {

		User user;
		int score;

		public UserScore(User user, int score) {
			this.user = user;
			this.score = score;
		}

		@Override
		public int compareTo(UserScore another) {
			return another.score - this.score;
		}

		@Override
		public int hashCode() {
			return this.user.hashCode();
		}

		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof UserScore)) {
				return false;
			} else {
				UserScore userScore = (UserScore) obj;
				if (this.user.equals(userScore.user)) {
					return true;
				} else {
					return false;
				}
			}
		}
	}

	private Map<Level, List<UserScore>> records = new HashMap<Level, List<UserScore>>();

	private static PostScoreServiceImpl postScoreService = new PostScoreServiceImpl();

	private PostScoreServiceImpl() {

	}

	public static PostScoreServiceImpl getInstance() {
		return postScoreService;
	}

	public void postUserScoreToLevel(String name, Level level, int score) {
		if (records.containsKey(level)) {
			List<UserScore> list = records.get(level);
			UserScore userScore = new UserScore(new User(name), score);
			int index = list.indexOf(userScore);
			if (index >= 0) {
				list.get(index).score = Math.max(score, list.get(index).score);
			} else {
				list.add(userScore);
			}
			Collections.sort(list);
		} else {
			List<UserScore> list = new ArrayList<UserScore>();
			UserScore userScore = new UserScore(new User(name), score);
			list.add(userScore);
			records.put(level, list);
		}
	}

	public String getHighScoreList(Level level) {
		if(records.containsKey(level)) {
			return records.get(level).stream().map(r -> r.user.getName() + " " + r.score).limit(15).collect(Collectors.joining( "," ));
		}else {
			return null;
		}
	}
}

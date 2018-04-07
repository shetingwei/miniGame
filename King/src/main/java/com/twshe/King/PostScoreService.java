package com.twshe.King;

public interface PostScoreService {
	public void postUserScoreToLevel(String name, Level level, int score);
	public String getHighScoreList(Level level);
}

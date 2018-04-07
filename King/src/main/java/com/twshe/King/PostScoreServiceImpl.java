package com.twshe.King;

import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class PostScoreServiceImpl implements PostScoreService {

	private Map<Level, Map<String, Integer>> records = new ConcurrentHashMap<Level, Map<String, Integer>>();

	private static PostScoreServiceImpl postScoreService = new PostScoreServiceImpl();

	private PostScoreServiceImpl() {

	}

	public static PostScoreServiceImpl getInstance() {
		return postScoreService;
	}

	public void postUserScoreToLevel(String name, Level level, int score) {
		if (records.containsKey(level)) {
			if (records.get(level).containsKey(name)) {
				if (score > records.get(level).get(name)) {
					records.get(level).put(name, score);
				}
			} else {
				records.get(level).put(name, score);
			}
		} else {
			Map<String, Integer> map = new ConcurrentHashMap<String, Integer>();
			map.put(name, score);
			records.put(level, map);
		}
	}

	public String getHighScoreList(Level level) {
		if (records.containsKey(level)) {
			return records.get(level).entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
					.map(r -> r.getKey() + " " + r.getValue()).limit(15).collect(Collectors.joining(","));
		} else {
			return null;
		}
	}
}

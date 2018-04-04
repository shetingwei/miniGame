package com.twshe.King;

import java.util.concurrent.TimeoutException;

public class App 
{
    public static void main( String[] args )
    {
   
    	Thread thread1 = new Thread() {
    		public void run() {
    			Game game = new Game();
    			game.login("Ting");
    			try {
    				game.postUserScoreToLevel("Ting", Level.EASY, 1000);
    			}catch(TimeoutException ex) {
    				
    			}
    			game.display();
    			System.out.println(game.getHighScoreList(Level.EASY));
    		}
    	};
    	
    	Thread thread2 = new Thread() {
    		public void run() {
    			Game game = new Game();
    			game.login("Thomas");
    			try {
    				game.postUserScoreToLevel("Thomas", Level.EASY, 2000);
    			}catch(TimeoutException ex) {
    				
    			}
    			game.display();
    			System.out.println(game.getHighScoreList(Level.EASY));
    		}
    	};
    	
    	try {
    		thread1.start();
    		thread2.start();
    		thread1.join();
    		thread2.join();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
    	
    	
    }
}
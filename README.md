# miniGame

Enhancement after code review:<br/>
1. Create LoginService and PostScoreService to deliver better OOP design.<br/>
2. LoginService and PostScoreService are Singleton Class. Threads are able to share sessions and scores.<br/>
3. Remove scores from User based on security concern. PostScoreService has a map to record every user's score.<br/>
4. PostScoreService uses ConcurrentHashMap to consider concurrency.<br/>
5. LoginService's login function takes Clock as an input. It helps us do timeout testing with mocking technique.<br/>

What I learned from code review:<br/>
1. HashMap uses TreeSet to implement Hash chain, making complexity from O(n) to O(log n). Before Java 8, it used LinkedList.<br/>
2. I can use singleton class to make multithreads share data. I used to announce data as static variable to be shared.<br/>
3. Why can Lambda expression be used when interface has more than one method? Because interface is able to have default methods.<br/>



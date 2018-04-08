# miniGame

Enhancement after code review:<br/>
1. Create LoginService and PostScoreService to deliver better OOP design.<br/>
2. LoginService and PostScoreService are Singleton Class. Threads are able to share sessions and scores.<br/>
3. User ConcurrentHashMap to consider concurrency. putIfAbsent method is used to replace containsKey if-else statement.<br/>
4. Create overloading function login(String name, Clock clock). It is only used in test scope. With mocking technique, clock.instance() returns 60 minutes ealier instance to do timeout testing.<br/>
5. Implement unit test in AppTest. <br/>

What I learned from code review:<br/>
1. HashMap uses TreeSet to implement Hash chain, making complexity from O(n) to O(log n). Before Java 8, it used LinkedList.<br/>
2. I can use singleton class to make multithreads share data. I used to announce data as static variable to be shared.<br/>
3. Why can Lambda expression be used when interface has more than one method? Because interface is able to have default methods.<br/>
4. SynchronizedHashMap's each method is synchronized and lock is object level. While a thread using get or put method, it will lock the      whole collection, no other threads can use it. SynchronizedHashMap has performance overhead.<br/>
5. ConcurrentHashMap's lock is at bucket level, allowing threads do get and put simultaneously. ConcurrentHashMap has better performance.<br/>
6. Java 8 Streams lazy evaluation. each intermedia operation doesn't do operation immediately, it returns new stream and become pipelined. When the terminal operation is invoked, traversal of streams begins and associated operations are performed one by one.
7. I can use partitions to subdivide table into small pieces. It will enhance the speed of data access. I used to create index to fix the slow query problem.



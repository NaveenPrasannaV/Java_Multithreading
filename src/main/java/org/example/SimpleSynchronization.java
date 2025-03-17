package org.example;

class Counter {

  int count = 0;  // Shared counter variable
  int value = 0;  // Dummy variable (not affecting synchronization)

  /**
   * Simple increment method (❌ NOT thread-safe). - If multiple threads access this, they may read
   * the same `count` before updating it, leading to **race conditions** and incorrect final count.
   */
  public void increment() {
    int temp = count;  // Read current count
    Thread.yield();  // Allow thread switching (for testing race conditions)
    count = temp + 1;  // Increment count
  }

  /**
   * Synchronized method to ensure thread safety (✔ Thread-safe). - Only one thread can execute this
   * method at a time. - `synchronized` keyword prevents **race conditions**.
   */
  public synchronized void synchronizedIncrement() {
    value = 0;  // Resetting value (not needed for synchronization)
    int temp = count;  // Read current count
    Thread.yield();  // Allow thread switching
    count = temp + 1;  // Increment count
    value = 0;  // Resetting value again
  }

  /**
   * Increment method using a **synchronized block** (✔ Thread-safe). - Uses `synchronized (this)`
   * instead of synchronizing the whole method. - Allows finer control over locking, improving
   * performance in some cases.
   */
  public void blockSynchronizedIncrement() {
    value = 0;  // Resetting value
    synchronized (this) {  // Synchronize only this block
      int temp = count;  // Read current count
      Thread.yield();  // Allow thread switching
      count = temp + 1;  // Increment count
    }
    value = 0;  // Resetting value again
  }

  /**
   * Increment method using **class-level locking** (✔ Thread-safe). - Uses `synchronized
   * (Counter.class)` to ensure **only one thread across all instances** of `Counter` can modify
   * `count` at a time. - This is useful when multiple instances of `Counter` exist and we need a
   * single shared lock.
   */
  public void classLevelSynchronizedIncrement() {
    value = 0;
    synchronized (Counter.class) {  // Locks at the class level (affects all instances)
      int temp = count;  // Read current count
      Thread.yield();  // Allow thread switching
      count = temp + 1;  // Increment count
    }
    value = 0;
  }

  /**
   * Returns the current counter value.
   */
  public int getCount() {
    return count;
  }
}

public class SimpleSynchronization {

  public static void main(String[] args) throws InterruptedException {
    Counter counter = new Counter();

    // Create three threads that call different increment methods
    Thread t1 = new Thread(() -> {
      for (int i = 0; i < 1000; i++) {
        counter.synchronizedIncrement();  // Using method with synchronized keyword
      }
    });

    Thread t2 = new Thread(() -> {
      for (int i = 0; i < 1000; i++) {
        counter.blockSynchronizedIncrement();  // Using synchronized block method
      }
    });

    /**
     * counter.classLevelSynchronizedIncrement();
     * If uncommented, this will mix object and class-level locks
     *     🔹 Class-level lock (`synchronized (Counter.class)`) causes dynamic output because
     *     it doesn't synchronize with object-level locks (`synchronized (this)`).
     *     🔹 This means some threads can still modify `count` at the same time.
     *
     */

    Thread t3 = new Thread(() -> {
      for (int i = 0; i < 1000; i++) {
        counter.synchronizedIncrement();
        //counter.classLevelSynchronizedIncrement();  // Using class-level locking method
      }
    });

    // Start all threads
    t1.start();
    t2.start();
    t3.start();

    // Wait for all threads to finish execution
    t1.join();
    t2.join();
    t3.join();

    // Print the final counter value
    System.out.println("Final count: " + counter.getCount());
  }
}

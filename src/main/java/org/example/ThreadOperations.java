package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

// Class representing an employee database accessed by multiple threads
class Employees {

  // Shared list that multiple threads will access
  private List<String> db = new ArrayList<>();

  // Getter method to retrieve the list
  public List<String> getDb() {
    return db;
  }

  // Adds data to the list
  void addData(String data) {
    db.add(data);
  }

  // Deletes data from the list at a given index
  void deleteData(int i) {
    if (i < db.size()) { // Prevents IndexOutOfBoundsException
      db.remove(i);
    }
  }

  // Retrieves data at a specific index
  String getData(int i) {
    return (i < db.size()) ? db.get(i) : null; // Prevents IndexOutOfBoundsException
  }

  // Getter for the list (alternative to getDb)
  List<String> getDB() {
    return db;
  }
}


public class ThreadOperations {

  // Method to execute operations sequentially without threading
  public static void ExecutionWithoutThread() {
    Employees employees = new Employees();
    int initialValue = 1;
    int finalValue = 20;

    // Adding data to the list
    for (int i = initialValue; i <= finalValue; i++) {
      employees.addData("Data: " + i);
    }

    // Removing elements
    for (int i = initialValue; i <= employees.getDB().size(); i = i + 2) {
      employees.deleteData(i);
    }

    // Printing the final list after deletion
    for (String data : employees.getDB()) {
      System.out.println(data);
    }


  }

  // Method to execute operations using multiple threads
  public static void ExecutionWithThread() throws InterruptedException {
    Employees employees = new Employees();
    int initialValue = 1;
    int finalValue = 20;

    // Thread 1: Adds data to the list
    Thread t1 = new Thread(() -> {
      for (int i = initialValue; i <= finalValue; i++) {
        employees.addData("Data: " + i);
      }
    });

    // Thread 2: Deletes data at even indexes
    Thread t2 = new Thread(() -> {
      for (int i = initialValue; i <= employees.getDB().size(); i = i + 2) {
        employees.deleteData(i);
      }
    });

    // Thread 3: Prints all remaining data in the list
    Thread t3 = new Thread(() -> {
      for (String data : employees.getDB()) {
        System.out.println(data);
      }
    });

    // Start threads in order
    t1.start();
    t1.join(); // Ensure t1 completes before t2 starts

    t2.start();
    t2.join(); // Ensure t2 completes before t3 starts

    t3.start();
    t3.join(); // Ensure t3 completes before main method exits
  }

  public static String getValueInParallel() throws InterruptedException {
    System.out.println(Thread.currentThread().getName() + " : Connecting with DB...");
    Thread.sleep(1000);
    System.out.println(Thread.currentThread().getName() + " : Data fetched successfully...");
    return "User: Naveen Singh";
  }

  /**
   * 1. First try the main() and getValueInParallel() 2. Next try ExecutionWithoutThread() 3.
   * Finally try ExecutionWithThread()
   */
  public static void main(String[] args) throws InterruptedException {
    /**
     *A simple illustration of Main() is responsible for UI creation, which takes about 1 second,
     * while getValueInParallel() fetches data from the database, also taking about 1 second.
     * Instead of having Main() handle everything sequentially, the execution is made parallel.
     * Finally, the main thread waits until dbThread completes and adds the data to the UI using
     * join().
     */
    System.out.println(Thread.currentThread().getName() + " : Welcome to Bank Service....");
    System.out.println(Thread.currentThread().getName() + " : Loading... the UI");
    AtomicReference<String> name = new AtomicReference<>();
    Thread dbThread = new Thread(() -> {
      try {
        name.set(getValueInParallel());
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    });
    Thread.sleep(1000);
    System.out.println(Thread.currentThread().getName() + " : UI ready...");
    dbThread.start();
    dbThread.join();
    System.out.println(Thread.currentThread().getName() + " : Hello, " + name);

     //ExecutionWithoutThread();
     //ExecutionWithThread();
    System.out.println(Thread.currentThread().getName() + " : Thread Exits...");
  }

}

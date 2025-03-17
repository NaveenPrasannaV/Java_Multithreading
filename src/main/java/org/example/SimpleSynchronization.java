package org.example;

class Counter {

  int count = 0;
  int value = 0;

  //  public void increment() {
//    int temp = count;
//    Thread.yield();
//    count = temp + 1;
//  }

//  public synchronized void increment() {
//    value = 0;
//    int temp = count;
//    Thread.yield();
//    count = temp + 1;
//    value = 0;
//  }

  public void increment() {
    value = 0;
    synchronized (this) {
      int temp = count;
      Thread.yield();
      count = temp + 1;
    }
    value = 0;
  }

  public int getCount() {
    return count;
  }

}

public class SimpleSynchronization {

  public static void main(String[] args) throws InterruptedException {
    Counter counter = new Counter();
    Thread t1 = new Thread(() -> {
      for (int i = 0; i < 1000; i++) {
        counter.increment();
      }
    });

    Thread t2 = new Thread(() -> {
      for (int i = 0; i < 1000; i++) {
        counter.increment();
      }
    });

    Thread t3 = new Thread(() -> {
      for (int i = 0; i < 1000; i++) {
        counter.increment();
      }
    });

    t1.start();
    t2.start();
    t3.start();
    t1.join();
    t2.join();
    t3.join();

    System.out.println(counter.getCount());
  }
}

package org.example;

class Book extends Thread{
  void read(){
    System.out.println("reading the book");
  }
}

public class Demo {

  public static void main(String[] args) {
    System.out.println("Main thread");
    Book book = new Book();
    book.start();
  }

}

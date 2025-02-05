package org.example;

// Library class that contains a method to simulate entering the library section
class Library {

  // Method to print a message indicating the current thread and the action of entering the library section
  public void entry() {
    System.out.println(
        "Thread: " + Thread.currentThread().getName());  // Prints the current thread's name
    System.out.println(
        "entering into library section...");  // Message indicating entry into the library section
  }

}

// Book class extends Thread and represents a Book-related task (in this case, reading a book)
class Book extends Thread {

  // Method simulating reading a book
  void read() {
    System.out.println(
        "Thread: " + Thread.currentThread().getName());  // Prints the current thread's name
    System.out.println("reading the book");  // Message indicating reading the book
  }

  // Overriding the run method to perform the actual task of reading the book
  @Override
  public void run() {
    System.out.println(
        "Thread: " + Thread.currentThread().getName());  // Prints the current thread's name
    read();  // Calling the read() method to simulate reading a book
  }
}

// Notes class extends Library and implements Runnable
// It simulates a task of taking notes and entering a section
class Notes extends Library implements Runnable {

  // Method simulating taking notes
  void takeNotes() {
    System.out.println(
        "Thread: " + Thread.currentThread().getName());  // Prints the current thread's name
    System.out.println("Taking Notes...");  // Message indicating taking notes
  }

  // Overriding entry() method from Library to simulate entering the Notes section
  public void entry() {
    System.out.println(
        "Thread: " + Thread.currentThread().getName());  // Prints the current thread's name
    System.out.println(
        "entering into Notes section...");  // Message indicating entry into the Notes section
  }

  // Implementing the run() method from Runnable to perform the task of taking notes
  @Override
  public void run() {
    System.out.println(
        "Thread: " + Thread.currentThread().getName());  // Prints the current thread's name
    takeNotes();  // Calling the takeNotes() method to simulate taking notes
  }

}

// Main class where the program execution starts
public class GettingIntoThread {

  public static void main(String[] args) {
    // Prints the name of the main thread
    System.out.println("Thread: " + Thread.currentThread().getName());

    // Creating a new Book object and starting the thread (calls run() method in a new thread)
    Book b = new Book();
    b.start();  // Starts the thread, which will run the overridden run() method in a separate thread

    // Creating a Library object and calling entry() method in the main thread
    Library lib = new Library();
    lib.entry();  // Main thread calls the entry() method of Library

    // Creating a Notes object (it’s of type Library but an instance of Notes) and calling entry() method in the main thread
    Library lib1 = new Notes();
    lib1.entry();  // Main thread calls the overridden entry() method in Notes

    // Creating a Notes object and calling takeNotes() directly in the main thread (no new thread here)
    Notes notes = new Notes();
    notes.takeNotes();  // Main thread calls takeNotes() method of Notes directly
    notes.run();  // Directly calling run() - runs in the main thread, not in a new thread

    // Creating a new thread for Notes object and starting it (this will call run() in a new thread)
    Thread noteThread = new Thread(notes);
    noteThread.start();  // Starts the Notes thread, which will execute the run() method in a new thread

    //Using a Lambda Runnable to simulate reading e -books on a computer
    Runnable computer = () -> {
      System.out.println(
          "Thread: " + Thread.currentThread().getName());  // Prints the current thread's name
      System.out.println("Reading e-books in the computer");  // Simulates reading e-books
    };

    // Calling run() directly - this executes in the main thread
    computer.run();

    // Running computer task in a separate thread
    Thread computerThread = new Thread(computer);
    computerThread.start();  // Starts a new thread to execute the lambda Runnable
  }

}

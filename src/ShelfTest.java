/**
 * Name: Cam Clendenon
 * Date: 1 March, 2023 - 16 April, 2023
 * Explanation: Testing of the shelf class including: Constructors, Getters and Setters, Add and Remove book, and list book.
 * Equals and hashCode not included as not necessary.
 **/

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class ShelfTest {
    private int testShelfNumber;
    private String testSubject;
    private HashMap<Book, Integer> testBooks;
    private Shelf testShelf;
    private Book testBook = null;
    @BeforeEach
    void setUp() {
        testShelfNumber = -1;
        testSubject = "Adventure";
        testBooks = new HashMap<>();
        testShelf = new Shelf(testShelfNumber,testSubject);
        testBook = new Book("5297", "Count of Monte Cristo", "Adventure", 999, "Alexandre Dumas", LocalDate.of(0, 1, 1));
    }

    @AfterEach
    void tearDown() {
        testShelfNumber = -1;
        testSubject = null;
        testBooks = null;
        testShelf = null;
        testBook = null;
    }

    @Test
    void getShelfNumber() {
        System.out.println("Testing getShelfNumber()");

        assertEquals(testShelfNumber, testShelf.getShelfNumber());

        System.out.println("Test Passed!!!");
    }

    @Test
    void setShelfNumber() {
        System.out.println("Testing setShelfNumber()");

        assertEquals(testShelfNumber, testShelf.getShelfNumber());
        testShelf.setShelfNumber(69);
        assertNotEquals(testShelfNumber, testShelf.getShelfNumber());

        System.out.println("Test Passed!!!");

    }

    @Test
    void getSubject() {
        System.out.println("Testing getSubject()");

        assertEquals(testSubject, testShelf.getSubject());

        System.out.println("Test Passed!!!");
    }

    @Test
    void setSubject() {
        System.out.println("Testing setSubject()");

        assertEquals(testSubject, testShelf.getSubject());
        testShelf.setSubject("lasagna"); // I hate Mondays
        assertNotEquals(testSubject, testShelf.getSubject());

        System.out.println("Test Passed!!!");
    }

    @Test
    void getBooks() {
        System.out.println("Testing getBook()");

        assertEquals(testBooks, testShelf.getBooks());

        System.out.println("Test Passed!!!");
    }

    @Test
    void setBooks() {
        System.out.println("Testing setBooks()");

        HashMap<Book, Integer> newBooks = new HashMap<>();
        newBooks.put(testBook, 1);
        assertEquals(testBooks, testShelf.getBooks());
        testShelf.setBooks(newBooks);
        assertNotEquals(testBooks, testShelf.getBooks());

        System.out.println("Test Passed!!!");
    }

    @Test
    void getBookCount() {
        System.out.println("Testing getBookCount()");

        Random random = new Random();
        int bookCopies = random.nextInt(40);
        testBooks.put(testBook, bookCopies);
        testShelf.setBooks(testBooks); // This makes it so it uses out tester hashmap.
        assertEquals(bookCopies, testShelf.getBookCount(testBook));// Should be the same value
        testShelf.removeBook(testBook);
        assertNotEquals(bookCopies, testShelf.getBookCount(testBook)); // Should have decreased by one
        testShelf.addBook(testBook);
        assertEquals(bookCopies, testShelf.getBookCount(testBook)); // Should have reset back to original value

        System.out.println("Test Passed!!!");
    }

    @Test
    void addBook() {
        System.out.println("Testing addBook()");

        Book wrongSubject = new Book("42-w-87", "Hitchhikers Guide To the Galaxy", "sci-fi", 42, "Douglas Adams", LocalDate.now());
        assertEquals(Code.SUCCESS, testShelf.addBook(testBook));
        assertEquals(1, testShelf.getBookCount(testBook));
        assertEquals(Code.SHELF_SUBJECT_MISMATCH_ERROR, testShelf.addBook(wrongSubject));
        assertEquals(-1, testShelf.getBookCount(wrongSubject));

        System.out.println("Test Passed!!!");
    }

    @Test
    void removeBook() {
        System.out.println("Testing removeBook()");

        Book wrongSubject = new Book("42-w-87", "Hitchhikers Guide To the Galaxy", "sci-fi", 42, "Douglas Adams", LocalDate.now());
        assertEquals(Code.BOOK_NOT_IN_INVENTORY_ERROR, testShelf.removeBook(wrongSubject));
        //```````````Adding books to the shelf
        assertEquals(Code.SUCCESS, testShelf.addBook(testBook));
        assertEquals(1, testShelf.getBookCount(testBook));
        assertEquals(Code.SUCCESS, testShelf.addBook(testBook));
        assertEquals(2, testShelf.getBookCount(testBook));
       //```````````````Removing books from shelf
        assertEquals(Code.SUCCESS, testShelf.removeBook(testBook));
        assertEquals(1, testShelf.getBookCount(testBook));
        assertEquals(Code.SUCCESS, testShelf.removeBook(testBook));
        assertEquals(0, testShelf.getBookCount(testBook));
        //```````````````Should not have any books left to remove
        assertEquals(Code.BOOK_NOT_IN_INVENTORY_ERROR, testShelf.removeBook(testBook));

        System.out.println("Test Passed!!!");
    }

    @Test
    void listBooks() {
        System.out.println("Testing listBooks()");

        Random random = new Random();
        int bookCopies00 = random.nextInt(40);
        testBooks.put(testBook, bookCopies00);
        testShelf.setBooks(testBooks); // This makes it so it uses out tester hashmap.
        assertEquals(bookCopies00, testShelf.getBookCount(testBook));// Should be the same value

        StringBuilder testString = new StringBuilder();
        testString.append(" on shelf : " + testShelfNumber + " : " + testSubject + "\n");
        int counter = 0;
        for (Book b : testBooks.keySet()) {
            counter += testBooks.get(b);
            testString.append(b + " " + testBooks.get(b));
            testString.append("\n");
        }

        if (counter > 1) {
            testString.insert(0, counter + " books"); // Should put the book count right at the beginning of the string
        } else {
            testString.insert(0, counter + " book"); // Should put the book count right at the beginning of the string

        }
        assertEquals(testShelf.listBooks(), testString.toString());

        System.out.println("Test Passed!!!");
    }


}
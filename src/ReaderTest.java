import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Name: Cam Clendenon
 * Date: 7 February, 2023 - 16 April, 2023
 * Explanation: The testing of Reader.java.
 **/


class ReaderTest {

    private int testCardNumber;
    private String testName;
    private String testPhone;
    private List<Book> testBooks;
    private List<Book> testBooks1;
    private Book testBook1;
    private Book testBook2;
    private Book testBook3;
    Reader testReader;

    @BeforeAll
    static void mainSetUp(){
        System.out.println("Main setup, runs once before all");

    }
    @AfterAll
    static void mainTearDown(){
        System.out.println("Main tear down, runs once at the end");
    }
    @BeforeEach
    void setUp() {
        System.out.println("Runs once before each test");
        // 1,Drew Clinkenbeard,831-582-4007,2,42-w-87,2020-10-12,1337,2020-11-1
        testCardNumber = 1;
        testName = "Drew Clinkenbeard";
        testPhone = "831-582-4007";
        testBook1 = new Book("5297","Count of Monte Cristo","Adventure",999,"Alexandre Dumas", LocalDate.now());
        testBook2 = new Book("42-w-87","Hitchhikers Guide To the Galaxy","sci-fi",42,"Douglas Adams", LocalDate.now());
        testBook3 = new Book("34-w-34","Dune","sci-fi",235,"Frank Herbert",LocalDate.now());
        testBooks = new ArrayList<>();
        testBooks1 = new ArrayList<>();
        testBooks1.add(testBook3);
        testReader = new Reader(testCardNumber, testName, testPhone);
    }

    @AfterEach
    void tearDown() {
        System.out.println("Runs once AFTER each test");
        testCardNumber = 1;
        testName = null;
        testPhone = null;
        testBook1 = null;
        testBook2 = null;
        testBook3 = null;
        testBooks = null;
        testBooks1 = null;
    }

    @Test
    void addBook() {
        assertEquals(Code.SUCCESS,testReader.addBook(testBook2));
        assertNotEquals(Code.SUCCESS,testReader.addBook(testBook2));
        assertEquals(Code.BOOK_ALREADY_CHECKED_OUT_ERROR, testReader.addBook(testBook2));
    }

    @Test
    void removeBook() {
        assertNotEquals(Code.SUCCESS, testReader.removeBook(testBook2));
        testReader.addBook(testBook2);
        assertEquals(Code.SUCCESS, testReader.removeBook(testBook2));
    }

    @Test
    void hasBook() {
        assertFalse(testReader.hasBook(testBook2));
        testReader.addBook(testBook2);
        assertTrue(testReader.hasBook(testBook2));
    }

    @Test
    void getBookCount() {
        System.out.println("Testing getBookCount()");
        assertEquals(0, testReader.getBookCount());
        testReader.addBook(testBook1);
        assertEquals(1, testReader.getBookCount());
        testReader.removeBook(testBook1);
        assertEquals(0, testReader.getBookCount());
    }

    @Test
    void testEquals() {
        Reader reader2 = new Reader(1, "Drew Clinkenbeard","831-582-4007");
        assertEquals(true, testReader.equals(reader2));
    }

    @Test
    void testHashCode() {
        Reader reader2 = new Reader(6262, "Cam Clendenon", "Hah you thought");
        assertNotEquals(testReader.hashCode(), reader2.hashCode());
        reader2 = new Reader(1, "Drew Clinkenbeard","831-582-4007");
        assertEquals(testReader.hashCode(), reader2.hashCode());
    }

    @Test
    void testToString() {
        System.out.println("This is an unused test as it just makes a string.");
    }
//````````````````````````````````````````Test get and Set Stuff`````````````````````````````//
    @Test
    void getCardNumber() {
        assertEquals(testCardNumber, testReader.getCardNumber());
    }

    @Test
    void setCardNumber() {
        assertEquals(testCardNumber, testReader.getCardNumber());
        testReader.setCardNumber(6567);
        assertNotEquals(testCardNumber, testReader.getCardNumber());
    }

    @Test
    void getName() {
        assertEquals(testName, testReader.getName());
    }

    @Test
    void setName() {
        assertEquals(testName, testReader.getName());
        testReader.setName("AAAAAAAAAAAAAAAAAAAAAAaaaaaahhhhhhhhhhhhhhh");
        assertNotEquals(testName, testReader.getName());
    }

    @Test
    void getPhone() {
        assertEquals(testPhone, testReader.getPhone());
    }

    @Test
    void setPhone() {
        assertEquals(testPhone, testReader.getPhone());
        testReader.setPhone("800-888-8888"); // Don't wait call 8; I wonder if you remember this infomercial number
        assertNotEquals(testPhone, testReader.getPhone());
    }

    @Test
    void getBooks() {
        assertEquals(testBooks, testReader.getBooks());
    }

    @Test
    void setBooks() {
        assertEquals(testBooks, testReader.getBooks());
        testReader.setBooks(testBooks1);
        assertNotEquals(testBooks, testReader.getBooks());

    }


}
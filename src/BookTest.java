import org.junit.jupiter.api.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Name: Cam Clendenon - 16 April, 2023
 * Date: 5 February, 2023
 * Explanation: The Testing of the methods in Book.java (all of the Getters and Setters, constructor, equals() and hashCode()).
 **/

class BookTest {
//    1337,Headfirst Java,education,1337,Grady Booch,0000
    private String testISBN = "1337";
    private String testTitle = "Headfirst Java";
    private String testSubject = "education";
    private int testPageCount = 1337;
    private String testAuthor = "Grady Booch";
    private LocalDate testDueDate = LocalDate.now();

    private Book testBook = null;

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
        testBook = new Book(testISBN, testTitle, testSubject, testPageCount, testAuthor, testDueDate);
    }

    @AfterEach
    void tearDown() {
        System.out.println("Runs once AFTER each test");
        testBook = null;
    }

    @Test
    void testConstructor(){
        Book testBook2 = null;
        assertNull(testBook2);
        testBook2 = testBook;
        assertNotNull(testBook2);
        assertEquals(testBook2,testBook);
    }

    @Test
    void testToString() {
    }

    @Test
    void getIsbn() {
        assertEquals(testISBN, testBook.getIsbn());
    }

    @Test
    void setIsbn() {
        assertEquals(testISBN, testBook.getIsbn());
        testBook.setIsbn("1234");
        assertNotEquals(testISBN, testBook.getIsbn());
    }

    @Test
    void getTitle() {
        assertEquals(testTitle, testBook.getTitle());
    }

    @Test
    void setTitle() {
        assertEquals(testTitle, testBook.getTitle());
        testBook.setTitle("Fake Book");
        assertNotEquals(testTitle, testBook.getTitle());
    }

    @Test
    void getSubject() {
        assertEquals(testSubject, testBook.getSubject());
    }

    @Test
    void setSubject() {
        assertEquals(testSubject, testBook.getSubject());
        testBook.setSubject("Realism");
        assertNotEquals(testSubject, testBook.getSubject());
    }

    @Test
    void getPageCount() {
        assertEquals(testPageCount, testBook.getPageCount());
    }

    @Test
    void setPageCount() {
        assertEquals(testPageCount, testBook.getPageCount());
        testBook.setPageCount(100);
        assertNotEquals(testPageCount, testBook.getPageCount());
    }

    @Test
    void getAuthor() {
        assertEquals(testAuthor, testBook.getAuthor());
    }

    @Test
    void setAuthor() {
        assertEquals(testAuthor, testBook.getAuthor());
        testBook.setAuthor("Me, the greatest author of all time");
        assertNotEquals(testAuthor, testBook.getAuthor());
    }

    @Test
    void getDueDate() {
        assertEquals(testDueDate, testBook.getDueDate());
    }

    @Test
    void setDueDate() {
        assertEquals(testDueDate, testBook.getDueDate());
        testBook.setDueDate(LocalDate.of(2023,06,23));
        assertNotEquals(testDueDate, testBook.getDueDate());
    }

    @Test
    void testEquals() {
        Book book1= null;
        book1 = new Book("5297","Count of Monte Cristo","Adventure",999,"Alexandre Dumas",LocalDate.of(00,01,01));
        assertNotEquals(true, book1.equals(testBook));
        book1 = new Book(testBook.getIsbn(),testBook.getTitle(), testBook.getSubject(), testBook.getPageCount(), testBook.getAuthor(), testBook.getDueDate());
        assertEquals(true, book1.equals(testBook));
        book1 = null;

    }

    @Test
    void testHashCode() {
        Book book1= null;
        book1 = new Book("5297","Count of Monte Cristo","Adventure",999,"Alexandre Dumas",LocalDate.of(00,01,01));
        assertNotEquals( book1.hashCode(), testBook.hashCode());
        book1 = new Book(testBook.getIsbn(),testBook.getTitle(), testBook.getSubject(), testBook.getPageCount(), testBook.getAuthor(), testBook.getDueDate());
        assertEquals( book1.hashCode(), testBook.hashCode());

    }

}
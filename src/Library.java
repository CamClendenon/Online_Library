/**
 * Title: Project01-Part04 --> Library
 * Name: Cam Clendenon
 * Date: 24 March, 2023 - 16 April, 2023
 * Explanation: THis is the Fourth part of CST338 Project 1. Library adds onto and utilizes Book, Reader, and Shelf to make a
 * cohesive library that allows readers to check out available books on library shelves.
 **/

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.*;

import static java.lang.Integer.parseInt;

public class Library {
    public final static int LENDING_LIMIT = 5;
    private String name;
    private static int libraryCard;
    private List<Reader> readers;
    private HashMap<String, Shelf> shelves;
    private HashMap<Book, Integer> books;

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Constructor ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //
    public Library(String name) {
        this.name = name;
        readers = new ArrayList<>();
        shelves = new HashMap<>();
        books = new HashMap<>();
        libraryCard = 0;
    } // Probs done.

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Getters() and Setters() ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

    //`````````````````Name Get and Set````````````````//
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //``````````````Library Card Get and Set``````````````//

    public static int getLibraryCard() {
        return libraryCard;
    }

    public int getLibraryCardNumber() {
        return libraryCard + 1;
    }

    public static void setLibraryCard(int libraryCard) {
        Library.libraryCard = libraryCard;
    }

    //````````````````Reader and Related```````````````//

    public List<Reader> getReaders() {
        return readers;
    }

    public void setReaders(List<Reader> readers) {
        this.readers = readers;
    }

    public Reader getReaderByCard(int cardNumber) {
        for (Reader reader_Index : readers) {
            if (reader_Index.getCardNumber() == cardNumber) {
                return reader_Index;
            }
        }

        System.out.println("Could not find a reader with card #" + cardNumber);
        return null;
    }

    //``````````````````Shelf and Related ```````````````````//

    public HashMap<String, Shelf> getShelves() {
        return shelves;
    }

    public void setShelves(HashMap<String, Shelf> shelves) {
        this.shelves = shelves;
    }

    public Shelf getShelf(String subject) {
        for (Shelf shelf_Index : shelves.values()) {
            if (shelf_Index.getSubject().equals(subject)) {
                return shelf_Index;
            }
        }
        System.out.println("No shelf for " + subject + " books");
        return null;
    }

    public Shelf getShelf(int shelfNumber) {
        for (Shelf shelf_Index : shelves.values()) {
            if (shelf_Index.getShelfNumber() == shelfNumber) {
                return shelf_Index;
            }
        }
        System.out.println("No shelf number " + shelfNumber + " found");
        return null;
    }

    //```````````````````Books and Related`````````````//

    public HashMap<Book, Integer> getBooks() {
        return books;
    }

    public void setBooks(HashMap<Book, Integer> books) {
        this.books = books;
    }

    public Book getBookByISBN(String isbn) {
        for (Book findingBook : books.keySet()){
            if (findingBook.getIsbn().equals(isbn)){
                Book readersCopy = new Book(findingBook.getIsbn(), findingBook.getTitle(), findingBook.getSubject(), findingBook.getPageCount(), findingBook.getAuthor(), findingBook.getDueDate()); //Creates a reader only copy?
                return readersCopy;
            }
        }
        System.out.println("ERROR: Could not find a book with isbn: " + isbn);
        return null;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Add() and Remove() ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

    //`````````````````````Book ````````````````//
    public Code addBook(Book newBook) { //THis is adding to our books HashMap
        if (books.containsKey(newBook)) {
            books.put(newBook, books.get(newBook) + 1);
            System.out.println(books.get(newBook) + " copies of " + newBook.getTitle() + " in the stacks.");
        } else {
            books.put(newBook, 1);
            System.out.println(newBook.getTitle() + " added to the stacks.");
        }
        if (shelves.containsKey(newBook.getSubject())) {
            Shelf addingBook = shelves.get(newBook.getSubject());
            addingBook.addBook(newBook);
            return Code.SUCCESS;
        }
        System.out.println("No shelf for " + newBook.getTitle() + " books");
        return Code.SHELF_EXISTS_ERROR;
    }
    public Code returnBook(Reader reader, Book book) {
        if (!reader.hasBook(book)) {
            System.out.println(reader.getName() + " doesn't have " + book.getTitle() + " checked out");
            return Code.READER_DOESNT_HAVE_BOOK_ERROR;
        }
        if (!books.containsKey(book)) {
            return Code.BOOK_NOT_IN_INVENTORY_ERROR;
        }
        System.out.println(reader.getName() + " is returning " + book);
        Code readerReturn = reader.removeBook(book);
        if (readerReturn == Code.SUCCESS) {
            return returnBook(book);
        }
        System.out.println("Could not return " + book);
        return readerReturn;
    }

    public Code returnBook(Book book) {
        if (!shelves.containsKey(book.getSubject())){
            System.out.println("No shelf for " + book);
            return Code.SHELF_EXISTS_ERROR;
        }
        return shelves.get(book.getSubject()).addBook(book); //This should get the proper shelf and
        // then add the book back to the shelf
    }

    //```````````````````````Shelf ````````````````````````//
    public Code addShelf(Shelf shelf) {
        if (shelves.containsKey(shelf.getSubject())) {
            System.out.println("ERROR: Shelf already exists " + shelf);
            return Code.SHELF_EXISTS_ERROR;
        }
        shelves.put(shelf.getSubject(), shelf);
        for (Book book : books.keySet()) {
            if (book.getSubject().equals(shelf.getSubject())) {
                for ( int x = 0; x < books.get(book); x++) {
                    shelf.addBook(book);
                }
            }
        }

        return Code.SUCCESS;
    }

    public Code addShelf(String shelfSubject) {
        Shelf newShelf = new Shelf(shelves.size() + 1, shelfSubject);
        addShelf(newShelf);

        return Code.SUCCESS;
    }

    private Code addBookToShelf(Book book, Shelf shelf) {

        Code bookReturn = returnBook(book);
        if (bookReturn == Code.SUCCESS) {
            return bookReturn;
        }

        if (!book.getSubject().equals(shelf.getSubject())) {
            return Code.SHELF_SUBJECT_MISMATCH_ERROR;
        }

        Code adding_Book_Back = shelf.addBook(book);
        if (adding_Book_Back == Code.SUCCESS){
            System.out.println(book + " added back to shelf");

        } else {
            System.out.println("Could not add " + book + " to shelf");
        }
        return adding_Book_Back;
    }

    //`````````````````````Reader``````````````````//

    public Code addReader(Reader reader) {
        if (readers.contains(reader)){
            System.out.println(reader.getName() + " already has an account!");
            return Code.READER_ALREADY_EXISTS_ERROR;
        }
        for (Reader reader1 : readers) {
            if (reader1.getCardNumber() == reader.getCardNumber()){
                System.out.println(reader1.getName() + " and " + reader.getName() + " have the same card number!");
                return Code.READER_CARD_NUMBER_ERROR;
            }
        }
        readers.add(reader);
//        System.out.println(reader.getName() + " added to the library!");
        if (reader.getCardNumber() > libraryCard) {
            libraryCard = reader.getCardNumber();
        }
        return Code.SUCCESS;
    }

    public Code removeReader(Reader reader) {
        if (reader.getBooks() != null) {
            System.out.println(reader.getName() + " must return all books.");
            return Code.READER_STILL_HAS_BOOKS_ERROR;
        }
        if (!readers.contains(reader)) {
            System.out.println(reader + " is not part of this library");
            return Code.READER_NOT_IN_LIBRARY_ERROR;
        }
        readers.remove(reader);
        return Code.SUCCESS;
    } //Should be done

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ List() ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ // List should be done now
    public int listBooks() {
        int totalBooks = 0;
        for (Book copies : books.keySet()) {
            totalBooks += books.get(copies);
            System.out.println(books.get(copies) + " copies of " + copies);

        }
        return totalBooks;
    }

    public Code listShelves(boolean showBooks) {
        for (Shelf shelf_Index : shelves.values()) {
            if (!showBooks) {
                System.out.println(shelf_Index.toString());
                continue;
            }
            System.out.println(shelf_Index.listBooks());
        }
        return Code.SUCCESS;
    }

    public int listReaders() {
        int totalReaders = 0;
     for (Reader reader_Index : readers) {
         totalReaders += 1;
         System.out.println(reader_Index.toString());
     }
     return totalReaders;
    }

    public int listReaders(boolean showBooks) {
        int totalReaders = 0;
        for (Reader reader_Index : readers) {
            totalReaders += 1;

            if (!showBooks) {
                System.out.print(reader_Index.getName());
                System.out.println(" has the following books: \n");
                for (Book reader_book : reader_Index.getBooks()) {
                    System.out.print(reader_book.toString() + ", ");
                }
                System.out.println(); //Just to get a new line.
            } else {
                System.out.println(reader_Index.toString());
            }
        }
        return totalReaders;
    }


    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Convert() ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //
    public int convertInt(String recordCountString, Code code) {
        int integerForm = 0;
        try {
            integerForm = parseInt(recordCountString);
        } catch (NumberFormatException e) {
            System.out.println("Value which caused the error: " + recordCountString);
            System.out.println("Error message: " + code);
            if (code.equals(Code.BOOK_COUNT_ERROR)) {
                System.out.println("Error: Could not read number of books");
            } else if (code.equals(Code.PAGE_COUNT_ERROR)) {
                System.out.println("Error: could not parse page count");
            } else if (code.equals(Code.DATE_CONVERSION_ERROR)) {
                System.out.println("Error: Could not parse date component");
            } else {
                System.out.println("Error: Unknown conversion error");
            }
            return -1;
        }
        return integerForm;
    }

    public LocalDate convertDate(String date, Code errorCode) {
        LocalDate newDate = LocalDate.of(1970, 1, 1);
        String[] dateArray = date.split("-");
        if (!date.equals("0000")) { //This makes sure that it is not default date.
            if (dateArray.length != 3) {
                System.out.println("ERROR: date conversion error, could not parse " + date);
                System.out.println("Using default date (01-jan-1970)");
                return newDate;
            }
            int year = convertInt(dateArray[0], Code.DATE_CONVERSION_ERROR);
            int month = convertInt(dateArray[1], Code.DATE_CONVERSION_ERROR);
            int day = convertInt(dateArray[2], Code.DATE_CONVERSION_ERROR);
            if (year < 0 || month < 0 || day < 0) { //This checks if any can't be turned to integer.
                if (year < 0) {
                    System.out.println("Error converting date: Year " + dateArray[0]);
                }
                if (month < 0) {
                    System.out.println("Error converting date: Month " + dateArray[1]);
                }
                if (day < 0) {
                    System.out.println("Error converting date: Dat " + dateArray[2]);
                }
                System.out.println("Using default date (01-jan-1970)");
                return newDate;
            }
            newDate = LocalDate.of(year, month, day); //This makes it so, it will set the date with year, month, day
        }
        return newDate;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Other() ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //
    public Code init(String filename) {
        Scanner readFile;
        File rFile = new File(filename);
        try {
            readFile = new Scanner(rFile);
            if (!rFile.canRead() || !rFile.exists()) {
                throw new FileNotFoundException();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Could not find " + filename);
            return Code.FILE_NOT_FOUND_ERROR;
        }

        //``````````````````````Reading Books```````````````````````//
        int num_of_Books = convertInt(readFile.nextLine(), Code.BOOK_COUNT_ERROR);

        if (num_of_Books < 0) {
            return Code.UNKNOWN_ERROR;
        }
        System.out.println("parsing " + num_of_Books + " books");
        Code readBooks = initBooks(num_of_Books, readFile);
        if (readBooks != Code.SUCCESS) {
            return readBooks;
        }
        listBooks();

        //`````````````````````Reading Shelves``````````````````````//
        int num_of_Shelves = convertInt(readFile.nextLine(), Code.UNKNOWN_ERROR);
        System.out.println("parsing " + num_of_Shelves + " shelves");
//        System.out.println(num_of_Shelves);
        if (num_of_Shelves < 1) {
            return Code.UNKNOWN_ERROR;
        }
//        System.out.println("parsing " + num_of_Shelves + " shelves");
        Code readShelves = initShelves(num_of_Shelves, readFile);
        if (readShelves != Code.SUCCESS) {
            return readShelves;
        }
        listShelves(true);


        //`````````````````````Reading Readers`````````````````````//
        int num_of_Readers = convertInt(readFile.nextLine(), Code.READER_COUNT_ERROR);
        System.out.println("parsing " + num_of_Readers + " readers");
        if (num_of_Readers < 0) {
            return Code.UNKNOWN_ERROR;
        }

        Code readReaders = initReader(num_of_Readers, readFile);
        if (readReaders != Code.SUCCESS) {
            return readReaders;
        }
        listReaders();
        return Code.SUCCESS;
    }

    private Code initBooks(int bookCount, Scanner scan) {
        if (bookCount < 1) {
            System.out.println("Error: Book Count impossible");
            return Code.LIBRARY_ERROR;
        }
        for (int read_Book = 0; read_Book < bookCount; read_Book++) {
            String line = scan.nextLine();
            System.out.println("parsing book: " + line);
            String[] preBook = line.split(",");
            LocalDate date = convertDate(preBook[Book.DUE_DATE_], Code.DATE_CONVERSION_ERROR);
            int pageCount = convertInt(preBook[Book.PAGE_COUNT_], Code.PAGE_COUNT_ERROR);
            if (pageCount <= 0) {
                return Code.PAGE_COUNT_ERROR;
            } else if (date.isBefore(LocalDate.of(1970, 1, 1))) {
                return Code.DATE_CONVERSION_ERROR;
            }
            Book book = new Book(preBook[Book.ISBN_], preBook[Book.TITLE_], preBook[Book.SUBJECT_], pageCount, preBook[Book.AUTHOR_], date);
            addBook(book);
        }
        System.out.println("SUCCESS\n");
        return Code.SUCCESS;
    }

    private Code initShelves(int shelfCount, Scanner scan) {
        if (shelfCount < 1) {
            return Code.SHELF_COUNT_ERROR;
        }
        for (int next_Shelf = 0; next_Shelf < shelfCount; next_Shelf++) {
            String line = scan.nextLine();
            System.out.println("parsing Shelf: " + line);
            String[] preShelf = line.split(",");
            int shelfNumber = convertInt(preShelf[Shelf.SHELF_NUMBER_], Code.SHELF_NUMBER_PARSE_ERROR);
            if (shelfNumber < 0) {
                return Code.SHELF_NUMBER_PARSE_ERROR;
            }
            addShelf(preShelf[Shelf.SUBJECT_]);

        }
        if (shelves.size() != shelfCount) {
            System.out.println("Number of shelves doesn't match expected");
            return Code.SHELF_NUMBER_PARSE_ERROR;
        }
        System.out.println("SUCCESS\n");
        return Code.SUCCESS;
    }

    private Code initReader ( int readerCount, Scanner scan){
        if (readerCount < 1) {
            return Code.READER_COUNT_ERROR;
        }
        for (int x = 0; x < readerCount; x++) {
            String line = scan.nextLine();
            String[] preReader = line.split(",");
            int cardNumber = convertInt(preReader[Reader.CARD_NUMBER_], Code.READER_CARD_NUMBER_ERROR);
            if (cardNumber < 0) {
                return Code.READER_CARD_NUMBER_ERROR;
            }
            Reader newReader = new Reader(cardNumber, preReader[Reader.NAME_], preReader[Reader.PHONE_]);
            addReader(newReader);
            if (convertInt(preReader[Reader.BOOK_COUNT_], Code.BOOK_COUNT_ERROR) >= 1){
                for (int y = Reader.BOOK_START_; y < convertInt(preReader[Reader.BOOK_COUNT_], Code.UNKNOWN_ERROR) + Reader.BOOK_START_; y += 2){
                    Book addingBook = getBookByISBN(preReader[y]);
                    if (addingBook == null) {
                        continue;
                    }
                    //Make sure that this changes users book not libraries.
                    checkOutBook(newReader, addingBook);
                    addingBook.setDueDate(convertDate(preReader[y+1], Code.DUE_DATE_ERROR));
                }
            }
        }

        return Code.SUCCESS;
    }
    public Code checkOutBook(Reader reader, Book book) {

        //````````````````Checking reader info```````````//
        if (!readers.contains(reader)) {
            System.out.println(reader.getName() + " doesn't have an account here");
            return Code.READER_NOT_IN_LIBRARY_ERROR;
        }
        if (reader.getBookCount() >= LENDING_LIMIT) {
            System.out.println(reader.getName() + " has reached the lending limit, (" + LENDING_LIMIT + ")");
            return Code.BOOK_LIMIT_REACHED_ERROR;
        }

        //``````````````````Checking book in stock ```````````````//
        if (!this.books.containsKey(book)) { //Using this. to make sure we are referring to library's book object
            System.out.println("ERROR: could not find " + book);
            return Code.BOOK_NOT_IN_INVENTORY_ERROR;
        }
        if (!shelves.containsKey(book.getSubject())) {
            System.out.println("No shelf for " + book.getSubject() + " books");
            return Code.SHELF_EXISTS_ERROR;
        }

        Shelf forBook = shelves.get(book.getSubject());
        if (forBook.getBooks().get(book) < 1) {
            System.out.println("ERROR: no copies of " + book + " remain.");
            return Code.BOOK_NOT_IN_INVENTORY_ERROR;
        }

        //`````````````````````Checking checking out features. ````````````````````````//
        Code checkingOut = reader.addBook(book);
        if (checkingOut != Code.SUCCESS) {
            System.out.println("Couldn't checkout " + book);
            return checkingOut;
        }

        Code removeFromShelf = forBook.removeBook(book);
        if (removeFromShelf == Code.SUCCESS) {
            System.out.println(book + " checked out successfully");

        }
        return Code.SUCCESS;
    }

    private Code errorCode(int codeNumber) {
        for (Code code : Code.values()) {
            if (code.getCode() == codeNumber) {
                return code;
            }
        }
        return Code.UNKNOWN_ERROR;
    }
}

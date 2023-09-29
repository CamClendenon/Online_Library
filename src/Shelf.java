/**
 * Title: Project01-Part03 --> Shelf
 * Name: Cam Clendenon
 * Date: 27 February, 2023 - 16 April, 2023
 * Explanation: The continuation of Project01 where the shelf class is now added, books can be added and taken off
 * the shelf, all books on the shelf can be listed, books can be checked if they are on the shelf, can even compare if
 * two shelves are the same.
 **/

import java.util.HashMap;

public class Shelf {
    public static final int SHELF_NUMBER_ = 0;
    public static final int SUBJECT_ = 1;

    private int shelfNumber;
    private String subject;
    private HashMap<Book, Integer> books;

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Constructor ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

    public Shelf() {

    }

    public Shelf(int shelfNumber, String subject) {
        this.shelfNumber = shelfNumber;
        this.subject = subject;
        books = new HashMap<>();
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Getters and Setters ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

    public int getShelfNumber() {
        return shelfNumber;
    }

    public void setShelfNumber(int shelfNumber) {
        this.shelfNumber = shelfNumber;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public HashMap<Book, Integer> getBooks() {
        return books;
    }

    public void setBooks(HashMap<Book, Integer> books) {
        this.books = books;
    }
    public int getBookCount(Book book) {
        if (books.containsKey(book)){
            return books.get(book);
        }
        return -1;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Other Methods ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //
    public Code addBook(Book book) {
        if (!book.getSubject().equals(subject)){// && !books.containsKey(book)
            return  Code.SHELF_SUBJECT_MISMATCH_ERROR;
        }else {
            if (books.containsKey(book)) {
                books.put(book, books.get(book) + 1);
            } else { // Ask what to do if subject doesn't macth make error or just gloss over it?
                books.put(book, 1);
            }
            System.out.println(book + " added to shelf " + this);
            return Code.SUCCESS;
        }
    }
    public Code removeBook(Book book) {
        if (!books.containsKey(book)) {
            System.out.println(book.getTitle() + " is not on shelf " + subject);
            return Code.BOOK_NOT_IN_INVENTORY_ERROR;
        } else if (books.get(book) == 0) {
            System.out.println( "No copies of " + book.getTitle() + " remain on shelf " + subject);
            return  Code.BOOK_NOT_IN_INVENTORY_ERROR;
        }
        books.put(book, books.get(book) -1);
        System.out.println(book + " successfully removed from shelf" + subject);
        return Code.SUCCESS;
    }  // Done.
    public String listBooks() {
        StringBuilder temp = new StringBuilder();
        temp.append(" on shelf : " + shelfNumber + " : " + subject + "\n");
        int counter = 0;
        for (Book b : books.keySet()) {
            counter += books.get(b);
            temp.append(b + " " + books.get(b));
            temp.append("\n");
        }

        if (counter > 1) {
            temp.insert(0, counter + " books"); // Should put the book count right at the beginning of the string
        } else {
            temp.insert(0, counter + " book"); // Should put the book count right at the beginning of the string

        }
        return temp.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Shelf shelf = (Shelf) o;

        if (getShelfNumber() != shelf.getShelfNumber()) return false;
        return getSubject() != null ? getSubject().equals(shelf.getSubject()) : shelf.getSubject() == null;
    }

    @Override
    public int hashCode() {
        int result = getShelfNumber();
        result = 31 * result + (getSubject() != null ? getSubject().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return shelfNumber + " : " + subject;
    } //Should be done
}

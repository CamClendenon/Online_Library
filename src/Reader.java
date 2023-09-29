import java.util.ArrayList;
import java.util.List;

/**
 * Name: Cam Clendenon
 * Date: 7 February, 2023 - 16 April, 2023
 * Explanation: The continuation of Project part 1, Book.java, where now a visitor to the library may check out
 * books or return them.
 **/

public class Reader {
    public static final int CARD_NUMBER_ = 0;
    public static final int NAME_ = 1;
    public static final int PHONE_ = 2;
    public static final int BOOK_COUNT_ = 3;
    public static final int BOOK_START_ = 4;

    private int cardNumber;
    private String name;
    private String phone;
    private List<Book> books;

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Constructor ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //
    public Reader(int cardNumber, String name, String phone) {
        this.cardNumber = cardNumber;
        this.name = name;
        this.phone = phone;
        books = new ArrayList<>();
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Other Methods ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //
    public Code addBook(Book book){
        if(hasBook(book)){
            return Code.BOOK_ALREADY_CHECKED_OUT_ERROR;
        }
        books.add(book);

        return Code.SUCCESS;
    }

    public Code removeBook(Book book){
        if(!hasBook(book)){
            System.out.println(book.toString() + "is not checked out.");
            return Code.READER_DOESNT_HAVE_BOOK_ERROR;
        }
        books.remove(book);
        if(!hasBook(book)){
            return Code.SUCCESS;
        } else {
            return Code.READER_COULD_NOT_REMOVE_BOOK_ERROR;
        }

    }

    public boolean hasBook(Book book){
        for (Book book_index : books) {
            if (book.equals(book_index)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Reader reader = (Reader) o;

        if (getCardNumber() != reader.getCardNumber()) return false;
        if (getName() != null ? !getName().equals(reader.getName()) : reader.getName() != null) return false;
        return getPhone() != null ? getPhone().equals(reader.getPhone()) : reader.getPhone() == null;
    }

    @Override
    public int hashCode() {
        int result = getCardNumber();
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getPhone() != null ? getPhone().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return name + " (#" + cardNumber + ") has checked out (" + books + ")";
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Getters and Setters ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //
    public int getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public int  getBookCount(){
        return books.size();
    }
}

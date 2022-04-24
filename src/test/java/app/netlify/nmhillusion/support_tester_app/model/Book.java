package app.netlify.nmhillusion.support_tester_app.model;

/**
 * date: 2022-04-24
 * <p>
 * created-by: nmhillusion
 */

public class Book {
    private String bookName;
    private String author;
    private int price;

    public String getBookName() {
        return bookName;
    }

    public Book setBookName(String bookName) {
        this.bookName = bookName;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public Book setAuthor(String author) {
        this.author = author;
        return this;
    }

    public int getPrice() {
        return price;
    }

    public Book setPrice(Integer price) {
        this.price = price;
        return this;
    }

    public String askReadBook(String readerName, int times) {
        return "Hi " + readerName + ", how many times have you read the book '" + this.bookName + "' [$" + this.price + "]? " + times + " times?";
    }
}

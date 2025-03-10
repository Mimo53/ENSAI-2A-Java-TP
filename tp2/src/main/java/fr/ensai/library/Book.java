package fr.ensai.library;

/**
 * Represents a book, extending Item.
 */
public class Book extends Item {

    private String isbn;
    private Author author;

    /**
     * Constructs a new Book object.
     */
    public Book(String isbn, String title, Author author, int year, int pageCount) {
        super(title, year, pageCount);  // Appel du constructeur de Item
        this.isbn = isbn;
        this.author = author;
    }

    public Author getAuthor() {
        return author;
    }

    @Override
    public String getDetails() {
        return "Book: " + title + " written by " + author.toString() + ", ISBN: " + isbn + ", Year: " + year + ", Pages: " + pageCount;
    }
}

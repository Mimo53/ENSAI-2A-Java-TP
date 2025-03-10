package fr.ensai.library;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a Library.
 */
public class Library {
    private String name;
    private List<Book> books;

    /**
     * Constructs a new Library object.
     */
    public Library(String name) {
        this.name = name;
        this.books = new ArrayList<>();
    }

    /**
     * Adds a book to the library collection.
     */
    public void addBook(Book book) {
        books.add(book);
    }

    /**
     * Displays all books in the library.
     */
    public void displayBooks() {
        if (books.isEmpty()) {
            System.out.println("No books available in the library.");
        } else {
            for (Book book : books) {
                System.out.println(book);
            }
        }
    }

    /**
     * Loads books from a CSV file and adds them to the library.
     * 
     * @param filePath The path to the CSV file containing book data.
     */
    public void loadBooksFromCSV(String filePath) {
        // Récupérer l'URL du fichier CSV à partir du classpath
        URL url = getClass().getClassLoader().getResource(filePath);
        
        if (url == null) {
            System.out.println("Error: The file could not be found in the classpath.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(url.getFile()))) {
            String line;
            Map<String, Author> authors = new HashMap<>(); // Map pour éviter les doublons d'auteurs

            // Skip header line if CSV has a header
            br.readLine(); // To skip the header line if your CSV file contains one
            
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 5) {
                    String isbn = data[0].trim();
                    String title = data[1].trim();
                    String authorName = data[2].trim();
                    int year = Integer.parseInt(data[3].trim());
                    int pageCount = Integer.parseInt(data[4].trim());

                    // Gestion de l'auteur (vérification et ajout s'il n'existe pas déjà)
                    Author author = authors.get(authorName);
                    if (author == null) {
                        author = new Author(authorName, 0, "Unknown"); // Age et nationalité inconnus par défaut
                        authors.put(authorName, author);
                        System.out.println("New author added: " + author);
                    }

                    // Création du livre avec l'auteur et ajout à la bibliothèque
                    Book book = new Book(isbn, title, author, year, pageCount);
                    addBook(book);
                } else {
                    System.out.println("Warning: Skipping invalid line (not enough data fields): " + line);
                }
            }
            System.out.println("Books successfully loaded from CSV.");
        } catch (IOException e) {
            System.out.println("Error reading the CSV file: " + e.getMessage());
        }
    }
}

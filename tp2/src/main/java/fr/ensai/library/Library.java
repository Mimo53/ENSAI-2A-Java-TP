package fr.ensai.library;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;

public class Library {
    private String name;
    private List<Item> items;  // Liste des items, qui peuvent être des livres ou des magazines
    private List<Loan> activeLoans;  // Liste des prêts en cours
    private List<Loan> completedLoans;  // Liste des prêts terminés

    public Library(String name) {
        this.name = name;
        this.items = new ArrayList<>();
        this.activeLoans = new ArrayList<>();
        this.completedLoans = new ArrayList<>();
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void displayItems() {
        if (items.isEmpty()) {
            System.out.println("No items available in the library.");
        } else {
            for (Item item : items) {
                System.out.println(item.getDetails());
            }
        }
    }

    public void loadItemsFromCSV(String filePath) {
        URL url = getClass().getClassLoader().getResource(filePath);
        if (url == null) {
            System.out.println("Error: The file could not be found in the classpath.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(url.getFile()))) {
            String line;
            Map<String, Author> authors = new HashMap<>();

            br.readLine();  // Skip header if CSV has one

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 5) {
                    String title = data[1].trim();
                    String authorName = data[2].trim();
                    int year = Integer.parseInt(data[3].trim());
                    int pageCount = Integer.parseInt(data[4].trim());

                    Author author = authors.get(authorName);
                    if (author == null) {
                        author = new Author(authorName, 0, "Unknown");
                        authors.put(authorName, author);
                    }

                    Item item;
                    if (data[0].trim().equals("Book")) {
                        String isbn = data[0].trim();
                        item = new Book(isbn, title, author, year, pageCount);
                    } else if (data[0].trim().equals("Magazine")) {
                        String issn = data[0].trim();
                        int issueNumber = Integer.parseInt(data[4].trim());
                        item = new Magazine(title, issn, issueNumber, year, pageCount);
                    } else {
                        continue;
                    }

                    addItem(item);
                } else {
                    System.out.println("Warning: Skipping invalid line: " + line);
                }
            }
            System.out.println("Items successfully loaded from CSV.");
        } catch (IOException e) {
            System.out.println("Error reading the CSV file: " + e.getMessage());
        }
    }

    // Trouver un prêt actif pour un item
    public Loan findActiveLoanForItem(Item item) {
        for (Loan loan : activeLoans) {
            if (loan.getItem().equals(item)) {
                return loan;
            }
        }
        return null;
    }

    // Obtenir tous les livres d'un auteur
    public ArrayList<Book> getBooksByAuthor(Author author) {
        ArrayList<Book> booksByAuthor = new ArrayList<>();
        for (Item item : items) {
            if (item instanceof Book && ((Book) item).getAuthor().equals(author)) {
                booksByAuthor.add((Book) item);
            }
        }
        return booksByAuthor;
    }

    // Emprunter un item
    public boolean loanItem(Item item, Student student) {
        if (item != null && !isItemLoaned(item)) {
            Loan loan = new Loan(student, item, new Date());
            activeLoans.add(loan);
            System.out.println("Loan created: " + loan);
            return true;
        } else {
            System.out.println("Item is already loaned or unavailable.");
            return false;
        }
    }

    // Retourner un item
    public boolean renderItem(Item item) {
        Loan loan = findActiveLoanForItem(item);
        if (loan != null) {
            loan.setReturnDate(new Date());
            activeLoans.remove(loan);
            completedLoans.add(loan);
            System.out.println("Item returned: " + loan);
            return true;
        }
        return false;
    }

    // Afficher les prêts actifs
    public void displayActiveLoans() {
        if (activeLoans.isEmpty()) {
            System.out.println("No active loans.");
        } else {
            for (Loan loan : activeLoans) {
                System.out.println(loan);
            }
        }
    }

    // Vérifier si un item est déjà prêté
    private boolean isItemLoaned(Item item) {
        return findActiveLoanForItem(item) != null;
    }

    public List<Loan> getActiveLoans() {
        return activeLoans;
    }

    public List<Loan> getCompletedLoans() {
        return completedLoans;
    }
}

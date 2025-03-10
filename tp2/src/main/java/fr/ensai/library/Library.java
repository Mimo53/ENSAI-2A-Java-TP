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

/**
 * Represents a Library.
 */
public class Library {
    private String name;
    private List<Item> items;  // Liste des items, qui peuvent être des livres ou des magazines
    private List<Loan> activeLoans;  // Liste des prêts en cours
    private List<Loan> completedLoans;  // Liste des prêts terminés

    /**
     * Constructs a new Library object.
     */
    public Library(String name) {
        this.name = name;
        this.items = new ArrayList<>();
        this.activeLoans = new ArrayList<>();
        this.completedLoans = new ArrayList<>();
    }

    /**
     * Adds an item (book or magazine) to the library collection.
     */
    public void addItem(Item item) {
        items.add(item);
    }

    /**
     * Displays all items (books and magazines) in the library.
     */
    public void displayItems() {
        if (items.isEmpty()) {
            System.out.println("No items available in the library.");
        } else {
            for (Item item : items) {
                System.out.println(item.getDetails());
            }
        }
    }

    /**
     * Loads items from a CSV file and adds them to the library.
     * 
     * @param filePath The path to the CSV file containing item data.
     */
    public void loadItemsFromCSV(String filePath) {
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

                    // Création de l'item (livre ou magazine)
                    Item item;
                    if (data[0].trim().equals("Book")) {
                        String isbn = data[0].trim();
                        item = new Book(isbn, title, author, year, pageCount);
                    } else if (data[0].trim().equals("Magazine")) {
                        String issn = data[0].trim();
                        int issueNumber = Integer.parseInt(data[4].trim());
                        item = new Magazine(title, issn, issueNumber, year, pageCount);
                    } else {
                        continue;  // Ignore if type is unknown
                    }

                    addItem(item);
                } else {
                    System.out.println("Warning: Skipping invalid line (not enough data fields): " + line);
                }
            }
            System.out.println("Items successfully loaded from CSV.");
        } catch (IOException e) {
            System.out.println("Error reading the CSV file: " + e.getMessage());
        }
    }

    /**
     * Loans an item to a student.
     */
    public void loanItem(Student student, Item item) {
        // Vérifie si l'item est disponible (non prêté)
        if (item != null && !isItemLoaned(item)) {
            Loan loan = new Loan(student, item, new Date());
            activeLoans.add(loan);
            System.out.println("Loan created: " + loan);
        } else {
            System.out.println("Item is already loaned or unavailable.");
        }
    }

    /**
     * Returns an item and updates the loan status.
     */
    public void returnItem(Loan loan) {
        loan.setReturnDate(new Date());  // Met à jour la date de retour
        activeLoans.remove(loan);  // Retire le prêt de la liste des prêts actifs
        completedLoans.add(loan);  // Ajoute le prêt à la liste des prêts terminés
        System.out.println("Item returned: " + loan);
    }

    /**
     * Checks if an item is already loaned out.
     */
    public boolean isItemLoaned(Item item) {
        for (Loan loan : activeLoans) {
            if (loan.toString().contains(item.getDetails())) {
                return true;
            }
        }
        return false;
    }

    public List<Loan> getActiveLoans() {
        return activeLoans;
    }

    public List<Loan> getCompletedLoans() {
        return completedLoans;
    }
}

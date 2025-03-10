package fr.ensai.library;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        // Création de quelques auteurs
        Author tolkien = new Author("J.R.R. Tolkien", 81, "UK");
        Author lewis = new Author("C.S. Lewis", 64, "UK");

        // Création de quelques livres
        Book fellowshipOfTheRing = new Book("978-0-618-26025-6", "The Fellowship of the Ring", tolkien, 1954, 423);
        Book narnia = new Book("978-0-06-623850-0", "The Lion, the Witch and the Wardrobe", lewis, 1950, 208);

        // Création de quelques magazines
        Magazine timeMagazine = new Magazine("Time Magazine", "1234-5678", 1001, 2023, 60);
        Magazine natGeo = new Magazine("National Geographic", "9876-5432", 2203, 2023, 100);

        // Création de la bibliothèque
        Library myLibrary = new Library("Central Library");

        // Ajout des items dans la bibliothèque
        myLibrary.addItem(fellowshipOfTheRing);
        myLibrary.addItem(narnia);
        myLibrary.addItem(timeMagazine);
        myLibrary.addItem(natGeo);

        // Test de la recherche d'un prêt actif pour un item
        Loan loan = myLibrary.findActiveLoanForItem(fellowshipOfTheRing);
        if (loan != null) {
            System.out.println("Found active loan for item: " + loan);
        } else {
            System.out.println("No active loan found for the item.");
        }

        // Test de l'obtention des livres d'un auteur
        ArrayList<Book> tolkienBooks = myLibrary.getBooksByAuthor(tolkien);
        System.out.println("Books by J.R.R. Tolkien:");
        for (Book book : tolkienBooks) {
            System.out.println(book.getDetails());
        }

        // Emprunter un item
        Student student1 = new Student("Alice", 20, 2, false);
        boolean loanSuccess = myLibrary.loanItem(narnia, student1);
        if (loanSuccess) {
            System.out.println("Book successfully borrowed.");
        }

        // Retourner un item
        boolean renderSuccess = myLibrary.renderItem(fellowshipOfTheRing);
        if (renderSuccess) {
            System.out.println("Book successfully returned.");
        }

        // Afficher les prêts actifs
        myLibrary.displayActiveLoans();
    }
}

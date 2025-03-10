package fr.ensai.library;

import java.util.Date;

public class Main {

    public static void main(String[] args) {

        // Création de quelques auteurs
        Author tolkien = new Author("J.R.R. Tolkien", 81, "UK");
        Author lewis = new Author("C.S. Lewis", 64, "UK");

        // Création de quelques livres
        Book fellowshipOfTheRing = new Book(
                "978-0-618-26025-6",
                "The Fellowship of the Ring",
                tolkien,
                1954,
                423);

        Book narnia = new Book(
                "978-0-06-623850-0",
                "The Lion, the Witch and the Wardrobe",
                lewis,
                1950,
                208);

        // Création de quelques magazines
        Magazine timeMagazine = new Magazine(
                "Time Magazine",
                "1234-5678",
                1001,
                2023,
                60);

        Magazine natGeo = new Magazine(
                "National Geographic",
                "9876-5432",
                2203,
                2023,
                100);

        // Affichage de l'élément créé
        System.out.println(fellowshipOfTheRing.getDetails());
        System.out.println(narnia.getDetails());
        System.out.println(timeMagazine.getDetails());
        System.out.println(natGeo.getDetails());

        // Création de la bibliothèque
        Library myLibrary = new Library("Central Library");

        // Ajout des items dans la bibliothèque
        myLibrary.addItem(fellowshipOfTheRing);
        myLibrary.addItem(narnia);
        myLibrary.addItem(timeMagazine);
        myLibrary.addItem(natGeo);

        // Charger les items depuis le fichier CSV
        myLibrary.loadItemsFromCSV("books.csv");

        // Afficher les items dans la bibliothèque
        myLibrary.displayItems();

        // Création de quelques étudiants
        Student student1 = new Student("Alice", 20, 2, false);
        Student student2 = new Student("Bob", 21, 3, true);

        // Emprunt d'un livre par Alice
        System.out.println("\n--- Lending Books ---");
        myLibrary.loanItem(student1, fellowshipOfTheRing);  // Alice emprunte "The Fellowship of the Ring"
        myLibrary.loanItem(student1, timeMagazine);  // Alice emprunte "Time Magazine"
        
        // Tentative d'emprunt d'un autre livre (cela échouera, car Alice a déjà emprunté "The Fellowship of the Ring")
        myLibrary.loanItem(student2, fellowshipOfTheRing);  // Bob tente d'emprunter "The Fellowship of the Ring", mais il est déjà emprunté

        // Affichage des prêts actifs
        System.out.println("\n--- Active Loans ---");
        for (Loan loan : myLibrary.getActiveLoans()) {
            System.out.println(loan);
        }

        // Retour du livre "The Fellowship of the Ring"
        System.out.println("\n--- Returning Books ---");
        Loan loan = myLibrary.getActiveLoans().get(0);  // Prendre le premier prêt actif (Alice + "The Fellowship of the Ring")
        myLibrary.returnItem(loan);

        // Affichage des prêts terminés (livre retourné)
        System.out.println("\n--- Completed Loans ---");
        for (Loan completedLoan : myLibrary.getCompletedLoans()) {
            System.out.println(completedLoan);
        }
    }
}

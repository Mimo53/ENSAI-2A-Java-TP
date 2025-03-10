package fr.ensai.library;

public abstract class Item {
    protected String title;
    protected int year;
    protected int pageCount;

    // Constructeur de la classe Item
    public Item(String title, int year, int pageCount) {
        this.title = title;
        this.year = year;
        this.pageCount = pageCount;
    }

    // Méthodes abstraites pour être implémentées par les sous-classes
    public abstract String getDetails();
}

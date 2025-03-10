package fr.ensai.library;

/**
 * Represents a magazine, extending Item.
 */
public class Magazine extends Item {

    private String issn;
    private int issueNumber;

    /**
     * Constructs a new Magazine object.
     */
    public Magazine(String title, String issn, int issueNumber, int year, int pageCount) {
        super(title, year, pageCount);  // Appel du constructeur de Item
        this.issn = issn;
        this.issueNumber = issueNumber;
    }

    @Override
    public String getDetails() {
        return "Magazine: " + title + ", ISSN: " + issn + ", Issue No: " + issueNumber + ", Year: " + year + ", Pages: " + pageCount;
    }
}

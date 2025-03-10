package fr.ensai.library;

import java.util.Date;

/**
 * Represents a loan in the library system.
 */
public class Loan {

    private Student student;
    private Item item;
    private Date startDate;
    private Date returnDate;

    /**
     * Constructs a new Loan object.
     */
    public Loan(Student student, Item item, Date startDate) {
        this.student = student;
        this.item = item;
        this.startDate = startDate;
        this.returnDate = null; // The return date is initially null
    }

    /**
     * Gets the item that was loaned.
     */
    public Item getItem() {
        return item;
    }

    /**
     * Sets the return date for the loan.
     */
    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    /**
     * Gets the return date of the loan.
     */
    public Date getReturnDate() {
        return returnDate;
    }

    @Override
    public String toString() {
        return "Item: " + item.getDetails() + " borrowed by " + student.toString() + ".";
    }
}

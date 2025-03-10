package fr.ensai.library;

/**
 * Represents a student in the library system.
 */
public class Student {

    private String name;
    private int age;
    private int academicYear;
    private boolean isClassDelegate;

    /**
     * Constructs a new Student object.
     */
    public Student(String name, int age, int academicYear, boolean isClassDelegate) {
        this.name = name;
        this.age = age;
        this.academicYear = academicYear;
        this.isClassDelegate = isClassDelegate;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getAcademicYear() {
        return academicYear;
    }

    public boolean isClassDelegate() {
        return isClassDelegate;
    }

    @Override
    public String toString() {
        return name + " (Age: " + age + ", Year: " + academicYear + ")";
    }
}

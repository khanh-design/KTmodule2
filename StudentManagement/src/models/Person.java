package models;

public abstract class Person {
    protected String id;
    protected String name;
    protected String dateOfBirth;
    protected String gender;
    protected String phone;

    public Person(String id, String name, String dateOfBirth, String gender, String phone) {
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.phone = phone;
    }
    
    public abstract void displayInfo();

    // Getters
    public String getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public String getDateOfBirth() {
        return dateOfBirth;
    }
    
    public String getGender() {
        return gender;
    }
    
    public String getPhone() {
        return phone;
    }
}

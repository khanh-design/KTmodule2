package models;

public class Teacher extends Person {
    private String teacherId;
    
    public Teacher(String teacherId, String name, String dateOfBirth, String gender, String phone) {
        super(teacherId, name, dateOfBirth, gender, phone);
        this.teacherId = teacherId;
    }
    
    @Override
    public void displayInfo() {
        System.out.printf("%-10s %-20s %-12s %-8s %-10s\n", teacherId, name, dateOfBirth, gender, phone);
    }
    
    // Getter
    public String getTeacherId() {
        return teacherId;
    }
}

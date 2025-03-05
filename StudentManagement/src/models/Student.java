package models;

public class Student extends Person {
    private int studentId;
    private String classId;
    
    public Student(int studentId, String name, String dateOfBirth, String gender, String phone, String classId) {
        super(String.valueOf(studentId), name, dateOfBirth, gender, phone);
        this.studentId = studentId;
        this.classId = classId;
    }
    
    @Override
    public void displayInfo() {
        System.out.printf("%-5d %-20s %-12s %-8s %-10s\n", studentId, name, dateOfBirth, gender, phone);
    }
    
    // Getters
    public int getStudentId() {
        return studentId;
    }
    
    public String getClassId() {
        return classId;
    }
}

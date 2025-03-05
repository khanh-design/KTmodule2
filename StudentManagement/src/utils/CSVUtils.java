package utils;

import models.Student;
import models.Classroom;
import models.Teacher;
import java.io.*;
import java.util.*;

public class CSVUtils {
    public static List<Student> readStudents(String filePath) {
        List<Student> students = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if(parts.length < 6) continue;
                int studentId = Integer.parseInt(parts[0]);
                String name = parts[1];
                String dateOfBirth = parts[2];
                String gender = parts[3];
                String phone = parts[4];
                String classId = parts[5];
                students.add(new Student(studentId, name, dateOfBirth, gender, phone, classId));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return students;
    }
    
    public static void writeStudents(String filePath, List<Student> students) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath))) {
            pw.println("studentId,name,dateOfBirth,gender,phone,classId");
            for (Student s : students) {
                pw.println(s.getStudentId() + "," + s.getName() + "," + s.getDateOfBirth() + ","
                        + s.getGender() + "," + s.getPhone() + "," + s.getClassId());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static List<Classroom> readClassrooms(String filePath) {
        List<Classroom> classrooms = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if(parts.length < 3) continue;
                String classId = parts[0];
                String className = parts[1];
                String teacherId = parts[2];
                classrooms.add(new Classroom(classId, className, teacherId));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return classrooms;
    }
    
    public static List<Teacher> readTeachers(String filePath) {
        List<Teacher> teachers = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if(parts.length < 5) continue;
                String teacherId = parts[0];
                String name = parts[1];
                String dateOfBirth = parts[2];
                String gender = parts[3];
                String phone = parts[4];
                teachers.add(new Teacher(teacherId, name, dateOfBirth, gender, phone));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return teachers;
    }
}

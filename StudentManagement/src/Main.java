import models.Student;
import models.Classroom;
import models.Teacher;
import exceptions.NotFoundStudentException;
import utils.CSVUtils;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.*;

public class Main {
    private static final String STUDENTS_FILE = "D:\\KTmodule2\\StudentManagement\\data\\students.csv";
    private static final String CLASSROOMS_FILE = "D:\\KTmodule2\\StudentManagement\\data\\batchs.csv";
    private static final String TEACHERS_FILE = "D:\\KTmodule2\\StudentManagement\\data\\teachers.csv";
    
    private static List<Student> students;
    private static List<Classroom> classrooms;
    private static List<Teacher> teachers;
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        students = CSVUtils.readStudents(STUDENTS_FILE);
        classrooms = CSVUtils.readClassrooms(CLASSROOMS_FILE);
        teachers = CSVUtils.readTeachers(TEACHERS_FILE);
        while (true) {
            System.out.println("\n===== MENU =====");
            System.out.println("1. Them moi sinh vien");
            System.out.println("2. Xoa sinh vien");
            System.out.println("3. Xem danh sach sinh vien");
            System.out.println("4. Xem thong tin giao vien theo ma giao vien");
            System.out.println("5. Tim kiem sinh vien");
            System.out.println("6. Thoat");
            System.out.print("Chon chuc nang: ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    addStudent(scanner);
                    break;
                case "2":
                    deleteStudent(scanner);
                    break;
                case "3":
                    listStudents();
                    break;
                case "4":
                    viewTeacherInfo(scanner);
                    break;
                case "5":
                    searchStudent(scanner);
                    break;
                case "6":
                    System.out.println("Thoat chuong trinh. Bye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Lua chon khong hop le.");
            }
        }
    }
    
    private static void addStudent(Scanner scanner) {
        try {
            System.out.println("\n=== Them moi sinh vien ===");
            int newId = students.isEmpty() ? 1 : students.get(students.size() - 1).getStudentId() + 1;
            System.out.print("Nhap ten sinh vien: ");
            String name = scanner.nextLine();
            if (name == null || name.trim().isEmpty() || name.length() < 4 || name.length() > 50) {
                System.out.println("Ten sinh vien phai tu 4 den 50 ky tu.");
                return;
            }
            System.out.print("Nhap ngay sinh (dd/MM/yyyy): ");
            String dob = scanner.nextLine();
            if (!isValidDate(dob)) {
                System.out.println("Ngay sinh khong dung dinh dang dd/MM/yyyy.");
                return;
            }
            System.out.print("Nhap gioi tinh: ");
            String gender = scanner.nextLine();
            if (gender == null || gender.trim().isEmpty()){
                System.out.println("Gioi tinh khong duoc de trong.");
                return;
            }
            System.out.print("Nhap so dien thoai: ");
            String phone = scanner.nextLine();
            if (!isValidPhone(phone)) {
                System.out.println("So dien thoai khong hop le. Phai la 10 so va bat dau bang 090 hoac 091.");
                return;
            }
            for (Student s : students) {
                if (s.getPhone().equals(phone)) {
                    System.out.println("So dien thoai da ton tai.");
                    return;
                }
            }
            System.out.print("Nhap ma lop hoc: ");
            String classId = scanner.nextLine();
            if (!existsClassroom(classId)) {
                System.out.println("Ma lop hoc khong ton tai.");
                return;
            }
            Student newStudent = new Student(newId, name, dob, gender, phone, classId);
            students.add(newStudent);
            CSVUtils.writeStudents(STUDENTS_FILE, students);
            System.out.println("Them sinh vien thanh cong.");
        } catch(Exception e) {
            System.out.println("Co loi khi them sinh vien: " + e.getMessage());
        }
    }
    
    private static void deleteStudent(Scanner scanner) {
        try {
            System.out.println("\n=== Xoa sinh vien ===");
            System.out.print("Nhap ma sinh vien can xoa: ");
            int id = Integer.parseInt(scanner.nextLine());
            Student studentToDelete = null;
            for (Student s : students) {
                if (s.getStudentId() == id) {
                    studentToDelete = s;
                    break;
                }
            }
            if (studentToDelete == null) {
                throw new NotFoundStudentException("Sinh vien khong ton tai.");
            }
            System.out.print("Ban co chac chan muon xoa sinh vien nay? (Yes/No): ");
            String confirm = scanner.nextLine();
            if (confirm.equalsIgnoreCase("Yes")) {
                students.remove(studentToDelete);
                CSVUtils.writeStudents(STUDENTS_FILE, students);
                System.out.println("Xoa sinh vien thanh cong. Danh sach sau khi xoa:");
                listStudents();
            } else {
                System.out.println("Huy xoa, quay ve menu chinh.");
            }
        } catch (NotFoundStudentException e) {
            System.out.println(e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Ma sinh vien phai la so.");
        }
    }
    
    private static void listStudents() {
        System.out.println("\n=== Danh sach sinh vien ===");
        System.out.printf("%-5s %-20s %-12s %-8s %-20s\n", "ID", "Ten sinh vien", "Ngay sinh", "GT", "Ten lop hoc");
        for (Student s : students) {
            String className = getClassName(s.getClassId());
            System.out.printf("%-5d %-20s %-12s %-8s %-20s\n", s.getStudentId(), s.getName(), s.getDateOfBirth(), s.getGender(), className);
        }
    }
    
    private static void viewTeacherInfo(Scanner scanner) {
        System.out.println("\n=== Xem thong tin giao vien ===");
        System.out.print("Nhap ma giao vien: ");
        String teacherId = scanner.nextLine();
        boolean found = false;
        System.out.printf("%-10s %-20s %-12s %-8s %-10s\n", "ID", "Ten", "Ngay sinh", "GT", "Phone");
        for (Teacher t : teachers) {
            if (t.getTeacherId().equals(teacherId)) {
                t.displayInfo();
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Khong tim thay giao vien voi ma: " + teacherId);
        }
    }
    
    private static void searchStudent(Scanner scanner) {
        System.out.println("\n=== Tim kiem sinh vien ===");
        System.out.print("Nhap tu khoa tim kiem (ten sinh vien): ");
        String keyword = scanner.nextLine().toLowerCase();
        boolean found = false;
        System.out.printf("%-5s %-20s %-12s %-8s %-20s\n", "ID", "Ten sinh vien", "Ngay sinh", "GT", "Ten lop hoc");
        for (Student s : students) {
            if (s.getName().toLowerCase().contains(keyword)) {
                String className = getClassName(s.getClassId());
                System.out.printf("%-5d %-20s %-12s %-8s %-20s\n", s.getStudentId(), s.getName(), s.getDateOfBirth(), s.getGender(), className);
                found = true;
            }
        }
        if (!found) {
            System.out.println("Khong tim thay sinh vien voi tu khoa: " + keyword);
        }
    }
    
    private static boolean isValidDate(String date) {
        try {
            new SimpleDateFormat("dd/MM/yyyy").parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
    
    private static boolean isValidPhone(String phone) {
        return phone.matches("^(090|091)\\d{7}$");
    }
    
    private static boolean existsClassroom(String classId) {
        for (Classroom c : classrooms) {
            if (c.getClassId().equals(classId)) {
                return true;
            }
        }
        return false;
    }
    
    private static String getClassName(String classId) {
        for (Classroom c : classrooms) {
            if (c.getClassId().equals(classId)) {
                return c.getClassName();
            }
        }
        return "N/A";
    }
}

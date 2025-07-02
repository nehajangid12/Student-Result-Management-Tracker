import java.util.*;
import java.io.*;

// Student class
class Student {
    private String name;
    private int roll;
    private int[] marks = new int[3];
    private int total;
    private double percentage;
    private String grade;

    public Student(String name, int roll, int[] marks) {
        this.name = name;
        this.roll = roll;
        this.marks = marks;
        calculateResult();
    }

    private void calculateResult() {
        total = marks[0] + marks[1] + marks[2];
        percentage = total / 3.0;

        if (percentage >= 90) grade = "A";
        else if (percentage >= 75) grade = "B";
        else if (percentage >= 60) grade = "C";
        else if (percentage >= 40) grade = "D";
        else grade = "F";
    }

    public void display() {
        System.out.println("Roll No: " + roll);
        System.out.println("Name: " + name);
        System.out.println("Marks: " + Arrays.toString(marks));
        System.out.println("Total: " + total);
        System.out.printf("Percentage: %.2f%%\n", percentage);
        System.out.println("Grade: " + grade);
        System.out.println("-------------------------");
    }

    public String getName() {
        return name;
    }

    public int getRoll() {
        return roll;
    }

    public int[] getMarks() {
        return marks;
    }
}

// ResultSystem class
class ResultSystem {
    private List<Student> students = new ArrayList<>();
    private Scanner sc = new Scanner(System.in);

    public ResultSystem() {
        loadFromFile(); // Load existing students from file on startup
    }

    public void menu() {
        int choice;
        do {
            System.out.println("\n--- Student Result Management ---");
            System.out.println("1. Add Student");
            System.out.println("2. Display All Students");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1: addStudent(); break;
                case 2: displayStudents(); break;
                case 3: System.out.println("Exiting..."); break;
                default: System.out.println("Invalid choice!");
            }
        } while (choice != 3);
    }

    private void addStudent() {
        System.out.print("Enter student name: ");
        sc.nextLine(); // consume newline
        String name = sc.nextLine();
        System.out.print("Enter roll number: ");
        int roll = sc.nextInt();
        int[] marks = new int[3];
        System.out.println("Enter marks for 3 subjects:");
        for (int i = 0; i < 3; i++) {
            System.out.print("Subject " + (i + 1) + ": ");
            marks[i] = sc.nextInt();
        }
        Student s = new Student(name, roll, marks);
        students.add(s);
        System.out.println("Student added successfully!");
        saveToFile(); // Save after adding
    }

    private void displayStudents() {
        if (students.isEmpty()) {
            System.out.println("No student records available.");
            return;
        }
        for (Student s : students) {
            s.display();
        }
    }

    private void saveToFile() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("students.txt"))) {
            for (Student s : students) {
                int[] m = s.getMarks();
                pw.println(s.getName() + "," + s.getRoll() + "," + m[0] + "," + m[1] + "," + m[2]);
            }
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    private void loadFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader("students.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String name = parts[0];
                int roll = Integer.parseInt(parts[1]);
                int[] marks = new int[3];
                marks[0] = Integer.parseInt(parts[2]);
                marks[1] = Integer.parseInt(parts[3]);
                marks[2] = Integer.parseInt(parts[4]);
                students.add(new Student(name, roll, marks));
            }
        } catch (IOException e) {
            System.out.println("No previous data found or error reading file.");
        }
    }
}

// Main class
public class Main {
    public static void main(String[] args) {
        ResultSystem rs = new ResultSystem();
        rs.menu();
    }
}
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


class Course {
    private String courseCode;
    private String title;
    private String description;
    private int capacity;
    private String schedule;
    private int enrolledStudents;

    public Course(String courseCode, String title, String description, int capacity, String schedule) {
        this.courseCode = courseCode;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.schedule = schedule;
        this.enrolledStudents = 0;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getSchedule() {
        return schedule;
    }

    public int getEnrolledStudents() {
        return enrolledStudents;
    }

    public boolean hasAvailableSlot() {
        return enrolledStudents < capacity;
    }

    public void enrollStudent() {
        if (hasAvailableSlot()) {
            enrolledStudents++;
        } else {
            System.out.println("Course is full. Cannot enroll.");
        }
    }

    public void dropStudent() {
        if (enrolledStudents > 0) {
            enrolledStudents--;
        }
    }

    @Override
    public String toString() {
        return String.format("%s - %s\nDescription: %s\nCapacity: %d\nSchedule: %s\nAvailable Slots: %d\n",
                courseCode, title, description, capacity, schedule, capacity - enrolledStudents);
    }
}


class Student {
    private String studentID;
    private String name;
    private List<Course> registeredCourses;

    public Student(String studentID, String name) {
        this.studentID = studentID;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }

    public String getStudentID() {
        return studentID;
    }

    public String getName() {
        return name;
    }

    public List<Course> getRegisteredCourses() {
        return registeredCourses;
    }

    public void registerCourse(Course course) {
        if (course.hasAvailableSlot()) {
            registeredCourses.add(course);
            course.enrollStudent();
            System.out.println("Successfully registered for " + course.getTitle());
        } else {
            System.out.println("Cannot register. The course is full.");
        }
    }

    public void dropCourse(Course course) {
        if (registeredCourses.remove(course)) {
            course.dropStudent();
            System.out.println("Successfully dropped " + course.getTitle());
        } else {
            System.out.println("You are not registered in this course.");
        }
    }
}


public class CourseManagementSystem {
    private Map<String, Course> courseCatalog;
    private Map<String, Student> studentRegistry;
    private Scanner scanner;

    public CourseManagementSystem() {
        courseCatalog = new HashMap<>();
        studentRegistry = new HashMap<>();
        scanner = new Scanner(System.in);
    }

    public void addCourse(Course course) {
        courseCatalog.put(course.getCourseCode(), course);
    }

    public void addStudent(Student student) {
        studentRegistry.put(student.getStudentID(), student);
    }

    public void listCourses() {
        System.out.println("Available Courses:");
        for (Course course : courseCatalog.values()) {
            System.out.println(course);
        }
    }

    public void registerStudentForCourse() {
        System.out.print("Enter Student ID: ");
        String studentID = scanner.nextLine();
        Student student = studentRegistry.get(studentID);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.print("Enter Course Code: ");
        String courseCode = scanner.nextLine();
        Course course = courseCatalog.get(courseCode);

        if (course == null) {
            System.out.println("Course not found.");
            return;
        }

        student.registerCourse(course);
    }

    public void dropStudentFromCourse() {
        System.out.print("Enter Student ID: ");
        String studentID = scanner.nextLine();
        Student student = studentRegistry.get(studentID);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.print("Enter Course Code: ");
        String courseCode = scanner.nextLine();
        Course course = courseCatalog.get(courseCode);

        if (course == null) {
            System.out.println("Course not found.");
            return;
        }

        student.dropCourse(course);
    }

    public static void main(String[] args) {
        CourseManagementSystem cms = new CourseManagementSystem();

        // Adding some courses to the system
        cms.addCourse(new Course("CS101", "Introduction to Computer Science", "Basic concepts of computer science.", 30, "Mon/Wed/Fri 10:00-11:00"));
        cms.addCourse(new Course("MATH201", "Calculus I", "Differential and integral calculus.", 40, "Tue/Thu 09:00-10:30"));
        cms.addCourse(new Course("PHYS101", "Physics I", "Introduction to mechanics.", 35, "Mon/Wed 14:00-15:30"));

        
        cms.addStudent(new Student("S123", "Alice Smith"));
        cms.addStudent(new Student("S124", "Bob Johnson"));

        
        while (true) {
            System.out.println("\n1. List Courses");
            System.out.println("2. Register for Course");
            System.out.println("3. Drop Course");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = Integer.parseInt(cms.scanner.nextLine());

            switch (choice) {
                case 1:
                    cms.listCourses();
                    break;
                case 2:
                    cms.registerStudentForCourse();
                    break;
                case 3:
                    cms.dropStudentFromCourse();
                    break;
                case 4:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
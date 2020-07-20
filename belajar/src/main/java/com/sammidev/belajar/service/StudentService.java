package com.sammidev.belajar.service;

import com.sammidev.belajar.model.Course;
import com.sammidev.belajar.model.Student;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class StudentService {

    static List<Student> students = new ArrayList<>();
    static {
        //Initialize Data
        Course course1 = new Course("Course1", "Spring", "10Steps", Arrays.asList("Learn Maven", "Import Project", "First Example", "Second Example"));
        Course course2 = new Course("Course2", "Spring MVC", "10 Examples", Arrays.asList("Learn Maven", "Import Project", "First Example", "Second Example"));
        Course course3 = new Course("Course3", "Spring Boot", "6K Students", Arrays.asList("Learn Maven", "Learn Spring", "Learn Spring MVC", "First Example", "Second Example"));
        Course course4 = new Course("Course4", "Maven", "Most popular maven course on internet!", Arrays.asList("Pom.xml", "Build Life Cycle", "Parent POM", "Importing into Eclipse"));
        Course course5 = new Course("Course5", "u_a_k_a_ha", "nge-guy", Arrays.asList("unknown", "unknown", "unknown", "unknown"));


        Student sam = new Student("Student1", "Sammi Aldhiyanto", "Hiker, Programmer and Architect", new ArrayList<>(Arrays.asList(course1, course2, course3, course4)));
        Student Ayatullah = new Student("Ayatullah_Ramadhan", "Ayatullah Ramadhan", "unknown, Programmer and Architect", new ArrayList<>(Arrays.asList(course4, course5)));
        Student adis = new Student("Student2", "Adisti Listianova", "Hiker, Programmer and Architect", new ArrayList<>(Arrays.asList(course1, course2, course3, course4)));

        students.add(sam);
    }

    public List<Student> retrieveAllStudent(){
        return students;
    }

    public Student retrieveStudent(String studentId) {
        for (Student student : students) {
            student.getId().equals(studentId);
            return student;
        }
        return  null;
    }

    public List<Course> retrieveCourses(String studentId){
        Student student = retrieveStudent(studentId);
        if (student == null) {
            return null;
        }
        return student.getCourses();
    }

    public Course retrieveCourse(String studentId, String courseId) {
        Student student = retrieveStudent(studentId);

        if (student == null) {
            return null;
        }

        for (Course course : student.getCourses()) {
            if (course.getId().equals(courseId)) {
                return course;
            }
        }

        return null;
    }

    private SecureRandom random = new SecureRandom();

    public Course addCourse(String studentId, Course course) {
        Student student = retrieveStudent(studentId);

        if (student == null) {
            return null;
        }

        String randomId = new BigInteger(130, random).toString(32);
        course.setId(randomId);

        student.getCourses().add(course);

        return course;
    }
}

package com.sammidev.belajar.controller;

import com.sammidev.belajar.model.Course;
import com.sammidev.belajar.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class StudentController {

//    @Autowired private StudentService studentService : We are using Spring Autowiring to wire the student service into the StudentController.
//    @GetMapping("/students/{studentId}/courses"): Exposing a Get Service with studentId as a path variable
//    @GetMapping("/students/{studentId}/courses/{courseId}"): Exposing a Get Service for retrieving specific course of a student.
//    @PathVariable String studentId: Value of studentId from the uri will be mapped to this parameter.

    @Autowired
    private StudentService studentService;

    @GetMapping("student/{studentId}/courses")
    public List<Course> retrieveCoursesForStudent(@PathVariable String studentId) {
        return studentService.retrieveCourses(studentId);
    }

    @GetMapping("/students/{studentId}/courses/{courseId}")
    public Course retrieveDetailsForCourse(@PathVariable String studentId,
                                           @PathVariable String courseId) {
        return studentService.retrieveCourse(studentId, courseId);
    }

    @PostMapping("/students/{studentId}/courses")
    public ResponseEntity<Void> registerStudentForCourse(
            @PathVariable String studentId, @RequestBody Course newCourse) {

        Course course = studentService.addCourse(studentId, newCourse);

        if (course == null)
            return ResponseEntity.noContent().build();

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path(
                "/{id}").buildAndExpand(course.getId()).toUri();

        return ResponseEntity.created(location).build();
    }
}

package com.sammidev.belajar.controller;
import com.sammidev.belajar.BelajarApplication;

import static org.junit.Assert.assertTrue;

import com.sammidev.belajar.model.Course;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

//  @SpringBootTest(classes = StudentServicesApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) : Launch the entire Spring Boot Application on a Random Port
//  @LocalServerPort private int port;: Autowire the random port into the variable so that we can use it create the url.
//  createURLWithPort(String uri) : Utility method to create the url given an uri. It appends the port.
//  HttpEntity<String> entity = new HttpEntity<String>(null, headers);: We use entity so that we have the flexibility of adding in request headers in future.
//  restTemplate.exchange(createURLWithPort("/students/Student1/courses/Course1"),HttpMethod.GET, entity, String.class): Fire a GET request to the specify uri and get the response as a String.
//  JSONAssert.assertEquals(expected, response.getBody(), false) : Assert that the response contains expected fields.

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BelajarApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerIT {

    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();
    HttpHeaders headers = new HttpHeaders();

    @Test
    public void testRetrieveStudentCourse() throws JSONException {

        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/students/Ayatullah_Ramadhan/courses/Course5"),
                HttpMethod.GET, entity, String.class);

        String expected = "{id:Course5,name:u_a_k_a_ha,description:nge-guy}";

        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void addCourse() {

        Course course = new Course("Course1", "Spring", "10Steps",
                Arrays.asList("Learn Maven", "Import Project", "First Example",
                        "Second Example"));

        HttpEntity<Course> entity = new HttpEntity<Course>(course, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/students/Student1/courses"),
                HttpMethod.POST, entity, String.class);

        String actual = response.getHeaders().get(HttpHeaders.LOCATION).get(0);

        assertTrue(actual.contains("/students/Student1/courses/"));
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}

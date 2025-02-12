package com.piyal;

import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.Map;

import jakarta.inject.Inject;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@MicronautTest
class ApplicationTest {

    @Inject
    EmbeddedApplication<?> application;

    @Test
    void testItWorks() {
        Assertions.assertTrue(application.isRunning());
    }

    @Test
    void testCreateBook(RequestSpecification spec) {
        System.out.println("\n--- Creating a new book ---");
        given()
                .spec(spec)
                .contentType("application/json")
                .body(Map.of(
                        "name", "C++ Guide",
                        "author", "Doe John",
                        "price", 229.99,
                        "totalPage", 400
                ))
                .when()
                .post("/api/v1/book/save")
                .then()
                .log().body()  // Print API response
                .statusCode(200)
                .body("name", equalTo("C++ Guide"));
    }

    @Test
    void testGetAllBooks(RequestSpecification spec) {
        System.out.println("\n--- Fetching all books ---");
        given()
                .spec(spec)
                .when()
                .get("/api/v1/book/all")
                .then()
                .log().body()  // Print API response
                .statusCode(200);
    }
}

package org.luongnm93.quarkus.microservices.book;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;

@QuarkusTest
class BookResourceTest {
    @Test
    void shouldCreateABook() {
        given()
            .formParam("title", "Understanding Quarkus")
            .formParam("author", "Luongnm")
            .formParam("year", "2020")
            .formParam("genre", "IT")
        .when()
            .post("/api/books")
        .then()
            .statusCode(201)
            .body("title", is("Understanding Quarkus"))
            .body("author", is("Luongnm"))
            .body("year_of_publication", is(2020))
            .body("genre", is("IT"))
            .body("isbn_13", startsWith("13-"));
    }

}
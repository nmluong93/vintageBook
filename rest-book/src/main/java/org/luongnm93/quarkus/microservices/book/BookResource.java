package org.luongnm93.quarkus.microservices.book;

import jakarta.inject.Inject;
import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.Instant;

@Tag(name = "Book REST endpoint")
@Path("/api/books")
public class BookResource {

    @RestClient
    @Inject
    NumberProxy numberProxy;

    @Inject
    Logger logger;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Operation(summary = "Creates a Book", description = "Creates a Book with an ISBN number")
    @Fallback(
            fallbackMethod = "fallbackOnCreatingABook"
    )
    @Retry(
            maxRetries = 3, delay = 3000
    )
    public Response createABook(@FormParam("title") String title, @FormParam("author") String author, @FormParam("year") int yearOfPublication, @FormParam("genre") String genre) {
            Book book = new Book(title, author, yearOfPublication, genre);
            book.isbn13 = numberProxy.generateIsbnNumbers().isbn13;
            logger.info("Book created with ISBN number: " + book.isbn13);
            return Response.status(201).entity(book).build();
    }

    public Response fallbackOnCreatingABook(String title, String author, int yearOfPublication, String genre) throws FileNotFoundException {
        Book book = new Book(title, author, yearOfPublication, genre);
        book.isbn13 = "Will be set later";
        logger.warn("Book created with ISBN number: " + book.isbn13);
        saveBookOnDisk(book);
        return Response.status(206).entity(book).build();
    }

    private void saveBookOnDisk(Book book) throws FileNotFoundException {
        String booJson = JsonbBuilder.create().toJson(book);
        try(PrintWriter out = new PrintWriter("book-" + Instant.now().toEpochMilli() + ".json")) {
            out.println(booJson);
        }
    }
}

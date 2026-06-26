package org.luongnm93.quarkus.microservices.book;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.json.bind.annotation.JsonbProperty;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.Instant;

@Schema(description = "This is a book")
public class Book {
    @Schema(required = true)
    public String title;
    public String author;

    @JsonbProperty("year_of_publication")
    public int yearOfPublication;
    public String genre;

    @Schema(required = true)
    @JsonbProperty("isbn_13")
    public String isbn13;

    @Schema(implementation = String.class, format = "date")
    @JsonbDateFormat("yyyy/MM/dd")
    @JsonbProperty("creation_date")
    public Instant creationDate;

    public Book(String title, String author, int yearOfPublication, String genre) {
        this.title = title;
        this.author = author;
        this.yearOfPublication = yearOfPublication;
        this.genre = genre;
        this.creationDate = Instant.now();
    }

    @Override
    public String toString() {
        return "Book{title='" + title + "', author='" + author + "', yearOfPublication=" + yearOfPublication + ", genre='" + genre + "', isbn13='" + isbn13 + "', creationDate=" + creationDate + "}";
    }
}

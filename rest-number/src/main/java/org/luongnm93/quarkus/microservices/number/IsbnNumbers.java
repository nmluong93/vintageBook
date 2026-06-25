package org.luongnm93.quarkus.microservices.number;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbTransient;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.Instant;

@Schema(description = "Several ISBN numbers for books")
public class IsbnNumbers {

    @JsonbProperty("isbn_13")
    @Schema(required = true)
    public String isbn13;

    @JsonbProperty("isbn_10")
    @Schema(required = true)
    public String isbn10;

    @JsonbTransient
    public Instant generationDate;

    @Override
    public String toString() {
        return "IsbnNumbers{" +
                "generationDate=" + generationDate +
                ", isbn13='" + isbn13 + '\'' +
                ", isbn10='" + isbn10 + '\'' +
                '}';
    }
}

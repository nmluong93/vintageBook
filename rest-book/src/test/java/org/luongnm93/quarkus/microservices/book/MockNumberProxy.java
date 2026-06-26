package org.luongnm93.quarkus.microservices.book;

import io.quarkus.test.Mock;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Mock
@RestClient
public class MockNumberProxy implements NumberProxy {

    @Override
    public IsbnThirteen generateIsbnNumbers() {
        var isbnNumbers = new IsbnThirteen();
        isbnNumbers.isbn13 = "13-Mock";
        return isbnNumbers;
    }
}

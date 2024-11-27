package org.example.utazasgyakorlat.Model;

public class SzallodaNotFoundException extends RuntimeException {
    public SzallodaNotFoundException(String id) {
        super("Szalloda not found with id: " + id);
    }
}

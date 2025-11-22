package org.example.flowerapp.Exceptions;

import java.util.Date;

public class DuplicateEntryException extends RuntimeException {
    public DuplicateEntryException(String name, String species, Date date){
        super(String.format("The flower named '%s', species '%s', and planted on %tB %te, %tY already exists in the system.", name, species, date, date, date));
    }


    public DuplicateEntryException(String message) {
        super(message);
    }
}

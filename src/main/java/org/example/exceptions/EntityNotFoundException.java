package org.example.exceptions;

public class EntityNotFoundException extends Exception {
    private final Long id;
    public EntityNotFoundException(Long id){
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}

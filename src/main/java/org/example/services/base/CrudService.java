package org.example.services.base;

import org.example.exceptions.EntityNotFoundException;

import java.util.List;

public interface CrudService<T> {
    T getById(Long id) throws EntityNotFoundException;

    void deleteById(Long id) throws EntityNotFoundException;

    List<T> getAll();

    void create(T body);

    void update(Long id, T body) throws EntityNotFoundException;
}

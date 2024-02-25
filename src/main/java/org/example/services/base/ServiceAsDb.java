package org.example.services.base;

import java.io.FileNotFoundException;

public interface ServiceAsDb {
    String getFileName();
    void init() throws FileNotFoundException;
    void save();
}

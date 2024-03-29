package ru.javawebinar.basejava.exception;

public class ExistStorageException extends StorageException {
    public ExistStorageException(String uuid) {
        super("Error: \"uuid\" (" + uuid + ") already exists!", uuid);
    }
}

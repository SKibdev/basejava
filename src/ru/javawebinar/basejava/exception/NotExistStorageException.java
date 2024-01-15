package ru.javawebinar.basejava.exception;

public class NotExistStorageException extends StorageException {
    public NotExistStorageException(String uuid) {
        super("Error: The entered \"uuid\" (" + uuid + ") does not exist !!!", uuid);
    }
}

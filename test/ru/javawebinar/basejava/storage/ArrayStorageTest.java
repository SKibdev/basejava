package ru.javawebinar.basejava.storage;

import static org.junit.jupiter.api.Assertions.*;

class ArrayStorageTest extends AbstractArrayStorageTest{
    public ArrayStorageTest(Storage storage) {
        super(new ArrayStorage());
    }
}
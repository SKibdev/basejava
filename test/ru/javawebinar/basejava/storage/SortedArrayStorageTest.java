package ru.javawebinar.basejava.storage;

import static org.junit.jupiter.api.Assertions.*;

class SortedArrayStorageTest extends AbstractArrayStorageTest {
    public SortedArrayStorageTest(Storage storage) {
        super(new SortedArrayStorage());
    }
}
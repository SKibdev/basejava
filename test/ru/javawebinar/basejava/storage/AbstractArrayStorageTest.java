package ru.javawebinar.basejava.storage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import static org.junit.jupiter.api.Assertions.*;
import static ru.javawebinar.basejava.storage.AbstractArrayStorage.STORAGE_LIMIT;

class AbstractArrayStorageTest {
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final int sizeStorageTest = 3;
    private static final Resume resumeUuid1 = new Resume(UUID_1);
    private static final Resume resumeUuid2 = new Resume(UUID_2);
    private static final Resume resumeUuid3 = new Resume(UUID_3);
    private final Storage storage;

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    public void setUp() {
        storage.clear();
        storage.save(resumeUuid1);
        storage.save(resumeUuid2);
        storage.save(resumeUuid3);
    }

    @Test
    void getAll() {
        Resume[] expectedResume = new Resume[sizeStorageTest];
        expectedResume[0] = new Resume(UUID_1);
        expectedResume[1] = new Resume(UUID_2);
        expectedResume[2] = new Resume(UUID_3);

        assertArrayEquals(expectedResume, storage.getAll());
    }

    @Test
    void size() {
        assertSize(sizeStorageTest);
    }

    @Test
    void save() {
        Resume resumeUuid4 = new Resume("uuid4");
        storage.save(resumeUuid4);
        assertEquals(4, storage.size());
        assertEquals(resumeUuid4, storage.get("uuid4"));
    }

    @Test
    void saveExist() {
        assertThrows(ExistStorageException.class, () -> storage.save(resumeUuid2));
    }

    @Test
    void saveOverFlow() {
        try {
            for (int i = sizeStorageTest; i < STORAGE_LIMIT; i++) {
                storage.save(new Resume());
            }
        } catch (Exception e) {
            fail("Переполнение произошло раньше времени!");
        }
        assertThrows(StorageException.class, () -> storage.save(new Resume()));
    }

    @Test
    void update() {
        storage.update(resumeUuid2);
        assertEquals(resumeUuid2, storage.get(UUID_2));
    }

    @Test
    void updateNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.update(new Resume("dummy")));
    }

    @Test
    void delete() {
        checkDeleteArrayElement(true);
        checkDeleteArrayElement(false);
        checkArrayElementsNull();
    }

    private void checkDeleteArrayElement(boolean isRestorationArrayElements) {
        int size = sizeStorageTest;
        for (int i = 1; i <= size; i++) {
            String uuid = "uuid" + i;
            storage.delete(uuid);
            assertThrows(NotExistStorageException.class, () -> storage.get(uuid));
            if (isRestorationArrayElements) {
                storage.save(new Resume(uuid));
                assertEquals(size, storage.size(), "size не соответствует размеру массива!");
            } else {
                assertEquals(size - i, storage.size(), "size не соответствует размеру массива!");
            }
        }
    }

    @Test
    void deleteNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.delete("dummy"));
    }

    @Test
    void get() {
        assertEquals(resumeUuid2, storage.get(UUID_2));
    }

    @Test
    void getNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.get("dummy"));
    }

    @Test
    void clear() {
        storage.clear();
        checkArrayElementsNull();
    }

    private void checkArrayElementsNull() {
        for (Resume element : storage.getAll()) {
            assertNull(element, "Array element should be null");
        }
        assertEquals(0, storage.size());
    }

    private void assertSize(int size) {
        assertEquals(size, storage.size());
    }
}
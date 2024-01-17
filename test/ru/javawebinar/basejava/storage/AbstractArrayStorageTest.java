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
    private final Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private final Resume resumeUuid2 = new Resume(UUID_2);

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    public void setUp() {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    void getAll() {
        Resume[] expectedResume = new Resume[3];
        expectedResume[0] = new Resume(UUID_1);
        expectedResume[1] = new Resume(UUID_2);
        expectedResume[2] = new Resume(UUID_3);

        assertArrayEquals(expectedResume, storage.getAll());
    }

    @Test
    void size() {
        assertEquals(3, storage.size());
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
        assertThrows(StorageException.class, () -> {
            for (int i = 3; i <= STORAGE_LIMIT; i++) {
                storage.save(new Resume());
            }
        });
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
        int size = storage.size();

        for (int i = 1; i <= size; i++) {
            String uuid = "uuid" + i;
            storage.delete(uuid);
            assertThrows(NotExistStorageException.class, () -> storage.get(uuid));
            storage.save(new Resume(uuid));
        }

        for (int i = 1; i <= size; i++) {
            String uuid = "uuid" + i;
            storage.delete(uuid);
            assertThrows(NotExistStorageException.class, () -> storage.get(uuid));
        }

        for (Resume element : storage.getAll()) {
            assertNull(element, "Array element should be null");
        }
        assertEquals(0, storage.size());
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
        for (Resume element : storage.getAll()) {
            assertNull(element, "Array element should be null");
        }
        assertEquals(0, storage.size());
    }
}
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

    private static final int sizeStorageTest = 3;
    public static final String UUID_1 = "uuid1";
    private static final Resume resumeUuid1 = new Resume(UUID_1);
    public static final String UUID_2 = "uuid2";
    private static final Resume resumeUuid2 = new Resume(UUID_2);
    public static final String UUID_3 = "uuid3";
    private static final Resume resumeUuid3 = new Resume(UUID_3);
    public static final String UUID_4 = "uuid4";
    private static final Resume resumeUuid4 = new Resume(UUID_4);

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
        assertGetAll(new Resume[]{resumeUuid1, resumeUuid2, resumeUuid3});
    }

    @Test
    void size() {
        assertSize(sizeStorageTest);
    }

    @Test
    void save() {
        storage.save(resumeUuid4);
        assertSize(sizeStorageTest + 1);
        assertGet(resumeUuid4);
    }

    @Test
    void saveExist() {
        assertThrows(ExistStorageException.class, () -> storage.save(resumeUuid2));
    }

    @Test
    void saveOverFlow() {
        try {
            storage.clear();
            for (int i = 0; i < STORAGE_LIMIT; i++) {
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
        storage.delete(UUID_2);
        assertSize(sizeStorageTest - 1);
        assertThrows(NotExistStorageException.class, () -> storage.get(UUID_2));
        assertGetAll(new Resume[]{resumeUuid1, resumeUuid3});
    }

//    private void checkDeleteArrayElement(boolean isRestorationArrayElements) {
//        int size = sizeStorageTest;
//        for (int i = 1; i <= size; i++) {
//            String uuid = "uuid" + i;
//            storage.delete(uuid);
//            assertThrows(NotExistStorageException.class, () -> storage.get(uuid));
//            if (isRestorationArrayElements) {
//                storage.save(new Resume(uuid));
//                assertEquals(size, storage.size(), "size не соответствует размеру массива!");
//            } else {
//                assertEquals(size - i, storage.size(), "size не соответствует размеру массива!");
//            }
//        }
//    }

    @Test
    void deleteNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.delete("dummy"));
    }

    @Test
    void get() {
        assertGet(resumeUuid1);
        assertGet(resumeUuid2);
        assertGet(resumeUuid3);
    }

    @Test
    void getNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.get("dummy"));
    }

    @Test
    void clear() {
        storage.clear();
        assertSize(0);
        assertArrayEquals(new Resume[0], storage.getAll());
    }

    private void assertSize(int size) {
        assertEquals(size, storage.size());
    }

    private void assertGetAll(Resume[] expected) {
        assertArrayEquals(expected, storage.getAll());
    }

    private void assertGet(Resume resume) {
        assertEquals(resume, storage.get(resume.getUuid()));
    }
}
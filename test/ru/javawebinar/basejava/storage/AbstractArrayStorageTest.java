package ru.javawebinar.basejava.storage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static ru.javawebinar.basejava.storage.AbstractArrayStorage.STORAGE_LIMIT;

public abstract class AbstractArrayStorageTest {

    private static final int SIZE_TEST = 3;
    public static final String UUID_1 = "uuid1";
    private static final Resume RESUME_UUID_1 = new Resume(UUID_1);
    public static final String UUID_2 = "uuid2";
    private static final Resume RESUME_UUID_2 = new Resume(UUID_2);
    public static final String UUID_3 = "uuid3";
    private static final Resume RESUME_UUID_3 = new Resume(UUID_3);
    public static final String UUID_4 = "uuid4";
    private static final Resume RESUME_UUID_4 = new Resume(UUID_4);
    public static final String UUID_NOT_EXIST = "dummy";

    private final Storage storage;

    protected AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    public void setUp() {
        storage.clear();
        storage.save(RESUME_UUID_1);
        storage.save(RESUME_UUID_2);
        storage.save(RESUME_UUID_3);
    }

    @Test
    public void getAll() {
        assertGetAll(new Resume[]{RESUME_UUID_1, RESUME_UUID_2, RESUME_UUID_3});
    }

    @Test
    public void size() {
        assertSize(SIZE_TEST);
    }

    @Test
    public void save() {
        storage.save(RESUME_UUID_4);
        assertSize(SIZE_TEST + 1);
        assertGet(RESUME_UUID_4);
    }

    @Test
    public void saveExist() {
        // Для проверки условия if (index >= 0) необходимо сохранять RESUME с индексом 0
        assertThrows(ExistStorageException.class, () -> storage.save(RESUME_UUID_1));
    }

    @Test
    public void saveOverFlow() {
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
    public void update() {
        storage.update(RESUME_UUID_2);
        assertEquals(RESUME_UUID_2, storage.get(UUID_2));
    }

    @Test
    public void updateNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.update(new Resume(UUID_NOT_EXIST)));
    }

    @Test
    public void delete() {
        storage.save(RESUME_UUID_4);
        assertDelete(UUID_2, new Resume[]{RESUME_UUID_1, RESUME_UUID_3, RESUME_UUID_4});
        assertDelete(UUID_4, new Resume[]{RESUME_UUID_1, RESUME_UUID_3});
        assertDelete(UUID_1, new Resume[]{RESUME_UUID_3});
        assertDelete(UUID_3, new Resume[0]);
    }

    private void assertDelete(String uuid, Resume[] remaining) {
        int size = storage.size();
        storage.delete(uuid);
        assertSize(size - 1);
        assertThrows(NotExistStorageException.class, () -> storage.get(uuid));
        Resume[] storageSorted = storage.getAll();
        Arrays.sort(storageSorted);
        assertArrayEquals(remaining, storageSorted);
    }

    @Test
    public void deleteNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.delete(UUID_NOT_EXIST));
    }

    @Test
    public void get() {
        assertGet(RESUME_UUID_1);
        assertGet(RESUME_UUID_2);
        assertGet(RESUME_UUID_3);
    }

    @Test
    public void getNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.get(UUID_NOT_EXIST));
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);
        assertArrayEquals(new Resume[0], storage.getAll());
    }

    private void assertSize(int size) {
        assertEquals(size, storage.size());
    }

    private void assertGetAll(Resume[] expected) {
        assertSize(SIZE_TEST);
        assertArrayEquals(expected, storage.getAll());
    }

    private void assertGet(Resume resume) {
        assertEquals(resume, storage.get(resume.getUuid()));
    }
}
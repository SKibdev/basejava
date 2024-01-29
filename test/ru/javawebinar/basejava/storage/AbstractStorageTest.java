package ru.javawebinar.basejava.storage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class AbstractStorageTest {
    protected static final int SIZE_TEST = 3;
    protected static final String UUID_1 = "uuid1";
    protected static final Resume RESUME_UUID_1 = new Resume(UUID_1);
    protected static final String UUID_2 = "uuid2";
    protected static final Resume RESUME_UUID_2 = new Resume(UUID_2);
    protected static final String UUID_3 = "uuid3";
    protected static final Resume RESUME_UUID_3 = new Resume(UUID_3);
    protected static final String UUID_4 = "uuid4";
    protected static final Resume RESUME_UUID_4 = new Resume(UUID_4);
    protected static final String UUID_NOT_EXIST = "dummy";

    protected final Storage storage;

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    public void setUp() {
        storage.doClear();
        storage.doSave(RESUME_UUID_1);
        storage.doSave(RESUME_UUID_2);
        storage.doSave(RESUME_UUID_3);
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
    public void doSave() {
        storage.doSave(RESUME_UUID_4);
        assertSize(SIZE_TEST + 1);
        assertGet(RESUME_UUID_4);
    }

    @Test
    public void doSaveExist() {
        // Для проверки условия if (index >= 0) необходимо сохранять RESUME с индексом 0
        assertThrows(ExistStorageException.class, () -> storage.doSave(RESUME_UUID_1));
    }


    @Test
    public void doUpdate() {
        storage.doUpdate(RESUME_UUID_2);
        assertEquals(RESUME_UUID_2, storage.doGet(UUID_2));
    }

    @Test
    public void doUpdateNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.doUpdate(new Resume(UUID_NOT_EXIST)));
    }

    @Test
    public void delete() {
        storage.doSave(RESUME_UUID_4);
        assertDelete(UUID_2, new Resume[]{RESUME_UUID_1, RESUME_UUID_3, RESUME_UUID_4});
        assertDelete(UUID_4, new Resume[]{RESUME_UUID_1, RESUME_UUID_3});
        assertDelete(UUID_1, new Resume[]{RESUME_UUID_3});
        assertDelete(UUID_3, new Resume[0]);
    }

    private void assertDelete(String uuid, Resume[] remaining) {
        int size = storage.doGetSize();
        storage.doDelete(uuid);
        assertSize(size - 1);
        assertThrows(NotExistStorageException.class, () -> storage.doGet(uuid));
        Resume[] storageSorted = storage.doGetAll();
        Arrays.sort(storageSorted);
        assertArrayEquals(remaining, storageSorted);
    }

    @Test
    public void deleteNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.doDelete(UUID_NOT_EXIST));
    }

    @Test
    public void get() {
        assertGet(RESUME_UUID_1);
        assertGet(RESUME_UUID_2);
        assertGet(RESUME_UUID_3);
    }

    @Test
    public void getNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.doGet(UUID_NOT_EXIST));
    }

    @Test
    public void doClear() {
        storage.doClear();
        assertSize(0);
        assertArrayEquals(new Resume[0], storage.doGetAll());
    }

    private void assertSize(int size) {
        assertEquals(size, storage.doGetSize());
    }

    private void assertGetAll(Resume[] expected) {
        assertSize(SIZE_TEST);
        assertArrayEquals(expected, storage.doGetAll());
    }

    private void assertGet(Resume resume) {
        assertEquals(resume, storage.doGet(resume.getUuid()));
    }
}

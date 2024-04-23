package ru.javawebinar.basejava.storage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.ResumeTestData;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class AbstractStorageTest {
    protected static final File STORAGE_DIR = Config.get().getStorageDir();
    protected static final String STORAGE_PATH = "S:\\java\\basejava\\storage";
    protected static final int SIZE_TEST = 3;

    protected static final String NAME_1 = "fullName1";
    protected static final Resume RESUME_UUID_1 = ResumeTestData.createResume(NAME_1);
    protected static final String UUID_1 = RESUME_UUID_1.getUuid();

    protected static final String NAME_2 = "fullName2";
    protected static final Resume RESUME_UUID_2 = ResumeTestData.createResume(NAME_2);
    protected static final String UUID_2 = RESUME_UUID_2.getUuid();

    protected static final String NAME_3 = "fullName3";
    protected static final Resume RESUME_UUID_3 = ResumeTestData.createResume(NAME_3);
    protected static final String UUID_3 = RESUME_UUID_3.getUuid();

    protected static final String NAME_4 = "fullName4";
    protected static final Resume RESUME_UUID_4 = ResumeTestData.createResume(NAME_4);
    protected static final String UUID_4 = RESUME_UUID_4.getUuid();

    protected static final String UUID_NOT_EXIST = "dummy";

    protected final Storage storage;

    public AbstractStorageTest(Storage storage) {
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
    public void getAllSorted() {
        assertGetAll(List.of(RESUME_UUID_1, RESUME_UUID_2, RESUME_UUID_3));
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
    public void update() {
        Resume newResumeUuid2 = new Resume(UUID_2, "dummy");
        storage.update(newResumeUuid2);
        assertEquals(newResumeUuid2, storage.get(UUID_2));
    }

    @Test
    public void updateNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.update(new Resume(UUID_NOT_EXIST, "Name1")));
    }

    @Test
    public void delete() {
        storage.save(RESUME_UUID_4);
        assertDelete(UUID_2, List.of(RESUME_UUID_1, RESUME_UUID_3, RESUME_UUID_4));
        assertDelete(UUID_4, List.of(RESUME_UUID_1, RESUME_UUID_3));
        assertDelete(UUID_1, List.of(RESUME_UUID_3));
        assertDelete(UUID_3, new ArrayList<>());
    }

    private void assertDelete(String uuid, List<Resume> remaining) {
        int size = storage.size();
        storage.delete(uuid);
        assertSize(size - 1);
        assertThrows(NotExistStorageException.class, () -> storage.get(uuid));
        assertIterableEquals(remaining, storage.getAllSorted());
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
        assertIterableEquals(new ArrayList<>(), storage.getAllSorted());
    }

    private void assertSize(int size) {
        assertEquals(size, storage.size());
    }

    private void assertGetAll(List<Resume> expected) {
        assertSize(SIZE_TEST);
        assertIterableEquals(expected, storage.getAllSorted());
    }

    private void assertGet(Resume resume) {
        assertEquals(resume, storage.get(resume.getUuid()));
    }
}

package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    public void save(Resume r) {
        int index = getIndex(r.getUuid());
        if (size == STORAGE_LIMIT) {
            System.out.println("Error: Storage overflow!");
        } else if (index >= 0) {
            System.out.println("Error: \"uuid\" (" + r + ") already exists!");
        } else {
            index = -(index + 1);
            saveResume(r, index);
        }
    }

    public void update(Resume r) {
        int index = check(r.getUuid());
        if (index >= 0) {
            storage[index] = r;
        }
    }

    public void delete(String uuid) {
        int index = check(uuid);
        if (index >= 0) {
            deleteResume(index);
        }
    }

    public Resume get(String uuid) {
        int index = check(uuid);
        return (index >= 0) ? storage[index] : null;
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    protected int check(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.println("Error: The entered \"uuid\" (" + uuid + ") does not exist !!!");
            return -1;
        }
        return index;
    }

    protected abstract int getIndex(String uuid);

    protected abstract void saveResume(Resume r, int index);

    protected abstract void deleteResume(int index);
}

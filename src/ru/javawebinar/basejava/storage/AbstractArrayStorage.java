package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
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
    public final Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    public final void save(Resume r) {
        int index = getIndex(r.getUuid());
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Error: Storage overflow!", r.getUuid());
        } else if (index >= 0) {
            throw new ExistStorageException(r.getUuid());
//            System.out.println("Error: \"uuid\" (" + r + ") already exists!");
        } else {
            insertElement(r, index);
            size++;
        }
    }

    public final void update(Resume r) {
        int index = getExistingIndex(r.getUuid());
        if (index >= 0) {
            storage[index] = r;
        }
    }

    public final void delete(String uuid) {
        int index = getExistingIndex(uuid);
        if (index >= 0) {
            size--;
            fillDeletedElement(index);
            storage[size] = null;
        }
    }

    public final Resume get(String uuid) {
        int index = getExistingIndex(uuid);
        return (index >= 0) ? storage[index] : null;
    }

    public final void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    protected final int getExistingIndex(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
//            System.out.println("Error: The entered \"uuid\" (" + uuid + ") does not exist !!!");
//            return -1;
        }
        return index;
    }

    protected abstract int getIndex(String uuid);

    protected abstract void insertElement(Resume r, int index);

    protected abstract void fillDeletedElement(int index);
}

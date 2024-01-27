package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected void increaseSize(String uuid) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Error: Storage overflow!", uuid);
        }
        size++;
    }

    @Override
    protected void replaceElement(int index, Resume r) {
        storage[index] = r;
    }

    @Override
    protected final void deleteElement(int index) {
        size--;
        fillDeletedElement(index);
        storage[size] = null;
    }

    @Override
    protected Resume getElement(int index) {
        return storage[index];
    }

    protected abstract int getIndex(String uuid);

    protected abstract void insertElement(Resume r, int index);

    protected abstract void fillDeletedElement(int index);
}

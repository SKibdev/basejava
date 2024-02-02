package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

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
    protected void doUpdate(Integer searchKey, Resume r) {
        storage[searchKey] = r;
    }

    @Override
    protected void doSave(Integer searchKey, Resume r) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Error: Storage overflow!", r.getUuid());
        }
        insertElement(r, searchKey);
        size++;
    }

    @Override
    protected Resume doGet(Integer searchKey) {
        return storage[searchKey];
    }

    @Override
    protected void doDelete(Integer searchKey) {
        fillDeletedElement(searchKey);
        storage[--size] = null;
    }

    @Override
    protected boolean isExist(Integer searchKey) {
        return searchKey >= 0;
    }

    @Override
    protected abstract Integer getSearchKey(String uuid);

    protected abstract void insertElement(Resume r, Integer searchKey);

    protected abstract void fillDeletedElement(Integer searchKey);
}

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

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    public Resume[] doGetAll() {
        return Arrays.copyOf(storage, size);
    }

    @Override
    public int doGetSize() {
        return size;
    }

    @Override
    public void doClear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected void insertElement(Integer searchKey, Resume r) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Error: Storage overflow!", r.getUuid());
        }
        size++;
        insert(r, searchKey);
    }

    @Override
    protected boolean isExist(Integer searchKey) {
        return searchKey >= 0;
    }

    @Override
    protected void replaceElement(Integer searchKey, Resume r) {
        storage[searchKey] = r;
    }

    @Override
    protected final void deleteElement(Integer searchKey) {
        size--;
        fillDeletedElement(searchKey);
        storage[size] = null;
    }

    @Override
    protected Resume getElement(Integer searchKey) {
        return storage[searchKey];
    }

    protected abstract Integer getSearchKey(String uuid);

    protected abstract void insert(Resume r, Integer searchKey);

    protected abstract void fillDeletedElement(Integer searchKey);
}

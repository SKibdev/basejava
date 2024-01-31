package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {
    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void insert(Resume r, Integer searchKey) {
        // size уже увеличен в родительском классе
        storage[size - 1] = r;
    }

    @Override
    protected void fillDeletedElement(Integer searchKey) {
        // size уже уменьшин в родительском классе
        storage[searchKey] = storage[size];
    }
}

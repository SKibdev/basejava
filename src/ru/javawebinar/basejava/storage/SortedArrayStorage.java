package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    protected Integer getSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected void insert(Resume r, Integer searchKey) {
        // size уже увеличен в родительском классе
        searchKey = -(searchKey + 1);
        System.arraycopy(storage, searchKey, storage, searchKey + 1, size - 1 - searchKey);
        storage[searchKey] = r;
    }

    @Override
    protected void fillDeletedElement(Integer searchKey) {
        // size уже уменьшин в родительском классе
        if (size != searchKey) {
            System.arraycopy(storage, searchKey + 1, storage, searchKey, size - searchKey);
        }
    }
}

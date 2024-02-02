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
    protected void insertElement(Resume r, Integer searchKey) {
        searchKey = -(searchKey + 1);
        System.arraycopy(storage, searchKey, storage, searchKey + 1, size - searchKey);
        storage[searchKey] = r;
    }

    @Override
    protected void fillDeletedElement(Integer searchKey) {
        if (size - 1 != searchKey) {
            System.arraycopy(storage, searchKey + 1, storage, searchKey, size - 1 - searchKey);
        }
    }
}

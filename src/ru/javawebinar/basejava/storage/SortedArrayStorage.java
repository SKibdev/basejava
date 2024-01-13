package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    protected void saveResume(Resume r, int index) {
        System.arraycopy(storage, index, storage, index + 1, size);
        storage[index] = r;
        size++;
    }

    @Override
    protected void deleteResume(int index) {
        if (size > 1 || index < size - 1) {
            System.arraycopy(storage, index + 1, storage, index, size - index - 1);
        }
        size--;
        storage[size] = null;
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}

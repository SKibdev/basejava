package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void insertElement(Resume r, int index) {
        // size уже увеличен в родительском классе
        index = -(index + 1);
        System.arraycopy(storage, index, storage, index + 1, size - 1 - index );
        storage[index] = r;
    }

    @Override
    protected void fillDeletedElement(int index) {
        // size уже уменьшин в родительском классе
        if (size != index) {
            System.arraycopy(storage, index + 1, storage, index, size - index);
        }
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}

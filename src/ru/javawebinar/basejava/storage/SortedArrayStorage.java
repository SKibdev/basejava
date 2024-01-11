package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage{

    @Override
    public void clear() {

    }

    @Override
    public void update(Resume r) {

    }

    @Override
    public void save(Resume r) {
        int index = getIndex(r.getUuid());
        if (size == STORAGE_LIMIT) {
            System.out.println("Error: Storage overflow!");
        } else if (index == 0) {
            System.out.println("Error: \"uuid\" (" + r + ") already exists!");
        } else {
            index = -(index + 1);
            System.arraycopy(storage, index, storage, index + 1, size);
            storage[index] = r;
            size++;
        }
    }

    @Override
    public void delete(String uuid) {

    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}

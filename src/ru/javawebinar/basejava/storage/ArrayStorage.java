package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    public void update(Resume r) {
        int index = getIndex(r.getUuid());
        if (index == -1) {
            System.out.println("Error: The entered \"uuid\" (" + r.getUuid() + ") does not exist !!!");
        } else {
            storage[index] = r;
        }
    }

    public void save(Resume r) {
        if (size == STORAGE_LIMIT) {
            System.out.println("Error: Storage overflow!");
        } else if (getIndex(r.getUuid()) != -1) {
            System.out.println("Error: \"uuid\" (" + r + ") already exists!");
        } else {
            storage[size++] = r;
        }
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index == -1) {
            System.out.println("Error: The entered \"uuid\" (" + uuid + ") does not exist !!!");
        } else {
            size--;
            storage[index] = storage[size];
            storage[size] = null;
        }
    }

    protected int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}

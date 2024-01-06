package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    public static final int CAPACITY = 10000;
    protected final Resume[] storage = new Resume[CAPACITY];
    private int size;

    public void update(Resume r) {
        int indexResumePresent = getIndex(r.getUuid(), true);
        if (indexResumePresent != -1) {
            storage[indexResumePresent] = r;
        }
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume r) {
        if (size == CAPACITY) {
            System.out.println("Error: Storage is full!");
        } else if (getIndex(r.getUuid(), false) != -1) {
            System.out.println("Error: \"uuid\" (" + r + ") already exists!");
        } else {
            storage[size++] = r;
        }
    }

    public Resume get(String uuid) {
        int indexResumePresent = getIndex(uuid, true);
        if (indexResumePresent != -1) {
            return storage[indexResumePresent];
        }
        return null;
    }

    public void delete(String uuid) {
        int indexResumePresent = getIndex(uuid, true);
        if (indexResumePresent != -1) {
            size--;
            storage[indexResumePresent] = storage[size];
            storage[size] = null;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    private int getIndex(String uuid, boolean isMessage) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        if (isMessage) {
            System.out.println("Error: The entered \"uuid\" (" + uuid + ") does not exist !!!");
        }
        return -1;
    }
}

package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    public static final int CAPACITY = 10000;
    Resume[] storage = new Resume[CAPACITY];
    int size;
    int indexResumePresent;

    public void update(Resume r) {
        if (hasResume(r.getUuid())) {
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
            return;
        } else {
            for (int i = 0; i < size; i++) {
                if (storage[i].getUuid().equals(r.getUuid())) {
                    System.out.println("Error: \"uuid\" (" + r + ") already exists!");
                    return;
                }
            }
        }
        storage[size++] = r;
    }

    public Resume get(String uuid) {
        if (hasResume(uuid)) {
            return storage[indexResumePresent];
        }
        return null;
    }

    public void delete(String uuid) {
        if (hasResume(uuid)) {
            if (size > 1 && indexResumePresent < size - 1) {
                System.arraycopy(storage, (indexResumePresent + 1), storage, indexResumePresent,
                        size - indexResumePresent - 1);
            }
            size--;
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

    private boolean hasResume(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                indexResumePresent = i;
                return true;
            }
        }
        System.out.println("Error: The entered \"uuid\" (" + uuid + ") does not exist !!!");
        return false;
    }
}

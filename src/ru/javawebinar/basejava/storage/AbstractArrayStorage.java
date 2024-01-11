 package ru.javawebinar.basejava.storage;

 import ru.javawebinar.basejava.model.Resume;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

    public int size() {
        return size;
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index == -1) {
            System.out.println("Error: The entered \"uuid\" (" + uuid + ") does not exist !!!");
            return null;
        }
        return storage[index];
    }

    protected abstract int getIndex(String uuid);
}

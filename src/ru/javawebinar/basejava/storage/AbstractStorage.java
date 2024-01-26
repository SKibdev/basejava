package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

    public final void save(Resume r) {
        String uuid = r.getUuid();
        int index = getIndex(uuid);
        if (index >= 0) {
            throw new ExistStorageException(uuid);
        }
        increaseSize(uuid);
        insertElement(r, index);
    }

    protected void increaseSize(String uuid) {

    }

    public final void update(Resume r) {
        int index = getExistingIndex(r.getUuid());
        replaceElement(index, r);
    }

    public final void delete(String uuid) {
        int index = getExistingIndex(uuid);
        deleteElement(index);
    }

    public final Resume get(String uuid) {
        int index = getExistingIndex(uuid);
        return getElement(index);
    }

    protected final int getExistingIndex(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return index;
    }

    public abstract Resume[] getAll();

    public abstract int size();

    public abstract void clear();

    protected abstract int getIndex(String uuid);

    protected abstract void insertElement(Resume r, int index);

    protected abstract void replaceElement(int index, Resume r);

    protected abstract void deleteElement(int index);

    protected abstract Resume getElement(int index);
}

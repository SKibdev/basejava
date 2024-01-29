package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage <T extends Comparable<T>> implements Storage  {

    public final void doSave(Resume r) {
        String uuid = r.getUuid();
        T searchKey = getIndex(uuid);
        if (searchKey.compareTo((T) Integer.valueOf(0)) >= 0) {
            throw new ExistStorageException(uuid);
        }
        increaseSize(uuid);
        insertElement(r, (Integer) searchKey);
    }

    protected void increaseSize(String uuid) {

    }

    public final void doUpdate(Resume r) {
        T searchKey = getExistingSearchKey(r.getUuid());
        replaceElement((Integer) searchKey, r);
    }

    public final void doDelete(String uuid) {
        T searchKey = getExistingSearchKey(uuid);
        deleteElement((Integer) searchKey);
    }

    public final Resume doGet(String uuid) {
        T searchKey = getExistingSearchKey(uuid);
        return getElement((Integer) searchKey);
    }

    protected final T getExistingSearchKey(String uuid) {
        T searchKey = getIndex(uuid);
        if (searchKey.compareTo((T) Integer.valueOf(0)) < 0) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    public abstract Resume[] doGetAll();

    public abstract int doGetSize();

    public abstract void doClear();

    protected abstract T getIndex(String uuid);

    protected abstract void insertElement(Resume r, int index);

    protected abstract void replaceElement(int index, Resume r);

    protected abstract void deleteElement(int index);

    protected abstract Resume getElement(int index);
}

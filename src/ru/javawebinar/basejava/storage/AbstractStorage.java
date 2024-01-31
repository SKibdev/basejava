package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage<T> implements Storage {

    public final void doSave(Resume r) {
        String uuid = r.getUuid();
        T searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        insertElement(searchKey, r);
    }

    public final void doUpdate(Resume r) {
        T searchKey = getExistingSearchKey(r.getUuid());
        replaceElement(searchKey, r);
    }

    public final void doDelete(String uuid) {
        T searchKey = getExistingSearchKey(uuid);
        deleteElement(searchKey);
    }

    public final Resume doGet(String uuid) {
        T searchKey = getExistingSearchKey(uuid);
        return getElement(searchKey);
    }

    protected final T getExistingSearchKey(String uuid) {
        T searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            return searchKey;
        }
        throw new NotExistStorageException(uuid);
    }

    public abstract Resume[] doGetAll();

    public abstract int doGetSize();

    public abstract void doClear();

    protected abstract T getSearchKey(String uuid);

    protected abstract boolean isExist(T searchKey);

    protected abstract void insertElement(T searchKey, Resume r);

    protected abstract void replaceElement(T searchKey, Resume r);

    protected abstract void deleteElement(T searchKey);

    protected abstract Resume getElement(T searchKey);
}

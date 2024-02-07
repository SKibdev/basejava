package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage<T> implements Storage {
    public final List<Resume> getAllSorted() {
        List<Resume> listStorage = getList();
        listStorage.sort(Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid));
        return listStorage;
    }

     public final void update(Resume r) {
        T searchKey = getExistingSearchKey(r.getUuid());
        doUpdate(searchKey, r);
    }

    public final void save(Resume r) {
        T searchKey = getNotExistingSearchKey(r.getUuid());
        doSave(searchKey, r);
    }

    public final Resume get(String uuid) {
        T searchKey = getExistingSearchKey(uuid);
        return doGet(searchKey);
    }

    public final void delete(String uuid) {
        T searchKey = getExistingSearchKey(uuid);
        doDelete(searchKey);
    }

    private T getExistingSearchKey(String uuid) {
        T searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            return searchKey;
        }
        throw new NotExistStorageException(uuid);
    }

    private T getNotExistingSearchKey(String uuid) {
        T searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            return searchKey;
        }
        throw new ExistStorageException(uuid);
    }

    public abstract void clear();

    public abstract int size();

    protected abstract List<Resume> getList();

    protected abstract void doUpdate(T searchKey, Resume r);

    protected abstract void doSave(T searchKey, Resume r);

    protected abstract Resume doGet(T searchKey);

    protected abstract void doDelete(T searchKey);

    protected abstract T getSearchKey(String uuid);

    protected abstract boolean isExist(T searchKey);
}

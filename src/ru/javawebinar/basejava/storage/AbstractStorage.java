package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage<T> implements Storage {
    private final static Comparator<Resume> FULLNAME_COMPARATOR =
            Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);

    public final List<Resume> getAllSorted() {
        List<Resume> listStorage = doCopyAll();
        listStorage.sort(FULLNAME_COMPARATOR);
        return listStorage;
    }

    public final void update(Resume r) {
        T searchKey = getExistingSearchKey(r.getUuid());
        doUpdate(r, searchKey);
    }

    public final void save(Resume r) {
        T searchKey = getNotExistingSearchKey(r.getUuid());
        doSave(r, searchKey);
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

    protected abstract List<Resume> doCopyAll();

    protected abstract void doUpdate(Resume r, T searchKey);

    protected abstract void doSave(Resume r, T searchKey);

    protected abstract Resume doGet(T searchKey);

    protected abstract void doDelete(T searchKey);

    protected abstract T getSearchKey(String uuid);

    protected abstract boolean isExist(T searchKey);
}

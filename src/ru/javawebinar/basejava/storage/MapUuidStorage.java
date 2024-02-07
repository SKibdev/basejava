package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.*;

public class MapUuidStorage extends AbstractStorage<String> {
    protected final Map<String, Resume> storage = new HashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public List<Resume> getList() {
        Collection<Resume> resumes = storage.values();
        return new ArrayList<>(resumes);
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected void doUpdate(String searchKey, Resume r) {
        storage.put(searchKey, r);
    }

    @Override
    protected void doSave(String searchKey, Resume r) {
        storage.put(searchKey, r);
    }

    @Override
    protected Resume doGet(String searchKey) {
        return storage.get(searchKey);
    }

    @Override
    protected void doDelete(String searchKey) {
        storage.remove(searchKey);
    }

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExist(String searchKey) {
        return storage.containsKey(searchKey);
    }
}



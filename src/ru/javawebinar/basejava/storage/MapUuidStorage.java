package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.*;

public class MapUuidStorage extends AbstractStorage<String> {
    protected final Map<String, Resume> map = new HashMap<>();

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public List<Resume> doCopyAll() {
        Collection<Resume> resumes = map.values();
        return new ArrayList<>(resumes);
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    protected void doUpdate(Resume r, String searchKey) {
        map.put(searchKey, r);
    }

    @Override
    protected void doSave(Resume r, String searchKey) {
        map.put(searchKey, r);
    }

    @Override
    protected Resume doGet(String searchKey) {
        return map.get(searchKey);
    }

    @Override
    protected void doDelete(String searchKey) {
        map.remove(searchKey);
    }

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExist(String searchKey) {
        return map.containsKey(searchKey);
    }
}



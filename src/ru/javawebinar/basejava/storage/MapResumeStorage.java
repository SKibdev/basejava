package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage<Resume> {
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
    protected void doUpdate(Resume r, Resume searchKey) {
        map.put(searchKey.getUuid(), r);
    }

    @Override
    protected void doSave(Resume r, Resume searchKey) {
        // Если doSave запустился, значит searchKey = null
        String keyMap = r.getUuid();
        map.put(keyMap, r);
    }

    @Override
    protected Resume doGet(Resume searchKey) {
        return searchKey;
    }

    @Override
    protected void doDelete(Resume searchKey) {
        map.remove(searchKey.getUuid());
    }

    @Override
    protected Resume getSearchKey(String uuid) {
        return map.get(uuid);
    }

    @Override
    protected boolean isExist(Resume searchKey) {
        return searchKey != null;
    }
}

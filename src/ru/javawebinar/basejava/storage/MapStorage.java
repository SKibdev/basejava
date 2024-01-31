package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

// сравнить с Абстраксторожом и наконец вынести лишнее от массивов в абстрактаррайсторож
public class MapStorage extends AbstractStorage<String> {
    protected final Map<String, Resume> storage = new HashMap<>();

    @Override
    public Resume[] doGetAll() {
        Collection<Resume> resumes = storage.values();
        return resumes.toArray(new Resume[0]);
    }

    @Override
    public int doGetSize() {
        return storage.size();
    }

    @Override
    public void doClear() {
        storage.clear();
    }

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExist(String searchKey) {
        return storage.containsKey(searchKey);
    }

    @Override
    protected void insertElement(String searchKey, Resume r) {
        storage.put(searchKey, r);
    }

    @Override
    protected void replaceElement(String searchKey, Resume r) {
        storage.put(searchKey, r);
    }

    @Override
    protected void deleteElement(String searchKey) {
        storage.remove(searchKey);
    }

    @Override
    protected Resume getElement(String searchKey) {
        return storage.get(searchKey);
    }
}



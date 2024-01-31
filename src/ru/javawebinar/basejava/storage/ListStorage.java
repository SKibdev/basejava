package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;

public class ListStorage extends AbstractStorage<Integer> {
    protected final ArrayList<Resume> storage = new ArrayList<>();

    @Override
    public Resume[] doGetAll() {
        return storage.toArray(new Resume[0]);
    }

    @Override
    public int doGetSize() {
        return storage.size();
    }

    @Override
    public final void doClear() {
        storage.clear();
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        Resume searchResume = new Resume(uuid);
        int searchKey = -1;
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).equals(searchResume)) {
                searchKey = i;
            }
        }
        return searchKey;
    }

    @Override
    protected boolean isExist(Integer searchKey) {
        return searchKey >= 0;
    }

    @Override
    protected void insertElement(Integer searchKey, Resume r) {
        storage.add(r);
    }

    @Override
    protected void replaceElement(Integer searchKey, Resume r) {
        storage.set(searchKey, r);
    }

    @Override
    protected final void deleteElement(Integer searchKey) {
        storage.remove((int) searchKey);
    }

    @Override
    protected final Resume getElement(Integer searchKey) {
        return storage.get(searchKey);
    }
}


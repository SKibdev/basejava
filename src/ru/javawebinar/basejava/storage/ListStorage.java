package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;

public class ListStorage extends AbstractStorage<Integer> {
    protected final ArrayList<Resume> storage = new ArrayList<>();

    @Override
    public final void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected boolean isExist(Integer searchKey) {
        return searchKey >= 0;
    }

    @Override
    protected void doSave(Integer searchKey, Resume r) {
        storage.add(r);
    }

    @Override
    protected void doUpdate(Integer searchKey, Resume r) {
        storage.set(searchKey, r);
    }

    @Override
    protected final Resume doGet(Integer searchKey) {
        return storage.get(searchKey);
    }

    @Override
    protected final void doDelete(Integer searchKey) {
        storage.remove((int) searchKey);
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}


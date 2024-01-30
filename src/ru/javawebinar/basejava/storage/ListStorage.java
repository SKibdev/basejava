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
    protected Integer getKey(String uuid) {
        Resume searchKey = new Resume(uuid);
        int index = -1;
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).equals(searchKey)) {
                index = i;
            }
        }
        return index;
    }

    @Override
    protected void insertElement(Resume r, int index) {
        storage.add(r);
    }

    @Override
    protected void replaceElement(int index, Resume r) {
        storage.set(index, r);
    }

    @Override
    protected final void deleteElement(int index) {
        storage.remove(index);
    }

    @Override
    protected final Resume getElement(int index) {
        return storage.get(index);
    }
}


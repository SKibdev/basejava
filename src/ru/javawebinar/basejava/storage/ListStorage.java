package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;

public class ListStorage extends AbstractStorage {
    protected final ArrayList<Resume> storage = new ArrayList<>();

    @Override
    public Resume[] getAll() {
        return storage.toArray(storage.toArray(new Resume[0]));
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public final void clear() {
        storage.clear();
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume(uuid);
        return storage.indexOf(searchKey);
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
        storage.trimToSize();
    }

    @Override
    protected final Resume getElement(int index) {
        return storage.get(index);
    }
}


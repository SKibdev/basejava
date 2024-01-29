package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

/**
 * Array based storage for Resumes
 */
public interface Storage {

    void doClear();

    void doUpdate(Resume r);

    void doSave(Resume r);

    Resume doGet(String uuid);

    void doDelete(String uuid);

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] doGetAll();

    int doGetSize();
}

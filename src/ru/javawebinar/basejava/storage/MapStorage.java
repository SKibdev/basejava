package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
// implements Storage - для тестирования, временно
public class MapStorage implements Storage/*extends AbstractStorage<String> */{
    protected final Map<String, Resume> storage = new HashMap<>();
// ТО ДО сделать полностью мап отдельно - готово
// сравнить с Абстраксторожом и наконец вынести лишнее от массивов в абстрактаррайсторож

    public final void doSave(Resume r) {
        String uuid = r.getUuid();
        String searchKey = getKey(uuid);
        if (!searchKey.equals("-1")) {
            throw new ExistStorageException(uuid);
        }
//        increaseSize(uuid);
        insertElement(uuid, r);
    }

    public final void doUpdate(Resume r) {
        String searchKey = getExistingSearchKey(r.getUuid());
        replaceElement(searchKey, r);
    }

    public final void doDelete(String uuid) {
        String searchKey = getExistingSearchKey(uuid);
        deleteElement(searchKey);
    }

    public final Resume doGet(String uuid) {
        String searchKey = getExistingSearchKey(uuid);
        return getElement(searchKey);
    }

    protected final String getExistingSearchKey(String uuid) {
        String searchKey = getKey(uuid);
        if (searchKey.equals("-1")) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

//    @Override
    public Resume[] doGetAll() {
        Collection<Resume> resumes = storage.values();
        return resumes.toArray(new Resume[0]);
    }

//    @Override
    public int doGetSize() {
        return storage.size();
    }

//    @Override
    public void doClear() {
        storage.clear();
    }

//    @Override
    protected String getKey(String uuid) {
        if (storage.containsKey(uuid)) {
            return uuid;
        }
        return "-1";
    }

//    @Override
    protected void insertElement(String key, Resume r) {
        storage.put(key, r);
    }

//    @Override
    protected void replaceElement(String key, Resume r) {
        storage.put(key, r);
    }

//    @Override
    protected void deleteElement(String index) {
        storage.remove(index);
    }

//    @Override
    protected Resume getElement(String key) {
        return storage.get(key);
    }
}

package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {
    // Пример Локального класса
//    private static class ResumeComparator implements Comparator<Resume> {
//        @Override
//        public int compare(Resume o1, Resume o2) {
//            return o1.getUuid().compareTo(o2.getUuid());
//        }
//    }
    // Анонимный класс - получает имя в run time переделан в лямда выражение
    private static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getUuid);

    @Override
    protected Integer getSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid, "fullName");
        return Arrays.binarySearch(storage, 0, size, searchKey, RESUME_COMPARATOR);
    }

    @Override
    protected void insertElement(Resume r, Integer searchKey) {
        searchKey = -(searchKey + 1);
        System.arraycopy(storage, searchKey, storage, searchKey + 1, size - searchKey);
        storage[searchKey] = r;
    }

    @Override
    protected void fillDeletedElement(Integer searchKey) {
        if (size - 1 != searchKey) {
            System.arraycopy(storage, searchKey + 1, storage, searchKey, size - 1 - searchKey);
        }
    }
}

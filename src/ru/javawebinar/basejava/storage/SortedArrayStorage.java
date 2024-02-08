package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {
    // Пример реализации интерфейса Comparator для класса Resume в котором не реализован интерфейс Comparable

//     Пример Локального класса - Определен внутри блока кода, обычно внутри метода или конструктора.
//     Его область видимости ограничена блоком кода, в котором он определен.
//    private static final Comparator<Resume> RESUME_COMPARATOR = new ResumeComparator();
//        private static class ResumeComparator implements Comparator<Resume> {
//        @Override
//        public int compare(Resume o1, Resume o2) {
//            return o1.getUuid().compareTo(o2.getUuid());
//        }
//    }

    // Анонимный класс - получает имя в run time
//    private static final Comparator<Resume> RESUME_COMPARATOR = new Comparator<Resume>() {
//        @Override
//        public int compare(Resume o1, Resume o2) {
//            return o1.getUuid().compareTo(o2.getUuid());
//        }
//    };

    // Анонимный класс - получает имя в run time переделан в лямда выражение
//    private static final Comparator<Resume> RESUME_COMPARATOR = (o1, o2) -> o1.getUuid().compareTo(o2.getUuid());

    private static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getUuid);

    @Override
    protected Integer getSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid, "fullName");
        return Arrays.binarySearch(storage, 0, size, searchKey, RESUME_COMPARATOR);
    }

//    Пример статического вложенного класса (static nested classes)
//    private static final Comparator<Resume> RESUME_COMPARATOR =new ResumeComparator();
//    private static class ResumeComparator implements Comparator<Resume> {
//        @Override
//        public int compare(Resume o1, Resume o2) {
//            return o1.getUuid().compareTo(o2.getUuid());
//        }
//    }

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

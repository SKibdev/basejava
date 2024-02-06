package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.SortedArrayStorage;
import ru.javawebinar.basejava.storage.Storage;

/**
 * Test for your ru.javawebinar.basejava.storage.ArrayStorage implementation
 */
public class MainTestArrayStorage {
    private static final Storage ARRAY_STORAGE = new SortedArrayStorage();

    public static void main(String[] args) {
        final Resume r1 = new Resume("uuid6", "fullName6");
        final Resume r2 = new Resume("uuid1", "fullName1");
        final Resume r3 = new Resume("uuid11", "fullName11");

        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r3);

        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid()));
        System.out.println("Size: " + ARRAY_STORAGE.size());

        System.out.println("doGet dummy: " + ARRAY_STORAGE.get("dummy"));

        ARRAY_STORAGE.update(r3);

        System.out.println("doGet updated r3: " + ARRAY_STORAGE.get(r3.getUuid()));

        printAll();
        ARRAY_STORAGE.delete(r1.getUuid());
        printAll();
        ARRAY_STORAGE.clear();
        printAll();

        System.out.println("doGetSize: " + ARRAY_STORAGE.size());
    }

    static void printAll() {
        System.out.println("\ndoGet All");
//        for (Resume r : ARRAY_STORAGE.getAllSorted() {
//            System.out.println(r);
//        }
        System.out.println(ARRAY_STORAGE.getAllSorted());
    }
}

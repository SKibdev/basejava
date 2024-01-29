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
        final Resume r1 = new Resume("uuid6");
        final Resume r2 = new Resume("uuid1");
        final Resume r3 = new Resume("uuid11");

        ARRAY_STORAGE.doSave(r1);
        ARRAY_STORAGE.doSave(r2);
        ARRAY_STORAGE.doSave(r3);

        System.out.println("Get r1: " + ARRAY_STORAGE.doGet(r1.getUuid()));
        System.out.println("Size: " + ARRAY_STORAGE.doGetSize());

        System.out.println("doGet dummy: " + ARRAY_STORAGE.doGet("dummy"));

        ARRAY_STORAGE.doUpdate(r3);

        System.out.println("doGet updated r3: " + ARRAY_STORAGE.doGet(r3.getUuid()));

        printAll();
        ARRAY_STORAGE.doDelete(r1.getUuid());
        printAll();
        ARRAY_STORAGE.doClear();
        printAll();

        System.out.println("doGetSize: " + ARRAY_STORAGE.doGetSize());
    }

    static void printAll() {
        System.out.println("\ndoGet All");
        for (Resume r : ARRAY_STORAGE.doGetAll()) {
            System.out.println(r);
        }
    }
}

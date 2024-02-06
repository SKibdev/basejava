package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.Resume;

import java.util.*;

public class MainCollections {
    public static final String UUID_1 = "uuid1";
    private static final Resume RESUME_UUID_1 = new Resume(UUID_1, "fullName");
    public static final String UUID_2 = "uuid2";
    private static final Resume RESUME_UUID_2 = new Resume(UUID_2, "fullName");
    public static final String UUID_3 = "uuid3";
    private static final Resume RESUME_UUID_3 = new Resume(UUID_3, "fullName");
    public static final String UUID_4 = "uuid4";
    private static final Resume RESUME_UUID_4 = new Resume(UUID_4, "fullName");

    public static void main(String[] args) {
        Collection<Resume> collection = new ArrayList<>();
        collection.add(RESUME_UUID_1);
        collection.add(RESUME_UUID_2);
        collection.add(RESUME_UUID_3);

        for (Resume r : collection) {
            System.out.println(r);
            if (Objects.equals(r.getUuid(), UUID_1)) {
//                collection.remove(r);
            }
        }

        Iterator<Resume> iterator = collection.iterator();
        while (iterator.hasNext()) {
            Resume r = iterator.next();
            System.out.println(r);
            if (Objects.equals(r.getUuid(), UUID_1)) {
                iterator.remove();
            }
        }
        System.out.println(collection);

        Map<String, Resume> map = new HashMap<>();
        map.put(UUID_1, RESUME_UUID_1);
        map.put(UUID_2, RESUME_UUID_2);
        map.put(UUID_3, RESUME_UUID_3);

        // плохой вариант, т.к. map.get(uuid) - затратная опреация
        for (String uuid : map.keySet()) {
            System.out.println(map.get(uuid));
        }

        for (Map.Entry<String, Resume> entry : map.entrySet()) {
            System.out.println(entry.getValue());
        }
        List<Resume> resumes = Arrays.asList(RESUME_UUID_1, RESUME_UUID_2, RESUME_UUID_3);
        // не работает, так как Arrays.asList возвращает ссылку на новый
        //  ArrayList внутри вложенного статитический класса public static <T> List<T> asList
        // resumes.remove(1);
        System.out.println(resumes);
    }
}

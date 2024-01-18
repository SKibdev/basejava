package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {
    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Resume r = new Resume();
        Field field = r.getClass().getDeclaredFields()[0];
        field.setAccessible(true);
        System.out.println(field.getName());
        System.out.println(field.get(r));
        field.set(r, "new_uuid");

        System.out.println("Вызываем метод toString у Resume r стандартным методом: " + r.toString());
        Method method = r.getClass().getDeclaredMethod("toString");
        System.out.println("Вызываем метод toString у Resume r через отражение:     " + method.invoke(r));
    }
}

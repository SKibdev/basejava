package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {
    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Resume r = new Resume();
        /* Тип Class<? extends Resume> представляет собой параметризованный тип:
        1. Class: Это встроенный класс в Java, который используется для представления информации о классе во время
        выполнения. Объект типа Class содержит метаданные о классе, такие как его поля, методы, интерфейсы и т. д.
        2. <? extends Resume>: Это часть, связанная с использованием дженериков (generics) в Java.
        Здесь используется ограничение с верхней границей (extends). Он говорит о том, что переменная resumeClass
        будет содержать объект типа Class, который представляет собой класс, являющийся подклассом (или самим)
        класса Resume.

        Таким образом, Class<? extends Resume> означает, что переменная resumeClass содержит объект типа Class,
        представляющий класс, который является подклассом класса Resume или самим классом Resume. Это полезно,
        например, когда вы хотите динамически работать с объектами различных классов, наследующих от Resume.
        */

        Class<? extends Resume> resumeClass = r.getClass();
        Field field = resumeClass.getDeclaredFields()[0];
        field.setAccessible(true);
        System.out.println(field.getName());
        System.out.println(field.get(r));
        field.set(r, "new_uuid");

        System.out.println("Вызываем метод toString у Resume r стандартным методом: " + r.toString());
        /*
        getMethod (из класса Class):
        Используется для получения объекта Method для публичного метода класса или его предков.
        Только для публичных методов.
        Включает в себя методы, унаследованные от суперклассов.
        Не включает в себя приватные методы и методы, объявленные в интерфейсах, реализуемых классом.
        В случае, если метод не является публичным, выбрасывается исключение NoSuchMethodException.

        getDeclaredMethod (из класса Class):
        Используется для получения объекта Method для метода, объявленного в самом классе, независимо от его модификатора доступа (публичный, защищенный, частный и т. д.).
        Включает в себя все методы, в том числе приватные и защищенные, но не включает унаследованные методы из суперклассов.
        Если метод не найден, выбрасывается исключение NoSuchMethodException.
         */

        Method method = resumeClass.getMethod("toString");
        System.out.println("Вызываем метод toString у Resume r через отражение (getMethod):      " + method.invoke(r));

        method = resumeClass.getDeclaredMethod("toString");
        System.out.println("Вызываем метод toString у Resume r через отражение(getDeclaredMethod):" + method.invoke(r));

    }
}

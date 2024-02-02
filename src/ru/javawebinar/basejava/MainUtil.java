package ru.javawebinar.basejava;

public class MainUtil {
    public static void main(String[] args) {
        System.out.println(Integer.valueOf(-1) == Integer.valueOf(-1));
        // Unboxing of 'getInt()' may produce 'NullPointerException'
        //Распаковка getInt() может привести к исключению NullPointerException
        int result = getInt();
        System.out.println(result);
    }

    private static Integer getInt() {
        return null;
    }
}

//System.out.println(Integer.valueOf(-1) == new Integer(-1));
/*
Ошибка "Integer(int) is deprecated and marked for removal" указывает на то, что конструктор Integer(int)
в классе Integer является устаревшим (deprecated) и будет удален в будущих версиях Java. Устаревшие методы
обычно помечаются как таковые, чтобы предупредить разработчиков о том, что эти методы больше не рекомендуется
использовать и будут удалены в будущем.
                Если у вас есть код, использующий new Integer(int), вам следует пересмотреть его и использовать другие
                способы создания объектов типа Integer. Вместо использования конструктора Integer(int),
                 рекомендуется использовать метод Integer.valueOf(int), который предоставляет более эффективный
                 механизм создания объектов Integer и возвращает кешированные объекты для некоторых значений.

 */
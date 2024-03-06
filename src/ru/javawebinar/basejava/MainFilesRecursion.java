package ru.javawebinar.basejava;

import java.io.File;
import java.util.Objects;

public class MainFilesRecursion {
    public static void main(String[] args) {
        File dir = new File(".\\");
        printDirectoryDeeply1(dir);
        printDirectoryDeeply2(dir);
    }

    static void printDirectoryDeeply1(File dir) {
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            for (File file : Objects.requireNonNull(files)) {
                System.out.println(file.getName());
                if (file.isDirectory()) {
                    printDirectoryDeeply1(file);
                }
            }
        }
    }

    // Вариант из разбора ДЗ. Отличается от моего, тем что выводит имя,
    // даже если в метод подасться файл, а не директория. Есть  "File: " и "Directory: "
    static void printDirectoryDeeply2(File dir) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    System.out.println("File: " + file.getName());
                } else if (file.isDirectory()) {
                    System.out.println("Directory: " + file.getName());
                    printDirectoryDeeply2(file);
                }
            }
        }
    }
}

package ru.javawebinar.basejava;

import java.io.File;
import java.util.Objects;

public class MainFilesRecursion {
    public static void main(String[] args) {
        File dir = new File(".\\");
        getFiles(dir);
    }

    static void getFiles(File dir) {
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            for (File file : Objects.requireNonNull(files)) {
                System.out.println(file);
                if (file.isDirectory()) {
                    getFiles(file);
                }
            }
        }
    }
}

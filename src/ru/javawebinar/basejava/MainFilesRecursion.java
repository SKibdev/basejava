package ru.javawebinar.basejava;

import java.io.File;

public class MainFilesRecursion {
    public static void main(String[] args) {
        File dir = new File(".\\");
        getFiles(dir);
    }

    static void getFiles(File dir) {
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            for (File file : files) {
                System.out.println(file);
                if (file.isDirectory()) {
                    getFiles(file);
                }
            }
        }
    }
}

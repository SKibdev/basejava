package ru.javawebinar.basejava;

import java.io.File;

public class MainFilesRecursion {
    static final StringBuilder INDENTATION = new StringBuilder();
    static final String INDENTATION_STEP = "    ";
    static final int INDENTATION_STEP_LENGTH = INDENTATION_STEP.length();

    public static void main(String[] args) {
        File dir = new File("S:\\java\\getoffer\\storage");
        printDirectoryDeeply(dir);
        printDirectoryDeeply1(dir, "");
    }

    static void printDirectoryDeeply(File dir) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    System.out.println(INDENTATION + "F: " + file.getName());
                } else if (file.isDirectory()) {
                    System.out.println(INDENTATION + "D: " + file.getName());
                    INDENTATION.append(INDENTATION_STEP);
                    printDirectoryDeeply(file);
                    int indentationLength = INDENTATION.length();
                    if (indentationLength >= INDENTATION_STEP_LENGTH) {
                        INDENTATION.delete(indentationLength - INDENTATION_STEP_LENGTH, indentationLength);
                    }
                }
            }
        }
    }

    static void printDirectoryDeeply1(File dir, String offset) {
        File[] files = dir.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    System.out.println(offset + "F: " + file.getName());
                } else if (file.isDirectory()) {
                    System.out.println(offset + "D: " + file.getName());
                    printDirectoryDeeply1(file, offset + "    ");
                }
            }
        }
    }
}

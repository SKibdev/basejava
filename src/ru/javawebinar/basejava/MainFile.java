package ru.javawebinar.basejava;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class MainFile {
    public static void main(String[] args) throws IOException {
//        String filePath = ".\\.gitignore";
//
//        File file = new File(filePath);
//        try {
//            System.out.println(file.getCanonicalPath());
//        } catch (IOException e) {
//            throw new RuntimeException("Error", e);
//        }
//
//        File dir = new File(".\\src\\ru\\javawebinar\\basejava");
//        System.out.println(dir.isDirectory());
//        String[] list = dir.list();
//        if (list != null) {
//            for (String name : list) {
//                System.out.println(name);
//            }
//        }
//
//        try (FileInputStream fis = new FileInputStream(filePath)) {
//            System.out.println(fis.read());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        File storageDir = new File("S:\\java\\getoffer\\storage");
        System.out.println(storageDir.isDirectory());
        String[] storageDirList = storageDir.list();
        System.out.println(Arrays.toString(storageDirList));
        File[] files = storageDir.listFiles();
        for (File file : Objects.requireNonNull(files)) {
            file.delete();
        }
        storageDirList = storageDir.list();
        System.out.println(Arrays.toString(storageDirList));

        for (int i = 0; i < 10; i++) {
            File file = new File("S:\\java\\getoffer\\storage\\uuid" + i + ".txt");
            file.createNewFile();
        }

//        System.out.println(storageDirList.length + " " + files.length);
//        File Resume1 = new File("S:\\java\\getoffer\\storage\\uuid1.txt");
//
//        System.out.println(Resume1.isFile());
//        System.out.println(Resume1.getAbsolutePath());
//        System.out.println(Arrays.toString(storageDirList));
//        if (!Resume1.delete()) {
//            Resume1.createNewFile();
//        }



    }
}

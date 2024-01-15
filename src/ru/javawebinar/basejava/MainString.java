package ru.javawebinar.basejava;

public class MainString {
    public static void main(String[] args) {
        String [] strArray = {"1", "2", "3", "4", "5"};
//        String result = "";
        StringBuilder sb = new StringBuilder();
        for (String str : strArray) {
            sb.append(str).append(", ");
        }
        System.out.println(sb);
        String str1 = "abc";
        String strC = "c";

        String str2 = ("ab" + strC);
        System.out.println(str1 == str2);
        System.out.println(str1.equals(str2));
        str2 = "ab1";
        System.out.println(str1);

    }
}

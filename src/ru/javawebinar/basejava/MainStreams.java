package ru.javawebinar.basejava;

import java.util.Arrays;

public class MainStreams {
    public static void main(String[] args) {
        System.out.println(minValue(new int[]{1, 2, 3, 3, 2, 3}));
        System.out.println(minValue(new int[]{9, 8}));
        System.out.println(minValue(new int[]{6, 7, 2, 5, 9, 9, 3, 7}));
        System.out.println(minValue(new int[]{6, 6, 6, 6}));

    }

    static int minValue(int[] values) {

        return Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce(0, (acc, i) -> acc * 10 + i);
    }
}

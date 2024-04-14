package ru.javawebinar.basejava;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MainStreams {
    public static void main(String[] args) {
        System.out.println(minValue(new int[]{1, 2, 3, 3, 2, 3}));
        System.out.println(minValue(new int[]{9, 8}));
        System.out.println(minValue(new int[]{6, 7, 2, 5, 9, 9, 3, 7}));
        System.out.println(minValue(new int[]{6, 6, 6, 6}));
        System.out.println(oddOrEven(new ArrayList<>(Arrays.asList(1, 2, 1, 1, 2, 1))));
        System.out.println(oddOrEven(new ArrayList<>(Arrays.asList(1, 2, 1, 1, 2))));
    }

    static int minValue(int[] values) {
        return Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce(0, (acc, i) -> acc * 10 + i);
    }

    static List<Integer> oddOrEven(List<Integer> integers) {
        boolean isEven = integers.stream().filter(n -> n % 2 != 0).count() % 2 == 0;
        return integers.stream().filter(n -> isEven != (n % 2 == 0)).collect(Collectors.toList());
    }
}

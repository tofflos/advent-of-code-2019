package com.adventofcode2019;

import java.io.IOException;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) throws IOException {

        System.out.println("Part 1: " + IntStream.range(134564, 585159)
                .mapToObj(i -> String.valueOf(i))
                .filter(Main::isValid1)
                .count());

        System.out.println("Part 2: " + IntStream.range(134564, 585159)
                .mapToObj(i -> String.valueOf(i))
                .filter(Main::isValid2)
                .count());
    }

    static boolean isValid1(String digits) {
        return digits.length() == 6 && hasPair(digits) && isSameOrMore(digits);
    }

    static boolean isValid2(String digits) {
        return digits.length() == 6 && hasIsolatedPair(digits) && isSameOrMore(digits);
    }

    static boolean hasPair(String digits) {
        boolean b = false;

        for (int i = 0; i < digits.length() - 1; i++) {
            if (digits.charAt(i) == digits.charAt(i + 1)) {
                b = true;
                break;
            }
        }

        return b;
    }

    static boolean isSameOrMore(String digits) {
        boolean b = true;

        for (int i = 0; i < digits.length() - 1; i++) {
            if (digits.charAt(i) > digits.charAt(i + 1)) {
                b = false;
                break;
            }
        }

        return b;
    }

    static boolean hasIsolatedPair(String digits) {
        char p = Character.MIN_VALUE;
        int n = 1;

        for (int i = 0; i < digits.length(); i++) {
            char c = digits.charAt(i);

            if (c == p) {
                n++;
            } else {
                if (n == 2) {
                    break;
                } else {
                    p = c;
                    n = 1;
                }
            }
        }

        return n == 2;
    }
}

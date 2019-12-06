package com.adventofcode2019;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws IOException {

        var initialState = Arrays.stream(Files.readString(Paths.get("1.in")).split(",")).mapToInt(Integer::parseInt).toArray();
        System.out.println("Part 1; " + execute(initialState, 1));
        System.out.println("Part 2; " + execute(initialState, 5));
    }

    static int execute(int[] initialState, int input) {
        int[] memory = Arrays.copyOf(initialState, initialState.length);
        int output = Integer.MIN_VALUE;

        loop:
        for (int ptr = 0;;) {
            int opcode = memory[ptr++];

            switch (opcode) {
                case 1-> {
                    int a = memory[ptr++];
                    int b = memory[ptr++];
                    int c = memory[ptr++];
                    memory[c] = memory[a] + memory[b];
                }
                case 101-> {
                    int a = memory[ptr++];
                    int b = memory[ptr++];
                    int c = memory[ptr++];
                    memory[c] = a + memory[b];
                }
                case 1101-> {
                    int a = memory[ptr++];
                    int b = memory[ptr++];
                    int c = memory[ptr++];
                    memory[c] = a + b;
                }
                case 1001-> {
                    int a = memory[ptr++];
                    int b = memory[ptr++];
                    int c = memory[ptr++];
                    memory[c] = memory[a] + b;
                }
                case 2-> {
                    int a = memory[ptr++];
                    int b = memory[ptr++];
                    int c = memory[ptr++];
                    memory[c] = memory[a] * memory[b];
                }
                case 102-> {
                    int a = memory[ptr++];
                    int b = memory[ptr++];
                    int c = memory[ptr++];
                    memory[c] = a * memory[b];
                }
                case 1102-> {
                    int a = memory[ptr++];
                    int b = memory[ptr++];
                    int c = memory[ptr++];
                    memory[c] = a * b;
                }
                case 1002-> {
                    int a = memory[ptr++];
                    int b = memory[ptr++];
                    int c = memory[ptr++];
                    memory[c] = memory[a] * b;
                }
                case 3-> {
                    int a = memory[ptr++];
                    memory[a] = input;
                }
                case 4-> {
                    int a = memory[ptr++];
                    output = memory[a];
                }
                case 104-> {
                    int a = memory[ptr++];
                    output = a;
                }
                case 5-> {
                    int a = memory[ptr++];
                    int b = memory[ptr++];

                    if (memory[a] != 0) {
                        ptr = memory[b];
                    }
                }
                case 1105-> {
                    int a = memory[ptr++];
                    int b = memory[ptr++];

                    if (a != 0) {
                        ptr = b;
                    }
                }
                case 1005-> {
                    int a = memory[ptr++];
                    int b = memory[ptr++];

                    if (memory[a] != 0) {
                        ptr = b;
                    }
                }
                case 105-> {
                    int a = memory[ptr++];
                    int b = memory[ptr++];

                    if (a != 0) {
                        ptr = memory[b];
                    }
                }
                case 1106-> {
                    int a = memory[ptr++];
                    int b = memory[ptr++];

                    if (a == 0) {
                        ptr = b;
                    }
                }
                case 1006-> {
                    int a = memory[ptr++];
                    int b = memory[ptr++];

                    if (memory[a] == 0) {
                        ptr = b;
                    }
                }
                case 106-> {
                    int a = memory[ptr++];
                    int b = memory[ptr++];

                    if (a == 0) {
                        ptr = memory[b];
                    }
                }
                case 7-> {
                    int a = memory[ptr++];
                    int b = memory[ptr++];
                    int c = memory[ptr++];

                    memory[c] = memory[a] < memory[b] ? 1 : 0;
                }
                case 107-> {
                    int a = memory[ptr++];
                    int b = memory[ptr++];
                    int c = memory[ptr++];

                    memory[c] = a < memory[b] ? 1 : 0;
                }
                case 1007-> {
                    int a = memory[ptr++];
                    int b = memory[ptr++];
                    int c = memory[ptr++];

                    memory[c] = memory[a] < b ? 1 : 0;
                }
                case 1107-> {
                    int a = memory[ptr++];
                    int b = memory[ptr++];
                    int c = memory[ptr++];

                    memory[c] = a < b ? 1 : 0;
                }
                case 8-> {
                    int a = memory[ptr++];
                    int b = memory[ptr++];
                    int c = memory[ptr++];

                    memory[c] = memory[a] == memory[b] ? 1 : 0;
                }
                case 108-> {
                    int a = memory[ptr++];
                    int b = memory[ptr++];
                    int c = memory[ptr++];

                    memory[c] = a == memory[b] ? 1 : 0;
                }
                case 1008-> {
                    int a = memory[ptr++];
                    int b = memory[ptr++];
                    int c = memory[ptr++];

                    memory[c] = memory[a] == b ? 1 : 0;
                }
                case 1108-> {
                    int a = memory[ptr++];
                    int b = memory[ptr++];
                    int c = memory[ptr++];

                    memory[c] = a == b ? 1 : 0;
                }
                case 99-> {
                    break loop;
                }
                default-> {
                    throw new RuntimeException("Unknown opcode: " + opcode);
                }
            }

        }

        return output;
    }
}
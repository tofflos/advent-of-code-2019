package com.adventofcode2019;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws IOException {

        var initialState = Arrays.stream(Files.readString(Paths.get("1.in")).split(",")).mapToInt(Integer::parseInt).toArray();

        System.out.println("Part 1: " + part1(initialState, 12, 2));
        System.out.println("Part 2: " + part2(initialState));

    }

    static int part1(int[] initialState, int noun, int verb) {
        return execute(initialState, noun, verb)[0];
    }

    static int part2(int[] initialState) {
        for (int noun = 0; noun < 100; noun++) {
            for (int verb = 0; verb < 100; verb++) {
                if (execute(initialState, noun, verb)[0] == 19690720) {
                    return 100 * noun + verb;
                }
            }
        }

        throw new IllegalArgumentException("Unable to find a suitable noun and verb.");
    }

    static int[] execute(int[] initialState, int noun, int verb) {
        int[] memory = Arrays.copyOf(initialState, initialState.length);

        memory[1] = noun;
        memory[2] = verb;

        for (int ptr = 0;;) {
            int opcode = memory[ptr++];

            if (opcode == 99) {
                break;
            }

            int a = memory[ptr++];
            int b = memory[ptr++];
            int c = memory[ptr++];

            switch (opcode) {
                case 1:
                    memory[c] = memory[a] + memory[b];
                    break;
                case 2:
                    memory[c] = memory[a] * memory[b];
                    break;
                default:
                    throw new RuntimeException("Opcode not one of 1, 2 and 99: " + opcode);
            }
        }

        return memory;
    }
}

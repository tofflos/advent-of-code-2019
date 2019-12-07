package com.adventofcode2019;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Main {

    public static void main(String[] args) throws IOException {

        var program = Arrays.stream(Files.readString(Paths.get("1.in")).split(",")).mapToInt(Integer::parseInt).toArray();

        System.out.println("Part 1: " + permutations(0, 5).stream().mapToInt(p -> chain(program, p)).max().orElseThrow());
        System.out.println("Part 2: " + permutations(5, 10).stream().mapToInt(p -> feedback(program, p)).max().orElseThrow());
    }

    static int chain(int[] program, List<Integer> phases) {

        var a = new Computer(program);
        a.getInput().addAll(List.of(phases.get(0), 0));
        a.run();

        var b = new Computer(program);
        b.getInput().addAll(List.of(phases.get(1), a.getOutput().poll()));
        b.run();

        var c = new Computer(program);
        c.getInput().addAll(List.of(phases.get(2), b.getOutput().poll()));
        c.run();

        var d = new Computer(program);
        d.getInput().addAll(List.of(phases.get(3), c.getOutput().poll()));
        d.run();

        var e = new Computer(program);
        e.getInput().addAll(List.of(phases.get(4), d.getOutput().poll()));
        e.run();

        return e.getOutput().poll();
    }

    static int feedback(int[] program, List<Integer> phases) {

        var a = new Computer(program);
        var b = new Computer(program);
        var c = new Computer(program);
        var d = new Computer(program);
        var e = new Computer(program);

        a.getInput().add(phases.get(0));
        b.getInput().add(phases.get(1));
        c.getInput().add(phases.get(2));
        d.getInput().add(phases.get(3));
        e.getInput().add(phases.get(4));

        a.getInput().add(0);

        while (true) {
            a.run();

            b.getInput().add(a.getOutput().poll());
            b.run();

            c.getInput().add(b.getOutput().poll());
            c.run();

            d.getInput().add(c.getOutput().poll());
            d.run();

            e.getInput().add(d.getOutput().poll());
            e.run();

            if (e.getStatus().equals(Computer.Status.COMPLETED)) {
                break;
            } else {
                a.getInput().add(e.getOutput().poll());
            }
        }

        return e.getOutput().poll();
    }

    static List<List<Integer>> permutations(int from, int to) {
        List<List<Integer>> permutations = new ArrayList<>();

        for (int i = from; i < to; i++) {
            for (int j = from; j < to; j++) {
                for (int k = from; k < to; k++) {
                    for (int l = from; l < to; l++) {
                        for (int m = from; m < to; m++) {
                            if (i != j && i != k && i != l && i != m
                                    && j != k && j != l && j != m
                                    && k != l && k != m
                                    && l != m) {
                                permutations.add(List.of(i, j, k, l, m));
                            }
                        }
                    }
                }
            }
        }

        return permutations;
    }
}

class Computer implements Runnable {

    private final int[] memory;
    private int ptr;

    private final Queue<Integer> input;
    private final Queue<Integer> output;

    private Status status;

    enum Status {
        CREATED,
        RUNNING,
        WAITING,
        COMPLETED
    }

    public Computer(int[] program) {
        this.memory = Arrays.copyOf(program, program.length);
        this.ptr = 0;
        this.input = new LinkedList<>();
        this.output = new LinkedList<>();
        this.status = Status.CREATED;
    }

    public Queue<Integer> getInput() {
        return input;
    }

    public Queue<Integer> getOutput() {
        return output;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public void run() {
        status = Status.RUNNING;

        loop:
        while (true) {
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
                    if (input.isEmpty()) {
                        status = Status.WAITING;
                        ptr--;
                        return;
                    }

                    int a = memory[ptr++];
                    memory[a] = input.poll();
                }
                case 4-> {
                    int a = memory[ptr++];
                    output.add(memory[a]);
                }
                case 104-> {
                    int a = memory[ptr++];
                    output.add(a);
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
                    status = Status.COMPLETED;
                    break loop;
                }
                default-> {
                    throw new RuntimeException("Unknown opcode: " + opcode);
                }
            }
        }
    }
}

package com.adventofcode2019;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {

        var points = Files.lines(Paths.get("1.in"))
                .map(Main::parse)
                .collect(Collectors.toList());

        var intersections = points.get(0).stream()
                .filter(points.get(1)::contains)
                .collect(Collectors.toList());

        var distance = intersections.stream()
                .sorted((p1, p2) -> manhattan(p1) - manhattan(p2))
                .findFirst()
                .map(Main::manhattan)
                .orElseThrow();

        var steps = intersections.stream()
                .collect(Collectors.toMap(Function.identity(), p -> points.get(0).indexOf(p) + 1 + points.get(1).indexOf(p) + 1))
                .entrySet().stream()
                .sorted((e1, e2) -> e1.getValue() - e2.getValue())
                .findFirst()
                .map(Map.Entry::getValue)
                .orElseThrow();

        System.out.println("Part 1: " + distance);
        System.out.println("Part 2: " + steps);

    }

    static List<Point> parse(String wire) {
        var points = new ArrayList<Point>();
        var x = 0;
        var y = 0;

        for (var s : wire.split(",")) {
            var d = s.charAt(0);
            var n = Integer.parseInt(s.substring(1));

            switch (d) {
                case 'R':
                    for (int i = 0; i < n; i++) {
                        points.add(new Point(++x, y));
                    }
                    break;
                case 'U':
                    for (int i = 0; i < n; i++) {
                        points.add(new Point(x, ++y));
                    }
                    break;
                case 'L':
                    for (int i = 0; i < n; i++) {
                        points.add(new Point(--x, y));
                    }
                    break;
                case 'D':
                    for (int i = 0; i < n; i++) {
                        points.add(new Point(x, --y));
                    }
                    break;
                default:
                    throw new RuntimeException("Unknown direction: " + d);
            }
        }

        return points;
    }

    static int manhattan(Point p) {
        return Math.abs(p.getX()) + Math.abs(p.getY());
    }
}

class Point {

    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + this.x;
        hash = 97 * hash + this.y;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Point other = (Point) obj;
        if (this.x != other.x) {
            return false;
        }
        if (this.y != other.y) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Point{" + "x=" + x + ", y=" + y + '}';
    }
}

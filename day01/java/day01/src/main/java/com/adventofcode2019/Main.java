package com.adventofcode2019;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) throws IOException {

        var totalFuelRequirement = Files.lines(Paths.get("1.in"))
                .mapToInt(Integer::parseInt)
                .map(Main::calculateRequiredFuel)
                .sum();

        var adjustedFuelRequirement = Files.lines(Paths.get("1.in"))
                .mapToInt(Integer::parseInt)
                .map(Main::calculateAdjustedFuel)
                .sum();

        System.out.println("Day 1a: " + totalFuelRequirement);
        System.out.println("Day 1b: " + adjustedFuelRequirement);
    }

    static int calculateRequiredFuel(int mass) {
        return mass / 3 - 2;
    }

    static int calculateAdjustedFuel(int mass) {
        int requiredFuel = calculateRequiredFuel(mass);
        int extraFuel = requiredFuel;

        while ((extraFuel = calculateRequiredFuel(extraFuel)) > 0) {
            requiredFuel += extraFuel;
        }

        return requiredFuel;
    }
}

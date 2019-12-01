/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adventofcode2019;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 *
 * @author Erik
 */
public class MainTest {

    @ParameterizedTest
    @CsvSource({"12, 2", "14, 2", "1969, 654", "100756, 33583"})
    void calculateRequiredFuel(int mass, int expectedFuel) {
        assertEquals(expectedFuel, Main.calculateRequiredFuel(mass));
    }

    @ParameterizedTest
    @CsvSource({"14, 2", "1969, 966", "100756, 50346"})
    void calculateAdjustedFuel(int mass, int expectedFuel) {
        assertEquals(expectedFuel, Main.calculateAdjustedFuel(mass));
    }
}

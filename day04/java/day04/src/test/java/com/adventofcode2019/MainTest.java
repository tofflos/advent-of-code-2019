/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adventofcode2019;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 *
 * @author Erik
 */
public class MainTest {

    @ParameterizedTest
    @CsvSource({"111111, true", "223450, true", "123789, false"})
    void hasPair(String digits, boolean expected) {
        assertThat(Main.hasPair(digits)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"111111, true", "223450, false", "123789, true"})
    void isSameOrMore(String digits, boolean expected) {
        assertThat(Main.isSameOrMore(digits)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"112233, true", "123444, false", "111122, true"})
    void hasIsolatedPair(String digits, boolean expected) {
        assertThat(Main.hasIsolatedPair(digits)).isEqualTo(expected);
    }
}

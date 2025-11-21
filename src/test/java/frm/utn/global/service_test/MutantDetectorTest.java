package frm.utn.global.service_test;

import frm.utn.global.service.MutantDetector;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;

public class MutantDetectorTest {
    private MutantDetector detector;

    @BeforeEach
    void setUp() {
        detector = new MutantDetector();
    }

    @Test
    void testMutantHorizontal() {
        String[] dna = {
                "AAAA",
                "TGCT",
                "CGTA",
                "TTTT"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test
    void testMutantVertical() {
        String[] dna = {
                "ATGC",
                "ATGT",
                "ATGA",
                "ATGG"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test
    void testMutantDiagonalRight() {
        String[] dna = {
                "ATGC",
                "CAGT",
                "TTAT",
                "AAAA"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test
    void testMutantDiagonalLeft() {
        String[] dna = {
                "TGGG",
                "CTGA",
                "AGTG",
                "GGGG"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test
    void testMutantTwoSequences() {
        String[] dna = {
                "AAAA",
                "CAGT",
                "TTTT",
                "AGAA"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test
    void testNonMutant() {
        String[] dna = {
                "ATGC",
                "CAGT",
                "TTAT",
                "AGAC"
        };
        assertFalse(detector.isMutant(dna));
    }

    @Test
    void testSingleRowNotMutant() {
        String[] dna = {"ATGC"};
        assertFalse(detector.isMutant(dna));
    }

    @Test
    void testSingleColumnNotMutant() {
        String[] dna = {"A", "T", "G", "C"};
        assertFalse(detector.isMutant(dna));
    }

    @Test
    void testMatrix2x2NotMutant() {
        String[] dna = {"AT", "CG"};
        assertFalse(detector.isMutant(dna));
    }

    @Test
    void testDiagonalRightNotMutant() {
        String[] dna = {
                "ATG",
                "CAG",
                "TTT"
        };
        assertFalse(detector.isMutant(dna));
    }

    @Test
    void testDiagonalLeftNotMutant() {
        String[] dna = {
                "TGA",
                "CGT",
                "ACT"
        };
        assertFalse(detector.isMutant(dna));
    }

    @Test
    void testAllSameLetterMutant() {
        String[] dna = {
                "AAAA",
                "AAAA",
                "AAAA",
                "AAAA"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test
    void testLowercaseInputNotMutant() {
        String[] dna = {
                "atgc",
                "cagt",
                "ttat",
                "agac"
        };
        assertFalse(detector.isMutant(dna));
    }

    @Test
    void testInvalidCharactersNotMutant() {
        String[] dna = {
                "ATXG",
                "CAGT",
                "TTAT",
                "AGAC"
        };
        assertFalse(detector.isMutant(dna));
    }

    @Test
    void testEmptyRow() {
        String[] dna = {""};
        assertFalse(detector.isMutant(dna));
    }

    @Test
    void testNullMatrix() {
        assertThrows(NullPointerException.class, () -> detector.isMutant(null));
    }

    @Test
    void testNullRow() {
        String[] dna = {null};
        assertThrows(NullPointerException.class, () -> detector.isMutant(dna));
    }
}

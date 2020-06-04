package crypt.lab1;

import org.junit.Test;

import static org.junit.Assert.*;

import java.math.BigInteger;
import java.security.SecureRandom;

public class MontgomeryArithmenticTest {
    private final SecureRandom random = new SecureRandom();
    private static int numberOfBits = 3;
    private static int numberOfTestCases = 100;

    @Test
    public void testMultiply() {
        for (int i = 0; i < numberOfTestCases; ++i) {
            final BigInteger a = new BigInteger(numberOfBits, random);
            final BigInteger b = new BigInteger(numberOfBits, random);
            final BigInteger mod = BigInteger.probablePrime(numberOfBits, random);
            MontgomeryArithmetic montgomeryReducer = new MontgomeryArithmetic(mod);

            final BigInteger expected = a.multiply(b).mod(mod);
            final BigInteger actual = montgomeryReducer.multiplyConvertMod(a, b);

            assertEquals(expected, actual);
        }
    }

    @Test
    public void testPow() {
        for (int i = 0; i < numberOfTestCases; ++i) {
            final BigInteger number = new BigInteger(numberOfBits, random);
            final BigInteger exp = new BigInteger(numberOfBits, random);
            final BigInteger mod = BigInteger.probablePrime(numberOfBits, random);;
            MontgomeryArithmetic montgomeryReducer = new MontgomeryArithmetic(mod);

            final BigInteger expected = number.modPow(exp, mod);
            final BigInteger actual = montgomeryReducer.powConvertMod(number, exp);
            assertEquals(expected, actual);
        }
    }
}

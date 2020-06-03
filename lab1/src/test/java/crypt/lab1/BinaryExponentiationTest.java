package crypt.lab1;

import org.junit.Test;

import java.math.BigInteger;
import java.security.SecureRandom;

import static org.junit.Assert.*;

public class BinaryExponentiationTest {

    private static SecureRandom random = new SecureRandom();
    private static int numberOfBits = 512;
    private static int numberOfTestCases = 100;
    @Test
    public void isPrimeTest() {
        for (int i = 0;i < numberOfTestCases; ++i) {
            BigInteger value = BigInteger.probablePrime(numberOfBits, random);
            BigInteger exp = BigInteger.probablePrime(numberOfBits, random);
            BigInteger module = BigInteger.probablePrime(numberOfBits, random);

            assertEquals(BinaryExponentiation.pow(value, exp, module), value.modPow(exp, module));
        }
    }
}

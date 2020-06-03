package crypt.lab1;

import org.junit.Test;

import java.math.BigInteger;
import java.security.SecureRandom;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestMillerRabinTest {
    private static SecureRandom random = new SecureRandom();
    private static int numberOfBits = 512;
    private static int numberOfTestCases = 100;
    @Test
    public void isPrimeTest() {
        for (int i = 0;i < numberOfTestCases; ++i) {
            BigInteger probablePrime = BigInteger.probablePrime(numberOfBits, random);
            BigInteger nonPrime = probablePrime.add(BigInteger.ONE);

            assertTrue(TestMillerRabin.isPrime(probablePrime, random));
            assertFalse(TestMillerRabin.isPrime(nonPrime, random));
        }
    }
}

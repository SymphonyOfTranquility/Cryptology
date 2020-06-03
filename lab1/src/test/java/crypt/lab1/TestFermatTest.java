package crypt.lab1;

import org.junit.*;
import static org.junit.Assert.*;

import java.math.BigInteger;
import java.security.SecureRandom;

public class TestFermatTest {
    private static SecureRandom random = new SecureRandom();
    private static int numberOfBits = 512;
    private static int numberOfTestCases = 100;
    @Test
    public void isPrimeTest() {
        for (int i = 0;i < numberOfTestCases; ++i) {
            BigInteger probablePrime = BigInteger.probablePrime(numberOfBits, random);
            BigInteger nonPrime = probablePrime.add(BigInteger.ONE);

            assertTrue(TestFermat.isPrime(probablePrime, random));
            assertFalse(TestFermat.isPrime(nonPrime, random));
        }
    }
}

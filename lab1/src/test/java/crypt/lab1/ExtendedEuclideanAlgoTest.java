package crypt.lab1;

import org.junit.Test;

import java.math.BigInteger;
import java.security.SecureRandom;

import static org.junit.Assert.assertEquals;

public class ExtendedEuclideanAlgoTest {
    private static SecureRandom random = new SecureRandom();
    private static int numberOfBits = 512;
    private static int numberOfTestCases = 100;
    @Test
    public void isPrimeTest() {
        for (int i = 0;i < numberOfTestCases; ++i) {
            BigInteger value1 = new BigInteger(numberOfBits, random);
            BigInteger value2 = new BigInteger(numberOfBits, random);

            BigInteger trueGCD = value1.gcd(value2);
            BigInteger algoGCD = ExtendedEuclideanAlgo.gcd(value1, value2).elementAt(2);

            assertEquals(algoGCD, trueGCD);
        }
    }
}

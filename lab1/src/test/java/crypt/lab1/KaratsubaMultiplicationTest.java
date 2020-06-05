package crypt.lab1;

import org.junit.Test;

import java.math.BigInteger;
import java.security.SecureRandom;

import static org.junit.Assert.assertEquals;

public class KaratsubaMultiplicationTest {
    private static SecureRandom random = new SecureRandom();
    private static int numberOfBits = 512;
    private static int numberOfTestCases = 100;
    @Test
    public void multiTest() {
        for (int i = 0;i < numberOfTestCases; ++i) {
            BigInteger value1 = new BigInteger(numberOfBits, random);
            BigInteger value2 = new BigInteger(numberOfBits, random);

            assertEquals(KaratsubaMultiplication.multi(value1, value2), value1.multiply(value2));
        }
    }
}

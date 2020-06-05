package crypt.lab1;

import org.junit.Test;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Vector;

import static org.junit.Assert.assertEquals;

public class ExtendedEuclideanAlgoTest {
    private static SecureRandom random = new SecureRandom();
    private static int numberOfBits = 512;
    private static int numberOfTestCases = 100;
    @Test
    public void gcdTest() {
        for (int i = 0;i < numberOfTestCases; ++i) {
            BigInteger value1 = new BigInteger(numberOfBits, random);
            BigInteger value2 = new BigInteger(numberOfBits, random);

            BigInteger trueGCD = value1.gcd(value2);
            Vector<BigInteger> ans = ExtendedEuclideanAlgo.gcd(value1, value2);
            BigInteger algoGCD = ans.elementAt(2);

            assertEquals(algoGCD, trueGCD);
            assertEquals(algoGCD, ans.elementAt(0).multiply(value1).add(ans.elementAt(1).multiply(value2)));
        }
    }
}

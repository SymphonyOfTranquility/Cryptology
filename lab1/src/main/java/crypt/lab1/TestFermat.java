package crypt.lab1;

import java.math.BigInteger;
import java.security.SecureRandom;

public class TestFermat {
    private TestFermat() { }

    static public boolean isPrime(BigInteger number, SecureRandom random) {
        if (number.compareTo(BigInteger.ONE) <= 0) {
            return false;
        }
        int numberOfChecks = 128;
        for (int i = 0;i < numberOfChecks; ++i) {
            BigInteger checkVal = BigInteger.ZERO;
            while (checkVal.equals(BigInteger.ZERO) || checkVal.equals(BigInteger.ONE)) {
                checkVal = new BigInteger(number.bitLength(), random);
                checkVal = checkVal.mod(number);
            }

            if (!checkVal.modPow(number.subtract(BigInteger.ONE), number).equals(BigInteger.ONE)) {
                return false;
            }
        }
        return true;
    }
}

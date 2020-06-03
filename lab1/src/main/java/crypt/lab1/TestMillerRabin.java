package crypt.lab1;

import java.math.BigInteger;
import java.security.SecureRandom;

public class TestMillerRabin {
    private TestMillerRabin() {}

    public static boolean isPrime(BigInteger number, SecureRandom random) {
        if (number.compareTo(BigInteger.ONE) <= 0)
            return false;
        if (number.equals(BigInteger.TWO))
            return true;

        BigInteger d = number.subtract(BigInteger.ONE);
        int step = 0;
        while (d.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
            d = d.divide(BigInteger.TWO);
            step += 1;
        }
        if (step == 0)
            return false;
        int numberOfChecks = 128;
        for (int i = 0;i < numberOfChecks; ++i) {
            BigInteger checkVal = BigInteger.ONE;

            while (checkVal.equals(BigInteger.ONE) || checkVal.equals(BigInteger.ZERO) || checkVal.equals(number)) {
                checkVal = new BigInteger(number.bitLength(), random);
            }
            BigInteger nextNumber = new BigInteger(d.toString());
            if (checkVal.modPow(nextNumber, number).equals(BigInteger.ONE))
                continue;

            boolean flag = false;
            for (int j = 0;j < step; ++j) {
                if (checkVal.modPow(nextNumber, number).equals(number.subtract(BigInteger.ONE))) {
                    flag = true;
                    break;
                }
                nextNumber = nextNumber.multiply(BigInteger.TWO);
            }
            if (!flag)
                return false;
        }
        return true;
    }
}

package crypt.lab1;

import java.math.BigInteger;

import static java.lang.Math.min;

public class KaratsubaMultiplication {
    private KaratsubaMultiplication() {}

    private static BigInteger getHalfBegin(BigInteger a, int size) {
        if (a.toString().length() < size)
            return BigInteger.ZERO;
        else
            return new BigInteger(a.toString().substring(0, a.toString().length() - size));
    }

    private static BigInteger getHalfEnd(BigInteger a, int size) {
        return new BigInteger(a.toString().substring(a.toString().length() - size));
    }

    private static BigInteger createWithPow10(BigInteger a, int size) {
        return new BigInteger(a.toString() + "0".repeat(size));
    }

    public static BigInteger multi(BigInteger first, BigInteger second) {
        int x = min(first.toString().length(), second.toString().length())/2 + 1;

        BigInteger a = getHalfBegin(first, x);
        BigInteger b = getHalfEnd(first, x);
        BigInteger c = getHalfBegin(second, x);
        BigInteger d = getHalfEnd(second, x);

        BigInteger ac = a.multiply(c);
        BigInteger bd = b.multiply(d);
        BigInteger a_c_b_d = (a.add(b)).multiply(c.add(d)).subtract(ac).subtract(bd);

        BigInteger acWZeros = createWithPow10(ac, x*x);
        BigInteger xZeros = createWithPow10(a_c_b_d, 2*x);
        return acWZeros.add(xZeros).add(bd);
    }


}

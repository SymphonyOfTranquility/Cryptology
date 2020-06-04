package crypt.lab1;

import java.math.BigInteger;
import java.util.Objects;

public class MontgomeryArithmetic {

    private BigInteger mask;
    private BigInteger factor;
    private BigInteger convertedOne;

    private BigInteger reducer;
    private int reducerBits;
    private BigInteger reciprocal;

    private BigInteger module;

    public MontgomeryArithmetic(BigInteger modulus) {
        Objects.requireNonNull(modulus);
        if (!modulus.testBit(0) || modulus.compareTo(BigInteger.ONE) <= 0)
            throw new IllegalArgumentException("Modulus must be an odd number at least 3");
        this.module = modulus;

        reducerBits = (modulus.bitLength() / 8 + 1) * 8;
        reducer = BigInteger.ONE.shiftLeft(reducerBits);
        mask = reducer.subtract(BigInteger.ONE);
        assert reducer.compareTo(modulus) > 0 && reducer.gcd(modulus).equals(BigInteger.ONE);

        reciprocal = reducer.modInverse(modulus);
        factor = reducer.multiply(reciprocal).subtract(BigInteger.ONE).divide(modulus);
        convertedOne = reducer.mod(modulus);
    }


    public BigInteger convertInMontgomery(BigInteger x) {
        return x.shiftLeft(reducerBits).mod(module);
    }


    public BigInteger convertOutMontgomery(BigInteger x) {
        return x.multiply(reciprocal).mod(module);
    }

    private BigInteger multiplyMod(BigInteger first, BigInteger second) {
        assert first.signum() >= 0 && first.compareTo(module) < 0;
        assert second.signum() >= 0 && second.compareTo(module) < 0;

        BigInteger product = first.multiply(second);
        BigInteger temp = product.and(mask).multiply(factor).and(mask);
        BigInteger reduced = product.add(temp.multiply(module)).shiftRight(reducerBits);
        BigInteger result = reduced.compareTo(module) < 0 ? reduced : reduced.subtract(module);

        assert result.signum() >= 0 && result.compareTo(module) < 0;
        return result;
    }

    public BigInteger multiplyConvertMod(BigInteger first, BigInteger second) {
        final BigInteger convertedFirst = convertInMontgomery(first);
        final BigInteger convertedSecond = convertInMontgomery(second);
        final BigInteger result = multiplyMod(convertedFirst, convertedSecond);
        return convertOutMontgomery(result);
    }

    private BigInteger powMod(BigInteger x, BigInteger y) {
        assert x.signum() >= 0 && x.compareTo(module) < 0;
        if (y.signum() == -1)
            throw new IllegalArgumentException("Negative exponent");

        BigInteger z = convertedOne;
        for (int i = 0, len = y.bitLength(); i < len; i++) {
            if (y.testBit(i))
                z = multiplyMod(z, x);
            x = multiplyMod(x, x);
        }
        return z;
    }

    public BigInteger powConvertMod(BigInteger number, BigInteger exponent) {
        final BigInteger convertedNumber = convertInMontgomery(number);
        final BigInteger result = powMod(convertedNumber, exponent);
        return convertOutMontgomery(result);
    }
}

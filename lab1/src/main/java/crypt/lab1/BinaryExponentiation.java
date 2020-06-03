package crypt.lab1;

import java.math.BigInteger;

public class BinaryExponentiation {

    private BinaryExponentiation() {}

    public static BigInteger pow(BigInteger number, BigInteger exp, BigInteger module) {
        if (exp.equals(BigInteger.ZERO))
            return BigInteger.ZERO;
        number = number.mod(module);
        if (number.equals(BigInteger.ONE) || number.equals(BigInteger.ZERO))
            return number;

        BigInteger result = BigInteger.ONE;
        BigInteger currentExp = new BigInteger(exp.toString());
        while (!currentExp.equals(BigInteger.ZERO)) {
            if (currentExp.mod(BigInteger.TWO).equals(BigInteger.ONE)) {
                result = result.multiply(number).mod(module);
            }
            currentExp.shiftRight(1);
            result = result.multiply(result).mod(module);
        }
        return result;
    }
}

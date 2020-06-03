package crypt.lab1;

import java.math.BigInteger;
import java.util.Vector;

public class ExtendedEuclideanAlgo {
    private ExtendedEuclideanAlgo() {}

    public static Vector<BigInteger> gcd(BigInteger a, BigInteger b) {
        Vector<BigInteger> res = new Vector<>();
        if (a.equals(BigInteger.ZERO)) {
            res.add(BigInteger.ZERO);
            res.add(BigInteger.ONE);
            res.add(new BigInteger(b.toString()));
            return res;
        }
        Vector<BigInteger> ans = gcd(b.mod(a), a);
        BigInteger x1 = ans.elementAt(0);
        BigInteger y1 = ans.elementAt(1);
        BigInteger d = ans.elementAt(2);

        res.add(y1.subtract(b.multiply(x1).divide(a)));
        res.add(x1);
        res.add(d);
        return res;
    }
}

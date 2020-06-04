package crypto.lab3;

public class Adler32Algo {

    private Adler32Algo() {}

    private static long mod = 65521;
    public static long getHash(String data) {
        long a = 1;
        long b = 0;
        for (int i = 0;i < data.length(); ++i) {
            a = (a + (int) data.charAt(i)) % mod;
            b = (a + b) % mod;
        }
        return (b << 16) + a;
    }
}

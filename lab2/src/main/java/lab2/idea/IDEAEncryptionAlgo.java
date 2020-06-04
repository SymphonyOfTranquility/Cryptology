package lab2.idea;


public class IDEAEncryptionAlgo {
    private static final int numberOfRounds = 8;
    private final int[] subKey;

    public IDEAEncryptionAlgo(byte[] password, boolean encrypt) {
        int[] tempSubKey = expandUserKey(password);
        if (encrypt) {
            subKey = tempSubKey;
        }
        else {
            subKey = invertSubKey(tempSubKey);
        }
    }

    public IDEAEncryptionAlgo (String password, boolean encrypt) {
        this(generateUserKeyFromCharPassword(password), encrypt);
    }

    public byte[] encrypt (String data) {
        byte[] bytes = data.getBytes();

        for (int i = 0;i+8 <= bytes.length; ++i)
            crypt(bytes, i);

        return bytes;
    }

    public String decrypt (byte[] bytes) {
        for (int i = bytes.length-8;i >= 0; --i)
            crypt(bytes, i);

        return new String(bytes);
    }



    private void crypt (byte[] data, int dataPos) {
        int x0 = ((data[dataPos + 0] & 0xFF) << 8) | (data[dataPos + 1] & 0xFF);
        int x1 = ((data[dataPos + 2] & 0xFF) << 8) | (data[dataPos + 3] & 0xFF);
        int x2 = ((data[dataPos + 4] & 0xFF) << 8) | (data[dataPos + 5] & 0xFF);
        int x3 = ((data[dataPos + 6] & 0xFF) << 8) | (data[dataPos + 7] & 0xFF);

        int p = 0;
        for (int round = 0; round < numberOfRounds; round++) {
            int y0 = multiply(x0, subKey[p++]);
            int y1 = add(x1, subKey[p++]);
            int y2 = add(x2, subKey[p++]);
            int y3 = multiply(x3, subKey[p++]);

            int t0 = multiply(y0 ^ y2, subKey[p++]);
            int t1 = add(y1 ^ y3, t0);
            int t2 = multiply(t1, subKey[p++]);
            int t3 = add(t0, t2);
            x0 = y0 ^ t2;
            x1 = y2 ^ t2;
            x2 = y1 ^ t3;
            x3 = y3 ^ t3;
        }
        //
        int r0 = multiply(x0, subKey[p++]);
        int r1 = add(x2, subKey[p++]);
        int r2 = add(x1, subKey[p++]);
        int r3 = multiply(x3, subKey[p++]);
        //
        data[dataPos + 0] = (byte)(r0 >> 8);
        data[dataPos + 1] = (byte)r0;
        data[dataPos + 2] = (byte)(r1 >> 8);
        data[dataPos + 3] = (byte)r1;
        data[dataPos + 4] = (byte)(r2 >> 8);
        data[dataPos + 5] = (byte)r2;
        data[dataPos + 6] = (byte)(r3 >> 8);
        data[dataPos + 7] = (byte)r3;
    }

    private static int add (int a, int b) {
        return (a + b) & 0xFFFF;
    }

    private static int addInverse(int x) {
        return (0x10000 - x) & 0xFFFF;
    }

    private static int multiply(int a, int b ) {
        long r = (long)a * b;
        if (r != 0) {
            return (int)(r % 0x10001) & 0xFFFF;
        }
        else {
            return (1 - a - b) & 0xFFFF;
        }
    }

    private static int mulInverse(int x) {
        if (x <= 1) {
            return x;
        }
        int y = 0x10001;
        int t0 = 1;
        int t1 = 0;
        while (true) {
            t1 += y / x * t0;
            y %= x;
            if (y == 1) {
                return 0x10001 - t1;
            }
            t0 += x / y * t1;
            x %= y;
            if (x == 1) {
                return t0;
            }
        }
    }

    private static int[] invertSubKey (int[] key) {
        int[] invKey = new int[key.length];
        int p = 0;
        int i = numberOfRounds * 6;
        invKey[i + 0] = mulInverse(key[p++]);
        invKey[i + 1] = addInverse(key[p++]);
        invKey[i + 2] = addInverse(key[p++]);
        invKey[i + 3] = mulInverse(key[p++]);
        for (int r = numberOfRounds - 1; r >= 0; r--) {
            i = r * 6;
            int m = r > 0 ? 2 : 1;
            int n = r > 0 ? 1 : 2;
            invKey[i + 4] =        key[p++];
            invKey[i + 5] =        key[p++];
            invKey[i + 0] = mulInverse(key[p++]);
            invKey[i + m] = addInverse(key[p++]);
            invKey[i + n] = addInverse(key[p++]);
            invKey[i + 3] = mulInverse(key[p++]);
        }
        return invKey;
    }

    private static int[] expandUserKey (byte[] userKey) {
        if (userKey.length != 16) {
            throw new IllegalArgumentException();
        }
        int[] key = new int[numberOfRounds * 6 + 4];
        for (int i = 0; i < userKey.length / 2; i++) {
            key[i] = ((userKey[2 * i] & 0xFF) << 8) | (userKey[2 * i + 1] & 0xFF);
        }
        for (int i = userKey.length / 2; i < key.length; i++) {
            key[i] = ((key[(i + 1) % 8 != 0 ? i - 7 : i - 15] << 9) | (key[(i + 2) % 8 < 2 ? i - 14 : i - 6] >> 7)) & 0xFFFF;
        }
        return key;
    }

    private static byte[] generateUserKeyFromCharPassword(String charKey) {
        final int minChar = 0x21;
        final int maxChar = 0x7E;
        final int nofChar = maxChar - minChar + 1;    // Number of different valid characters
        int[] a = new int[8];
        for (int p = 0; p < charKey.length(); p++) {
            int c = charKey.charAt(p);
            if (c < minChar || c > maxChar) {
                throw new IllegalArgumentException("Wrong character in key string.");
            }
            int val = c - minChar;
            for (int i = a.length - 1; i >= 0; i--) {
                val += a[i] * nofChar;
                a[i] = val & 0xFFFF;
                val >>= 16;
            }
        }
        byte[] key = new byte[16];
        for (int i = 0; i < 8; i++) {
            key[i * 2] = (byte)(a[i] >> 8);
            key[i * 2 + 1] = (byte)a[i];
        }
        return key;
    }

}

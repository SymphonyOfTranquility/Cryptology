package crypto.lab3;


public class RIPEDM_160 {
    private static final int[] K = {
            0x00000000,
            0x5A827999,
            0x6ED9EBA1,
            0x8F1BBCDC,
            0xA953FD4E
    };

    private static final int[] Kp = {
            0x50A28BE6,
            0x5C4DD124,
            0x6D703EF3,
            0x7A6D76E9,
            0x00000000
    };

    private static final int[] R = {
            0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15,
            7, 4, 13, 1, 10, 6, 15, 3, 12, 0, 9, 5, 2, 14, 11, 8,
            3, 10, 14, 4, 9, 15, 8, 1, 2, 7, 0, 6, 13, 11, 5, 12,
            1, 9, 11, 10, 0, 8, 12, 4, 13, 3, 7, 15, 14, 5, 6, 2,
            4, 0, 5, 9, 7, 12, 2, 10, 14, 1, 3, 8, 11, 6, 15, 13
    };

    private static final int[] Rp = {
            5, 14,  7,  0,  9,  2, 11,  4, 13,  6, 15,  8,  1, 10,  3, 12,
            6, 11,  3,  7,  0, 13,  5, 10, 14, 15,  8, 12,  4,  9,  1,  2,
            15,  5,  1,  3,  7, 14,  6,  9, 11,  8, 12,  2, 10,  0,  4, 13,
            8,  6,  4,  1,  3, 11, 15,  0,  5, 12,  2, 13,  9,  7, 10, 14,
            12, 15, 10,  4,  1,  5,  8,  7,  6,  2, 13, 14,  0,  3,  9, 11
    };

    private static final int[] rol = {
            11, 14, 15, 12, 5, 8, 7, 9, 11, 13, 14, 15, 6, 7, 9, 8,
            7, 6, 8, 13, 11, 9, 7, 15, 7, 12, 15, 9, 11, 7, 13, 12,
            11, 13, 6, 7, 14, 9, 13, 15, 14, 8, 13, 6, 5, 12, 7, 5,
            11, 12, 14, 15, 14, 15, 9, 8, 9, 14, 5, 6, 8, 6, 5, 12,
            9, 15, 5, 11, 6, 8, 13, 12, 5, 12, 13, 14, 11, 8, 5, 6
    };

    private static final int[] rolp = {
            8,  9,  9, 11, 13, 15, 15,  5,  7,  7,  8, 11, 14, 14, 12,  6,
            9, 13, 15,  7, 12,  8,  9, 11,  7,  7, 12,  7,  6, 15, 13, 11,
            9,  7, 15, 11,  8,  6,  6, 14, 12, 13,  5, 14, 13, 13,  7,  5,
            15,  5,  8, 11, 14, 14,  6, 14,  6,  9, 12,  9, 12,  5, 15,  8,
            8,  5, 12,  9, 12,  5, 14,  6,  8, 13,  6,  5, 15, 13, 11, 11
    };

    private static int st_h0 = 0x67452301;
    private static int st_h1 = 0xEFCDAB89;
    private static int st_h2 = 0x98BADCFE;
    private static int st_h3 = 0x10325476;
    private static int st_h4 = 0xC3D2E1F0;

    private static int f(int j, int x, int y, int z) {
        if (j < 16)
            return x^y^z;
        else if (j < 32)
            return (x & y) | (~x & z);
        else if (j < 48)
            return (x | ~y) ^ z;
        else if (j < 64)
            return (x & z) | (y & ~z);
        else
            return x^(y | ~z);
    }

    public static String countHash(String data) {
        byte[] bytes = data.getBytes();

        int mod_val = bytes.length % 64;
        int len = bytes.length;
        if (mod_val > 56) {
            byte[] newBytes = new byte[bytes.length + 64 - mod_val + 64];
            for (int i = 0; i < bytes.length; ++i)
                newBytes[i] = bytes[i];
            bytes = newBytes;
        }
        else {
            byte[] newBytes = new byte[bytes.length + 64 - mod_val];
            for (int i = 0; i < bytes.length; ++i)
                newBytes[i] = bytes[i];
            bytes = newBytes;
        }
        bytes[len] = (byte) 0x80;
        for (int j = len + 1;j < bytes.length; ++j) {
            bytes[j] = 0;
        }
        len = len << 3;
        for (int i = bytes.length-8; i < bytes.length; ++i) {
            bytes[i] = (byte) (len%256l);
            len = len >>> 8;
        }
        int A, B, C, D, E, Ap, Bp, Cp, Dp, Ep, T, s, j;
        int h0 = st_h0, h1 = st_h1, h2 = st_h2, h3 = st_h3, h4 = st_h4;
        for (int i = 0;i < bytes.length/64; ++i) {

            int[] X = new int[16];
            // encode 64 bytes from input block into an array of 16 unsigned integers
            for (j = 0; j < 16; ++j) {
                X[j] = (bytes[i*64 + 4*j] & 0xFF)       |
                        (bytes[i*64 + 4*j+1] & 0xFF) <<  8 |
                        (bytes[i*64 + 4*j + 2] & 0xFF) << 16 |
                        bytes[i*64 + 4*j + 3]         << 24;
            }
            A = Ap = h0;
            B = Bp = h1;
            C = Cp = h2;
            D = Dp = h3;
            E = Ep = h4;

            for (j = 0; j < 80; j++) { // rounds 0...15
                s = rol[j];
                T = A + f(j, B, C, D) + X[R[j]] + K[j/16];
                A = E; E = D; D = C << 10 | C >>> 22; C = B;
                B = (T << s | T >>> (32 - s)) + A;

                s = rolp[j];
                T = Ap + f(79 - j, Bp, Cp, Dp) + X[Rp[j]] + Kp[j/16];
                Ap = Ep; Ep = Dp; Dp = Cp << 10 | Cp >>> 22; Cp = Bp;
                Bp = (T << s | T >>> (32 - s)) + Ap;
            }

            T =  h1 + C + Dp;
            h1 = h2 + D + Ep;
            h2 = h3 + E + Ap;
            h3 = h4 + A + Bp;
            h4 = h0 + B + Cp;
            h0 = T;

        }
        byte[] res = new byte[] {
                (byte) h0, (byte)(h0 >>> 8), (byte)(h0 >>> 16), (byte)(h0 >>> 24),
                (byte) h1, (byte)(h1 >>> 8), (byte)(h1 >>> 16), (byte)(h1 >>> 24),
                (byte) h2, (byte)(h2 >>> 8), (byte)(h2 >>> 16), (byte)(h2 >>> 24),
                (byte) h3, (byte)(h3 >>> 8), (byte)(h3 >>> 16), (byte)(h3 >>> 24),
                (byte) h4, (byte)(h4 >>> 8), (byte)(h4 >>> 16), (byte)(h4 >>> 24)
        };

        String ans = "";
        for (int j1 = 0;j1 < res.length; ++j1) {
            if (res[j1] > 0 && res[j1] <= 15)
                ans += "0" + Integer.toHexString(res[j1] & 0xFF);
            else if (res[j1] == 0)
                ans += "00";
            else
                ans += Integer.toHexString(res[j1] & 0xFF);
        }

        return ans;
    }
}

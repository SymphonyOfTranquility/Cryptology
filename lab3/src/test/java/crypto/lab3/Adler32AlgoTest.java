package crypto.lab3;

import org.junit.Test;
import static org.junit.Assert.*;

public class Adler32AlgoTest {

    @Test
    public void testAdler() {
        String input = "Wikipedia";
        long ans = 300286872l;
        assertEquals(ans, Adler32Algo.getHash(input));

        input = "Cat walked on the keyboard.";
        ans = 2252212654l;
        assertEquals(ans, Adler32Algo.getHash(input));
    }


}

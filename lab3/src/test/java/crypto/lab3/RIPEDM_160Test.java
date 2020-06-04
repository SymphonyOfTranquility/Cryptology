package crypto.lab3;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RIPEDM_160Test {

    @Test
    public void testRIPEDM() {
        String input = "";//""The quick brown fox jumps over the lazy dog";

        String hash = "9c1185a5c5e9fc54612808977ee8f548b2258d31";
        assertEquals(hash, RIPEDM_160.countHash(input));

        input = "The quick brown fox jumps over the lazy dog";
        hash = "37f332f68db77bd9d7edd4969571ad671cf9dd3b";
        assertEquals(hash, RIPEDM_160.countHash(input));

        input = "The quick brown fox jumps over the lazy cog";
        hash = "132072df690933835eb8b6ad0b77e7b6f14acad7";
        assertEquals(hash, RIPEDM_160.countHash(input));

        input = "a";
        hash = "0BDC9D2D256B3EE9DAAE347BE6F4DC835A467FFE".toLowerCase();
        assertEquals(hash, RIPEDM_160.countHash(input));

        input = "12345678901234567890123456789012345678901234567890123456789012345678901234567890";
        hash = "9B752E45573D4B39F4DBD3323CAB82BF63326BFB".toLowerCase();
        assertEquals(hash, RIPEDM_160.countHash(input));
    }

}

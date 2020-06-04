package lab2.idea;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class IDEAEnctyptionAlgoTest {

    @Test
    public void test() {
        String key = "catsarebeautiful";
        String text = "Cat has been sitting on the window all day long.";

        IDEAEncryptionAlgo idea = new IDEAEncryptionAlgo(key, true);
        byte[] encrypted = idea.encrypt(text);
        //assertEquals(text, new String(bytes));
        idea = new IDEAEncryptionAlgo(key, false);

        String decrypted = idea.decrypt(encrypted);

        assertEquals(text, decrypted);

    }
}

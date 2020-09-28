package id.ac.ui.cs.mobileprogramming.rasikaayuningtyas.helloworld;

import org.junit.Test;


import static org.junit.Assert.*;

public class MainActivityTest {
    MainActivity main = new MainActivity();

    @Test
    public void input_isEmpty() {
        String input = "";
        assertFalse(main.register(input));
    }
}
import org.junit.Test;

import static org.junit.Assert.*;

public class CheckArrayTest {

    @Test
    public void checkArray() {
        CheckArray check= new CheckArray();
        assertTrue(check.checkArray(new int[]{1,2,3,4}));
        assertFalse(check.checkArray(new int[]{5,2,3,8}));

        boolean expected = true;
        assertEquals(check.checkArray(new int[]{1,0,1,0}),expected);
    }
}
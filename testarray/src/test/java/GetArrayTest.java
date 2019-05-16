import static org.junit.Assert.*;

public class GetArrayTest {

    @org.junit.Test
    public void getArray() throws RuntimeException {
        GetArray getArray = new GetArray();

        int[] result = getArray.getArray(new int[]{1, 4, 5, 7, 8, 9, 5});
        assertTrue("Длина возвращенного массива не равна нулю", result.length != 0);

        assertFalse("Длина возвращенного массива не равна нулю",result.length == 0);

        int[] actual = getArray.getArray(new int[]{1, 2, 4, 4, 2, 3, 4, 1, 7});
        int[] expected = new int[]{1,7};
        assertArrayEquals(expected, actual);

    }
}
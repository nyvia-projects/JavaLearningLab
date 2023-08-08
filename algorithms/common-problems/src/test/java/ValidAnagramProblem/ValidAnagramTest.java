package ValidAnagramProblem;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ValidAnagramTest {


    @Test
    void shouldReturnValidAnagramProblemInstance() {
        ValidAnagram underTest = ValidAnagram.getValidAnagramProblemInstance("moon", "noom");
        assertNotNull(underTest);
    }

    @Test
    void shouldReturnNullFor_Null_Empty_Blank_Inputs() {
        ValidAnagram underTest = ValidAnagram.getValidAnagramProblemInstance(null,null);
        assertNull(underTest);
        ValidAnagram underTest2 = ValidAnagram.getValidAnagramProblemInstance("","");
        assertNull(underTest2);
        ValidAnagram underTest3 = ValidAnagram.getValidAnagramProblemInstance(" "," ");
        assertNull(underTest3);
    }


    @Test
    void shouldSetMapKeyValuesFromStringArgument() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        HashMap<Character, Integer> expected = new HashMap<>();
        HashMap<Character, Integer> actual = new HashMap<>();
        expected.put('m', 1);
        expected.put('o', 2);
        expected.put('n', 1);

        ValidAnagram underTest = ValidAnagram.getValidAnagramProblemInstance("test", "tset");

        Method method = ValidAnagram.class.getDeclaredMethod("setMap", String.class, HashMap.class);
        method.setAccessible(true);

        method.invoke(underTest, "moon", actual);

        assertEquals(expected,actual);

    }

    @Test
    void areValidAnagramShouldReturnTrue() {
        var underTest = ValidAnagram.getValidAnagramProblemInstance("moon", "noom");
        assert underTest != null;
        assertTrue(underTest.areAnagrams());

    }

    @Test
    void areValidAnagramsAndShouldReturnTrue() {
        var underTestWithValidAnagrams = ValidAnagram.getValidAnagramProblemInstance("astronomers", "noMoreStars");
        assert underTestWithValidAnagrams != null;
        assertTrue(underTestWithValidAnagrams.areAnagrams());
    }

    @Test
    void lengthsAreWrongAndShouldReturnFalse() {
        var underTestWithWrongLengths = ValidAnagram.getValidAnagramProblemInstance("moon", "nooma");
        assert underTestWithWrongLengths != null;
        assertFalse(underTestWithWrongLengths.areAnagrams());
    }

    @Test
    void isInvalidAnagramAndShouldReturnFalse() {
        var underTestWithInvalidAnagrams = ValidAnagram.getValidAnagramProblemInstance("random", "mndor");
        assert underTestWithInvalidAnagrams != null;
        assertFalse(underTestWithInvalidAnagrams.areAnagrams());
    }

    @Test
    void isInvalidAnagramAndShouldReturnFalse2() {
        var underTestWithInvalidAnagrams = ValidAnagram.getValidAnagramProblemInstance("dad", "ddd");
        assert underTestWithInvalidAnagrams != null;
        assertFalse(underTestWithInvalidAnagrams.areAnagrams());
    }
}
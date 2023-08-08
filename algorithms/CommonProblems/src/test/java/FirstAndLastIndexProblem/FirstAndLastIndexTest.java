package FirstAndLastIndexProblem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class FirstAndLastIndexTest {

    static ArrayList<Integer> ary = new ArrayList<>();
    static int target;

    @BeforeAll
    static void beforeAll() {
        IntStream.of(0, 3, 4, 5, 5, 5, 5, 5, 7, 7, 9, 12).forEach(ary::add);
        target = 5;
    }

    @Test
    void shouldReturnNotNullInstance() {
        var underTest = new FirstAndLastIndex(target, ary);
        assertNotNull(underTest);
    }

    @Test
    void shouldReturnFirstAndLastIndexes() {
        var underTest = new FirstAndLastIndex(target, ary);
        int[] expected = {3, 7};
        int[] actual = underTest.getFirstAndLastIndexWithLinearSearch();
        assertArrayEquals(expected, actual);

    }

    @Test
    void shouldReturnSameIndices() {
        var underTest = new FirstAndLastIndex(9, ary);
        int[] expected = {10, 10};
        int[] actual = underTest.getFirstAndLastIndexWithLinearSearch();
        assertArrayEquals(expected, actual);

    }

    @Test
    void shouldReturnIndicesAndAssertFalse() {
        var underTest = new FirstAndLastIndex(1, ary);
        int[] expected = {0, 1};
        int[] actual = underTest.getFirstAndLastIndexWithLinearSearch();
        assertFalse(Arrays.equals(expected, actual));

    }

    @Test
    void shouldReturnDefaultIndices() {
        var underTest = new FirstAndLastIndex(1, ary);
        int[] expected = {-1, -1};
        int[] actual = underTest.getFirstAndLastIndexWithLinearSearch();
        assertArrayEquals(expected, actual);
    }
}
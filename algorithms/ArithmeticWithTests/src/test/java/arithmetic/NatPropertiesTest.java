package arithmetic;

import com.pholser.junit.quickcheck.From;
import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.generator.Size;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(JUnitQuickcheck.class)
public class NatPropertiesTest {
    // a == a
    @Property
    public void numberEqualsSelf(@From(NatGenerator.class) @Size(max = 10) final Nat a) {
        assertEquals(a, a);
    }

    @Property
    public void toStringAsExpected(@From(NatGenerator.class) @Size(max = 10) final Nat a) {
        int val = Math.toIntExact(a.toString()
                .chars()
                .filter(ch -> ch == 'S')
                .count());
        String base = "S(";
        String end = ")";
        String expected = base.repeat(val) + "0" + end.repeat(val);
        assertEquals(expected, a.toString());
    }

    @Property
    public void zeroIsNotNonZero(@From(NatGenerator.class) @Size(max = 10) final Nat a) {
        int val = Math.toIntExact(a.toString()
                .chars()
                .filter(ch -> ch == 'S')
                .count());
        if (val != 1)
            assertEquals(false, a.isOne());
        else
            assertEquals(true, a.isOne());
    }

    @Property
    public void natIsNotEqualOtherTypes(@From(NatGenerator.class) @Size(max = 10) final Nat a) {
        assertFalse(a.equals(new Object()));
    }

    

    @Property
    public void shouldFailEquals(@From(NatGenerator.class) @Size(max = 10) final Nat one,
                                   @From(NatGenerator.class) @Size(max = 10) final Nat two) {
        int val1 = Math.toIntExact(one.toString()
                .chars()
                .filter(ch -> ch == 'S')
                .count());
        int val2 = Math.toIntExact(two.toString()
                .chars()
                .filter(ch -> ch == 'S')
                .count());

        if (val1 != val2)
            assertFalse(one.equals(two));
        else
            numberEqualsSelf(one);

    }

    @Property
    public void additionValueAsExpected(@From(NatGenerator.class) @Size(max = 10) final Nat one,
                                        @From(NatGenerator.class) @Size(max = 10) final Nat two) {
        int val1 = Math.toIntExact(one.toString()
                .chars()
                .filter(ch -> ch == 'S')
                .count());
        int val2 = Math.toIntExact(two.toString()
                .chars()
                .filter(ch -> ch == 'S')
                .count());

        int val = val1 + val2;

        String base = "S(";
        String end = ")";
        String expected = base.repeat(val) + "0" + end.repeat(val);
        assertEquals(expected, one.add(two)
                .toString());
    }

    @Property
    public void subtract0asExpected(@From(NatGenerator.class) @Size(max = 10) final Nat one) {

        Nat zero = new Zero();
        assertEquals(one, one.subtract(zero));
    }

    @Property
    public void add0asExpected(@From(NatGenerator.class) @Size(max = 10) final Nat one) {
        Nat zero = new Zero();
        assertEquals(one, one.add(zero));
    }

    @Property
    public void multiply0asExpected(@From(NatGenerator.class) @Size(max = 10) final Nat one) {
        Nat zero = new Zero();
        assertEquals(zero, one.multiply(zero));
    }

    @Property
    public void subtractionValueAsExpected(@From(NatGenerator.class) @Size(max = 10) final Nat one,
                                           @From(NatGenerator.class) @Size(max = 10) final Nat two) {
        int val1 = Math.toIntExact(one.toString()
                .chars()
                .filter(ch -> ch == 'S')
                .count());
        int val2 = Math.toIntExact(two.toString()
                .chars()
                .filter(ch -> ch == 'S')
                .count());
        if (val1 < val2) assertTrue(true);
        else {
            int val = val1 - val2;

            String base = "S(";
            String end = ")";
            String expected = base.repeat(val) + "0" + end.repeat(val);
            assertEquals(expected, one.subtract(two)
                    .toString());
        }
    }

    @Property
    public void multiplicationValueAsExpected(@From(NatGenerator.class) @Size(max = 10) final Nat one,
                                              @From(NatGenerator.class) @Size(max = 10) final Nat two) {
        int val1 = Math.toIntExact(one.toString()
                .chars()
                .filter(ch -> ch == 'S')
                .count());
        int val2 = Math.toIntExact(two.toString()
                .chars()
                .filter(ch -> ch == 'S')
                .count());

        int val = val1 * val2;

        String base = "S(";
        String end = ")";
        String expected = base.repeat(val) + "0" + end.repeat(val);
        assertEquals(expected, one.multiply(two)
                .toString());
    }

    @Property
    public void comparisonAsExpected(@From(NatGenerator.class) @Size(max = 10) final Nat one,
                                     @From(NatGenerator.class) @Size(max = 10) final Nat two) {
        int val1 = Math.toIntExact(one.toString()
                .chars()
                .filter(ch -> ch == 'S')
                .count());
        int val2 = Math.toIntExact(two.toString()
                .chars()
                .filter(ch -> ch == 'S')
                .count());

        if (val1 < val2)
            assertTrue(one.lessThan(two));
        else
            assertFalse(one.lessThan(two));
    }
}



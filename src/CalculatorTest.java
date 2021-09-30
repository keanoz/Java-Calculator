import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CalculatorTest {
    @Test
    void testSimpleExpressions() throws Exception {
        assertEquals(0, Calculator.calculate("0"));
        assertEquals(1, Calculator.calculate("1"));
        assertEquals(3, Calculator.calculate("1+2"));
        assertEquals(1, Calculator.calculate("1+0"));
        assertEquals(1, Calculator.calculate("3-2"));
        assertEquals(3, Calculator.calculate("3-0"));
        assertEquals(6, Calculator.calculate("2*3"));
        assertEquals(0, Calculator.calculate("2*0"));
        assertEquals(-1, Calculator.calculate("2/0"));
        assertEquals(0, Calculator.calculate("0/2"));
        assertEquals(1, Calculator.calculate("3/2"));
        assertEquals(3, Calculator.calculate("6/2"));
        assertEquals(3, Calculator.calculate("6/2/1"));
        assertEquals(1, Calculator.calculate("6/2/1/3"));

    }
}

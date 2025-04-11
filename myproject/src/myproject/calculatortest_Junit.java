package myproject;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class calculatortest_Junit {

    @Test
    void testAdd() {
        calculator calc = new calculator();
        int result = calc.add(10, 5);
        Assertions.assertEquals(15, result, "Addition should return 15");
    }

    @Test
    void testSub() {
        calculator calc = new calculator();
        int result = calc.sub(10, 5);
       // assertEquals(5, result, "Subtraction should return 5");
        Assertions.assertEquals(5, result);
    }

	
}

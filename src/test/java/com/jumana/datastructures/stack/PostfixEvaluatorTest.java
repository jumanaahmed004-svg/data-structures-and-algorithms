package com.jumana.datastructures.stack;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PostfixEvaluatorTest {

    @Test
    void simpleAddition() {
        assertEquals(7, PostfixEvaluator.evaluate("3 4 +"));
    }

    @Test
    void longerExpression() {
        // 5 + ((1 + 2) * 4) - 3 = 14
        assertEquals(14, PostfixEvaluator.evaluate("5 1 2 + 4 * + 3 -"));
    }

    @Test
    void operandOrderMattersForMinusAndDivide() {
        assertEquals(6, PostfixEvaluator.evaluate("10 4 -"));
        assertEquals(5, PostfixEvaluator.evaluate("20 4 /"));
    }

    @Test
    void handlesNegativeNumbersAndExtraSpaces() {
        assertEquals(-8, PostfixEvaluator.evaluate("  -2   4 * "));
    }

    @Test
    void singleNumber() {
        assertEquals(42, PostfixEvaluator.evaluate("42"));
    }

    @Test
    void malformedExpressionsThrow() {
        assertThrows(IllegalArgumentException.class, () -> PostfixEvaluator.evaluate(""));
        assertThrows(IllegalArgumentException.class, () -> PostfixEvaluator.evaluate("+"));
        assertThrows(IllegalArgumentException.class, () -> PostfixEvaluator.evaluate("1 2"));
        assertThrows(IllegalArgumentException.class, () -> PostfixEvaluator.evaluate("1 2 %"));
    }

    @Test
    void divisionByZeroThrows() {
        assertThrows(ArithmeticException.class, () -> PostfixEvaluator.evaluate("1 0 /"));
    }
}

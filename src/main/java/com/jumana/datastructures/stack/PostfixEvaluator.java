package com.jumana.datastructures.stack;

/**
 * Evaluates integer postfix (Reverse Polish Notation) expressions using {@link ArrayStack}.
 *
 * <p>Example: {@code "5 1 2 + 4 * + 3 -"} evaluates to {@code 14}.
 *
 * <p>How it works: read tokens left to right. A number is pushed onto the stack.
 * An operator pops two numbers ({@code b} first, then {@code a}), computes {@code a op b},
 * and pushes the result. At the end, exactly one number must remain: the answer.
 *
 * <p>Supported operators: {@code + - * /} (integer division). Time complexity: O(n) in the
 * number of tokens.
 */
public final class PostfixEvaluator {

    private PostfixEvaluator() {
        // utility class: not meant to be instantiated
    }

    /**
     * Evaluates a postfix expression whose tokens are separated by spaces.
     *
     * @param expression e.g. {@code "3 4 +"}
     * @return the result of the expression
     * @throws IllegalArgumentException if the expression is empty or malformed
     * @throws ArithmeticException if the expression divides by zero
     */
    public static int evaluate(String expression) {
        if (expression == null || expression.isBlank()) {
            throw new IllegalArgumentException("Expression is empty");
        }

        ArrayStack<Integer> stack = new ArrayStack<>();

        for (String token : expression.trim().split("\\s+")) {
            if (token.matches("-?\\d+")) {
                stack.push(Integer.parseInt(token));
                continue;
            }
            if (stack.size() < 2) {
                throw new IllegalArgumentException("Not enough operands for '" + token + "'");
            }
            int b = stack.pop();
            int a = stack.pop();
            stack.push(apply(token, a, b));
        }

        if (stack.size() != 1) {
            throw new IllegalArgumentException("Malformed expression: too many operands");
        }
        return stack.pop();
    }

    private static int apply(String operator, int a, int b) {
        return switch (operator) {
            case "+" -> a + b;
            case "-" -> a - b;
            case "*" -> a * b;
            case "/" -> a / b;
            default -> throw new IllegalArgumentException("Unknown operator: " + operator);
        };
    }
}

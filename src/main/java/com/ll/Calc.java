package com.ll;

public class Calc {
    public static int run(String expression) {
        String[] tokens = expression.trim().split("\\s+");
        int firstNumber = Integer.parseInt(tokens[0]);

        return eval(tokens, 1, firstNumber);
    }

    private static int eval(String[] tokens, int idx, int acc) {
        if (idx >= tokens.length) {
            return acc;
        }

        String op = tokens[idx];
        int nextNumber = Integer.parseInt(tokens[idx + 1]);

        if ("+".equals(op)) {
            return eval(tokens, idx + 2, acc + nextNumber);
        }

        if ("-".equals(op)) {
            return eval(tokens, idx + 2, subtract(acc, nextNumber));
        }

        if ("*".equals(op)) {
            return eval(tokens, idx + 2, multiply(acc, nextNumber));
        }

        throw new IllegalArgumentException(op);
    }

    private static int subtract(int num1, int num2) {
        if (num2 == 0) {
            return num1;
        }

        if (num2 > 0) {
            return subtract(num1 - 1, num2 - 1);
        }

        return subtract(num1 + 1, num2 + 1);
    }

    private static int multiply(int num1, int num2) {
        if (num2 == 0) {
            return 0;
        }

        if (num2 > 0) {
            return num1 + multiply(num1, num2 - 1);
        }

        return subtract(0, multiply(num1, subtract(0, num2)));
    }
}

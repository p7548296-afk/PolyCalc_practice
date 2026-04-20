package com.ll;

public class Calc {
    public static int run(String expression) {
        String trimmed = expression.trim();
        if (trimmed.isEmpty()) {
            return 0;
        }

        String[] tokens = trimmed.split("\\s+");
        int[] idx = {0};
        int result = parseExpression(tokens, idx);

        if (idx[0] != tokens.length) {
            throw new IllegalArgumentException("\uC9C0\uC6D0\uD558\uC9C0 \uC54A\uB294 \uC5F0\uC0B0\uC790: " + tokens[idx[0]]);
        }

        return result;
    }

    private static int parseExpression(String[] tokens, int[] idx) {
        int left = parseTerm(tokens, idx);
        return parseExpressionTail(tokens, idx, left);
    }

    private static int parseExpressionTail(String[] tokens, int[] idx, int acc) {
        if (idx[0] >= tokens.length) {
            return acc;
        }

        String op = tokens[idx[0]];

        if ("+".equals(op)) {
            idx[0]++;
            int right = parseTerm(tokens, idx);
            return parseExpressionTail(tokens, idx, acc + right);
        }

        if ("-".equals(op)) {
            idx[0]++;
            int right = parseTerm(tokens, idx);
            return parseExpressionTail(tokens, idx, subtract(acc, right));
        }

        return acc;
    }

    private static int parseTerm(String[] tokens, int[] idx) {
        int left = parseFactor(tokens, idx);
        return parseTermTail(tokens, idx, left);
    }

    private static int parseTermTail(String[] tokens, int[] idx, int acc) {
        if (idx[0] >= tokens.length) {
            return acc;
        }

        String op = tokens[idx[0]];

        if ("*".equals(op)) {
            idx[0]++;
            int right = parseFactor(tokens, idx);
            return parseTermTail(tokens, idx, multiply(acc, right));
        }

        return acc;
    }

    private static int parseFactor(String[] tokens, int[] idx) {
        if (idx[0] >= tokens.length) {
            throw new IllegalArgumentException("Invalid expression");
        }

        int value = Integer.parseInt(tokens[idx[0]]);
        idx[0]++;

        return value;
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

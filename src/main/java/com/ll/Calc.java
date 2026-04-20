package com.ll;

public class Calc {
    public static int run(String expression) {
        String normalized = removeSpaces(expression, 0);
        if (normalized.isEmpty()) {
            return 0;
        }

        int[] idx = {0};
        int result = parseExpression(normalized, idx);

        if (idx[0] != normalized.length()) {
            throw new IllegalArgumentException("\uC9C0\uC6D0\uD558\uC9C0 \uC54A\uB294 \uC5F0\uC0B0\uC790: " + normalized.charAt(idx[0]));
        }

        return result;
    }

    private static String removeSpaces(String expression, int idx) {
        if (idx >= expression.length()) {
            return "";
        }

        char c = expression.charAt(idx);
        String tail = removeSpaces(expression, idx + 1);

        if (Character.isWhitespace(c)) {
            return tail;
        }

        return c + tail;
    }

    private static int parseExpression(String expression, int[] idx) {
        int left = parseTerm(expression, idx);
        return parseExpressionTail(expression, idx, left);
    }

    private static int parseExpressionTail(String expression, int[] idx, int acc) {
        if (idx[0] >= expression.length()) {
            return acc;
        }

        char op = expression.charAt(idx[0]);

        if (op == '+') {
            idx[0]++;
            int right = parseTerm(expression, idx);
            return parseExpressionTail(expression, idx, acc + right);
        }

        if (op == '-') {
            idx[0]++;
            int right = parseTerm(expression, idx);
            return parseExpressionTail(expression, idx, subtract(acc, right));
        }

        if (op == ')') {
            return acc;
        }

        throw new IllegalArgumentException("\uC9C0\uC6D0\uD558\uC9C0 \uC54A\uB294 \uC5F0\uC0B0\uC790: " + op);
    }

    private static int parseTerm(String expression, int[] idx) {
        int left = parseFactor(expression, idx);
        return parseTermTail(expression, idx, left);
    }

    private static int parseTermTail(String expression, int[] idx, int acc) {
        if (idx[0] >= expression.length()) {
            return acc;
        }

        char op = expression.charAt(idx[0]);

        if (op == '*') {
            idx[0]++;
            int right = parseFactor(expression, idx);
            return parseTermTail(expression, idx, multiply(acc, right));
        }

        if (op == '+' || op == '-' || op == ')') {
            return acc;
        }

        throw new IllegalArgumentException();
    }

    private static int parseFactor(String expression, int[] idx) {
        if (idx[0] >= expression.length()) {
            throw new IllegalArgumentException("Invalid expression");
        }

        char current = expression.charAt(idx[0]);

        if (current == '-') {
            idx[0]++;
            return subtract(0, parseFactor(expression, idx));
        }

        if (current == '(') {
            idx[0]++;
            int value = parseExpression(expression, idx);

            if (idx[0] >= expression.length() || expression.charAt(idx[0]) != ')') {
                throw new IllegalArgumentException("Invalid expression");
            }

            idx[0]++;
            return value;
        }

        return parseNumber(expression, idx);
    }

    private static int parseNumber(String expression, int[] idx) {
        if (idx[0] >= expression.length() || !Character.isDigit(expression.charAt(idx[0]))) {
            throw new IllegalArgumentException("Invalid number");
        }

        int value = parseDigits(expression, idx, 0);

        return value;
    }

    private static int parseDigits(String expression, int[] idx, int acc) {
        if (idx[0] >= expression.length()) {
            return acc;
        }

        char c = expression.charAt(idx[0]);
        if (!Character.isDigit(c)) {
            return acc;
        }

        idx[0]++;
        int next = acc * 10 + (c - '0');
        return parseDigits(expression, idx, next);
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

package com.ll;

public class Calc {
    public static int run(String expression) {
        String[] expressionBits = expression.split(" ");

        int result = Integer.parseInt(expressionBits[0]);

        for (int i = 1; i < expressionBits.length; ) {
            String op = expressionBits[i];
            int num = Integer.parseInt(expressionBits[i + 1]);
            i += 2;

            while (i < expressionBits.length && "*".equals(expressionBits[i])) {
                num *= Integer.parseInt(expressionBits[i + 1]);
                i += 2;
            }

            if ("+".equals(op)) {
                result += num;
            } else if ("-".equals(op)) {
                result -= num;
            } else {
                result *= num;
            }
        }

        return result;
    }
}

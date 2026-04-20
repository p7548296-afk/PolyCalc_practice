package com.ll;

public class Calc {
    public static int run(String expression) {
        expression = expression.trim();

        while (expression.contains("(")) {
            int open = expression.lastIndexOf('(');
            int close = expression.indexOf(')', open);
            int value = evalFlat(expression.substring(open + 1, close));
            expression = expression.substring(0, open) + value + expression.substring(close + 1);
        }

        return evalFlat(expression);
    }

    private static int evalFlat(String expression) {
        String[] expressionBits = expression.trim().split("\\s+");
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

import java.util.List;
import java.util.ArrayList;

public class Expression {
    private String expression;
    private int expressionLength;
    private List<Expression> expressionParts = new ArrayList<>();
    private char splitBy;

    public Expression(String expression) {
        this.expression = cleanUp(expression);
        this.expressionLength = this.expression.length();
        this.expressionParts = splitExpression();
    }

    public List<Expression> getExpressionParts() {
        return expressionParts;
    }

    private int parenCount(int prevCount, char currentChar) {
        int newCount = prevCount;
        if (currentChar == '(')
            newCount++;
        else if (currentChar == ')')
            newCount--;
        return newCount;
    }

    private String cleanUp(String expression) {
        int tempExpressionLength = expression.length();
        int unclosedParentheses = 0;

        for (int index = 0; index < tempExpressionLength; index++) {
            unclosedParentheses = parenCount(unclosedParentheses, expression.charAt(index));
            if (unclosedParentheses < 0) {
                return "(" + expression + ")";
            }
        }
        if (expression.charAt(0) == '(' && tempExpressionLength > 2 &&
                expression.charAt(tempExpressionLength-1) == ')' )
            return cleanUp(expression.substring(1, tempExpressionLength-1));

        return expression;
    }

    private List<Expression> splitExpression() {
        List<Integer> closedIndexes = new ArrayList<>();
        List<Character> closedCharacters = new ArrayList<>();
        boolean stopSplitCheck = false;
        int unclosedParentheses = 0;

        for (int index = 0; index < expressionLength; index++) {
            char currentChar = expression.charAt(index);
            unclosedParentheses = parenCount(unclosedParentheses, currentChar);
            if (unclosedParentheses == 0 && index != expressionLength - 1) {
                closedIndexes.add(index + 1);
                char nextChar = expression.charAt(index + 1);
                closedCharacters.add(nextChar);
                if (!stopSplitCheck)
                    stopSplitCheck = findSplit(currentChar, nextChar);
            }
        }
        if (!(closedCharacters.contains('+') || closedCharacters.contains('^') || closedCharacters.contains('(')))
            return null;

        List<Expression> expressionParts = splitTerms(closedIndexes, closedCharacters);
        return expressionParts;
    }

    private boolean findSplit(char currentChar, char nextChar) {
        switch (nextChar) {
            case '+':
                splitBy = nextChar;
                return true;
            case '^':
                splitBy = nextChar;
                return true;
            case '(':
                if (currentChar != ')' && splitBy != 'm') {
                    splitBy = nextChar;
                    return false;
                }
            default:
                splitBy = 'm';
                return false;
        }
    }

    private List<Expression> splitTerms(List<Integer> closedIndexes, List<Character> closedCharacters) {
        List<Expression> terms = new ArrayList<>();
        int lastSplitIndex = 0;
        int index = 0;
        for (; index < closedIndexes.size(); index++) {
            if (closedCharacters.get(index) == '+') {
                int splitIndex = closedIndexes.get(index);
                terms.add(new Term(expression.substring(lastSplitIndex, splitIndex)));
                lastSplitIndex = splitIndex + 1;
            }
        }
        terms.add(new Term(expression.substring(lastSplitIndex, expressionLength)));
        return terms;
    }

    public List<Expression> printParts() {

    }

    private List<Expression> splitFactors(List<Integer> closedIndexes, List<Character> closedCharacters) {
        return new ArrayList<>();
    }

    private List<Expression> splitOperation(List<Integer> closedIndexes, List<Character> closedCharacters) {
        return new ArrayList<>();
    }

    public class Term {
        public Term(String expression) {

        }

        public List<Expression> differentiate() {
            return null;
        }
    }
}

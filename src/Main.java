import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        System.out.println("This is the Derivative Calculator. " +
                "Please make sure everything is correct before pressing enter, " +
                "or you may encounter \na problem. Also, try to format it as simply and " +
                "normally as you can without spaces. Factoring is completely optional. \n\n" +
                "Type the equation you want to differentiate here, using x as the variable:\n");

        String splittable = reader.nextLine().replace('[', '(').replace(']', ')')
                .replaceAll("-","+-").replaceAll("\\++", "+");
        final long startTime = System.currentTimeMillis();
        Expression expression = new Expression(splittable);
        //System.out.println(expression.differentiate());
        final long endTime = System.currentTimeMillis();
        System.out.println("\nTotal execution time: " + (endTime - startTime) + " ms");
    }
}

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws InterruptedException {
        SquareMatrix squareMatrix = createSquareMatrix();
        //squareMatrix.displayArray(); does not show at start to avoid cheating

        Thread.sleep(1000);
        System.out.println("Shuffling Array:");
        Thread.sleep(1000);

        squareMatrix.shuffleMatrix();
        squareMatrix.displayArray();

        Thread.sleep(1000);

        // begins the loop where it asks for a swap each turn
        askForSwap(squareMatrix);
    }


    private static SquareMatrix createSquareMatrix() {
        // This is the method for the input odd number which generates a square matrix

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Enter number for matrix size (must be odd) \n>> ");

            int oddNumber;

            try {
                oddNumber = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("That is not a number!");
                // If the input is not a number
                scanner.next();
                continue; // Restart the loop
            }

            if (oddNumber % 2 == 0) {
                System.out.println("That is not an odd number!");
                // if the number is not odd
                continue; // Restart the loop
            }

            // instantiate a new square matrix
            SquareMatrix squareMatrix = new SquareMatrix(oddNumber);
            squareMatrix.populate();
            return squareMatrix;
        }
    }


    private static void askForSwap(SquareMatrix squareMatrix) {

        Scanner scanner = new Scanner(System.in);

        while (!squareMatrix.isMagicSquare()) {
            System.out.println("Enter code to swap items (e.g move 2 1 D) \n>> ");

            String input = scanner.nextLine();
            // gets the full line

            String[] inputValues = input.split(" ");
            // splits the input command into spaces


            if (!inputValues[0].equalsIgnoreCase("move")) {
                // if the first word is not move
                System.out.println("The command must start with 'move'!");
                continue;
            }

            if (inputValues.length < 4) {
                // if they have not used correct syntax
                System.out.println("Invalid syntax!");
                continue;
            }

            int row;
            int column;

            try {
                row = Integer.parseInt(inputValues[1]);
                column = Integer.parseInt(inputValues[2]);
            }
            catch (NumberFormatException e) {
                // if the provided 2nd and 3rd arg are not numbers
                System.out.println("Invalid number provided!");
                scanner.next();
                continue;
            }

            // gets the direction from the tag e.g. "D"
            Direction direction = Direction.getDirectionFromTag(inputValues[3]);

            if (direction == null) {
                System.out.println("Invalid direction! (Valid: U, D, L, R)");
                continue;
            }

            if (squareMatrix.swapItems(row, column, direction)) {
                // only runs if the swap is successful
                System.out.println("Successfully swapped items!");
                squareMatrix.checkMagicSquare();
                // updates for if it is magic or not
                squareMatrix.displayArray();
                // re displays for user
            }

            else {
                // if the swap was not successful
                System.out.println("There is no item to swap with for item at " + row + ", " + column + " going : " + direction.name().toLowerCase());
            }

            if (squareMatrix.isMagicSquare()) {
                System.out.println("Magic Square has been solved in " + squareMatrix.getTurnsToSolve() + " turns!");
                // game over
                break;
            }
        }
    }
}
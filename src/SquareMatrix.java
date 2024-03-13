import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SquareMatrix {
    private final int n;
    private final int[][] array;
    private int turnsToSolve;
    private boolean isMagicSquare = false;
    Random random = new Random();
    public SquareMatrix(int n) {
        this.n = n;
        // instantiates new array
        this.array = new int[n][n];
        // fills all columns and rows with 0s
        fillArrayWithZeros();
    }

    public void populate() {
        // runs the population code as provided
        
        int x = 0;
        int y = ((n+1) / 2)-1;

        array[x][y] = 1;

        for (int i = 2; i <= n * n; i++) {
            int newX = (x - 1 + n) % n;
            int newY = (y + 1) % n;

            if (array[newX][newY] == 0) {
                x = newX;
                y = newY;
            } else {
                x = (x + 1) % n;
            }

            array[x][y] = i;
        }
    }

    private void fillArrayWithZeros() {
        // iterates through the array and sets to 0
        for (int x = 0; x < n; x++) {
            for (int y = 0; y < n; y++) {
                array[x][y] = 0;
            }
        }
    }

    public void displayArray() {
        // displays the array by iterating through each row and column and printing it
        System.out.println("Array:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // keeps all elements in line
                System.out.printf("%-4d", array[i][j]);
            }
            System.out.println();
        }
    }

    public boolean swapItems(int row, int column, Direction direction) {

        int x = translateX(row);
        int y = translateY(column);


        int newX = x + direction.getxRelative();
        int newY = y + direction.getyRelative();

        // Check if the new indices are out of bounds
        if (newX < 0 || newY < 0 || newX >= n || newY >= n) {
            return false;
        }

        // increment the turns to solve
        turnsToSolve++;
        // Perform the swap
        performSwap(x,y,direction);

        return true;
    }

    private void performSwap(int x1, int y1, Direction direction) {
        // swap the actual values
        int x2 = x1 + direction.getxRelative();
        int y2 = y1 + direction.getyRelative();

        int originalValue = array[x1][y1];
        int newValue = array[x2][y2];
        array[x1][y1] = newValue;
        array[x2][y2] = originalValue;
    }


    private int translateX(int targetX) {
        // translate the x from the input to the actual array index
        return n - targetX;
    }

    private int translateY(int targetY) {
        // translate the y from the input to the actual array index
        return targetY -1;
    }

    public void shuffleMatrix() {
        // shuffles the matrix n^2 times with neighbouring elements (not diagonals)
        for (int i = 0; i<n*n; i++) {
            int x = random.nextInt(n);
            int y = random.nextInt(n);

            // random element on the array

            Direction direction = getEligibleRandomDirection(x,y);
            // swaps in random direction that is eligible
            performSwap(x,y, direction);
        }
    }

    public Direction getEligibleRandomDirection(int x, int y) {

        List<Direction> directions = new ArrayList<>(List.of(Direction.values()));

        // gets all directions and removes them if the element cannot go in a certain direction e.g. left column going left

        directions.removeIf(direction ->
                (x + direction.getxRelative() < 0 || x + direction.getxRelative() >= n) ||
                        (y + direction.getyRelative() < 0 || y + direction.getyRelative() >= n) );

        // returns a random one of the remaining (there will always be 2+ possibilities)
        return directions.get(random.nextInt(directions.size()));
    }

    public void checkMagicSquare() {
        // runs each turn to check if the square is "magic"
        int expectedSum = n * (n*n + 1) / 2;

        isMagicSquare = rowsAndColumnsAddToExpected(expectedSum) && diagonalsAddToExpected(expectedSum);
    }

    public boolean isMagicSquare() {
        // getter for isMagicSquare
        return this.isMagicSquare;
    }

    private boolean rowsAndColumnsAddToExpected(int expectedSum) {
        // iterates over all rows and columns in one go and checks they add to the expectedSum
        for (int x = 0; x<n; x++) {
            int rowSum = 0;
            int columnSum = 0;
            for (int j = 0; j<n; j++) {
                rowSum+=array[x][j];
                columnSum+=array[j][x];
            }

            if (rowSum!=expectedSum || columnSum!=expectedSum) return false;
        }
        return true;
    }

    private boolean diagonalsAddToExpected(int expectedSum) {
        int diagonalOneSum = 0;
        // firstly checks the first diagonal of which all elements are array[1][1], array[2][2] etc.
        for (int i = 0; i<n; i++) {
            diagonalOneSum+=array[i][i];
        }

        if (diagonalOneSum != expectedSum) return false;

        // then checks the second diagonal
        int diagonalTwoSum = 0;
        for (int i = 0; i < n; i++) {
            diagonalTwoSum += array[i][n - 1 - i];
        }
        return diagonalTwoSum == expectedSum;
    }

    public int getTurnsToSolve() {
        // getter for turns to solve
        return turnsToSolve;
    }
}

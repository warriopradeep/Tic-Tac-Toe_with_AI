package tictactoe;

import java.util.EnumMap;
import java.util.Random;
import java.util.Scanner;

public class Player {


    private final PlayerType playerType;

    // set the player's character whether X or O
    private char c;
    private char other;

    // set the reference to the grid variable
    private char[][] grid;

    gameGrid currentGame;

    EnumMap<GameState, Integer> score = new EnumMap<>(GameState.class);

    {
        score.put(GameState.DRAW, 0);
    }

    public Player(PlayerType playerType) {
        this.playerType = playerType;
    }

    public void makeMove() {

        switch (playerType) {
            case EASY -> nextAIMove();
            case MEDIUM -> nextMediumMove();
            case HARD -> nextHardMove();
            case HUMAN -> nextMove();
        }
    }

    public void setGridReference(char[][] grid) {
        this.grid = grid;
    }

    public void setPlayerChar(char c) {
        this.c = c;
        other = c == 'X'? 'O' : 'X';
    }

    private void nextAIMove() {
        // Generate random row and col values and put it as AI input to the grid

        int row, col;
        Random random = new Random();

        do {
            row = random.nextInt(3) + 1;
            col = random.nextInt(3) + 1;

        } while (cellOccupied(row, col));

        grid[row - 1][col - 1] = c;

        System.out.println("Making move level \"easy\"");
    }

    private void nextMediumMove() {
        // Generate row and col values depending on whether there are already same the character in the row/col/diagonal
        int row, col;

        // check if the rows have 2 of the same character and whether a cell is empty in that row
        for (int i = 0; i < 3; i++) {
            int[] sums = getCAndBSumInRow(c, i);
            int cSum = sums[0];
            int blankSum = sums[1];

            if ((cSum + blankSum) == 3 && cSum == 2) {
                grid[i][indexOfEmptyCol(i)] = c;
                System.out.println("Making move level \"medium\"");
                return;
            }
        }

        // check if the cols have 2 of the same character and whether a cell is empty in that col
        for (int i = 0; i < 3; i++) {
            int[] sums = getCAndBSumInCol(c, i);
            int cSum = sums[0];
            int blankSum = sums[1];

            if ((cSum + blankSum) == 3 && cSum == 2) {
                grid[indexOfEmptyRow(i)][i] = c;
                System.out.println("Making move level \"medium\"");
                return;
            }
        }

        // check if the diagonals have 2 of the same character and whether a cell is empty in that diagonal (diagonal right)
        {
            int cSum = 0;
            int blankSum = 0;

            for (int i = 0; i < 3; i++) {
                char o = grid[i][i];

                if (o == c) cSum++;
                if (o == ' ') blankSum++;
            }

            if ((cSum + blankSum) == 3 && cSum == 2) {
                grid[indexOfEmptyDiag(1)][indexOfEmptyDiag(1)] = c;
                System.out.println("Making move level \"medium\"");
                return;
            }
        }

        // check if the diagonals have 2 of the same character and whether a cell is empty in that diagonal (diagonal left)
        {
            int cSum = 0;
            int blankSum = 0;

            for (int i = 0; i < 3; i++) {
                char o = grid[i][2 - i];

                if (o == c) cSum++;
                if (o == ' ') blankSum++;
            }

            if ((cSum + blankSum) == 3 && cSum == 2) {
                grid[indexOfEmptyDiag(-1)][2 - indexOfEmptyDiag(-1)] = c;
                System.out.println("Making move level \"medium\"");
                return;
            }
        }

        // todo: implement blocking other player from winning

        // make move to block the opponent from winning
        // block opponent from winning row
        for (int i = 0; i < 3; i++) {
            int[] sums = getCAndBSumInRow(other, i);
            int cSum = sums[0];
            int blankSum = sums[1];

            if ((cSum + blankSum) == 3 && cSum == 2) {
                grid[i][indexOfEmptyCol(i)] = c;
                System.out.println("Making move level \"medium\"");
                return;
            }
        }

        // block opponent from winning
        // check if the cols have 2 of the same character and whether a cell is empty in that col
        for (int i = 0; i < 3; i++) {
            int[] sums = getCAndBSumInCol(other, i);
            int cSum = sums[0];
            int blankSum = sums[1];

            if ((cSum + blankSum) == 3 && cSum == 2) {
                grid[indexOfEmptyRow(i)][i] = c;
                System.out.println("Making move level \"medium\"");
                return;
            }
        }

        // block the opponent from winning
        {
            int cSum = 0;
            int blankSum = 0;

            for (int i = 0; i < 3; i++) {
                char o = grid[i][i];

                if (o == other) cSum++;
                if (o == ' ') blankSum++;
            }

            if ((cSum + blankSum) == 3 && cSum == 2) {
                grid[indexOfEmptyDiag(1)][indexOfEmptyDiag(1)] = c;
                System.out.println("Making move level \"medium\"");
                return;
            }
        }

        // block the opponent from winning
        // check if the diagonals have 2 of the same character and whether a cell is empty in that diagonal (diagonal left)
        {
            int cSum = 0;
            int blankSum = 0;

            for (int i = 0; i < 3; i++) {
                char o = grid[i][2 - i];

                if (o == other) cSum++;
                if (o == ' ') blankSum++;
            }

            if ((cSum + blankSum) == 3 && cSum == 2) {
                grid[indexOfEmptyDiag(-1)][2 - indexOfEmptyDiag(-1)] = c;
                System.out.println("Making move level \"medium\"");
                return;
            }
        }

        // Make a random move
        Random random = new Random();

        do {
            row = random.nextInt(3) + 1;
            col = random.nextInt(3) + 1;

        } while (cellOccupied(row, col));

        grid[row - 1][col - 1] = c;

        System.out.println("Making move level \"medium\"");

    }


    private int[] getCAndBSumInCol(char t, int colIndex) {
        int cSum = 0;
        int blankSum = 0;

        for (int j = 0; j < 3; j++) {
            char o = grid[j][colIndex];

            if (o == c) cSum++;
            if (o == ' ') blankSum++;
        }

        return new int[] {cSum, blankSum};
    }

    private int[] getCAndBSumInRow(char t, int rowIndex) {

        int cSum = 0;
        int blankSum = 0;

        // check if the row has 2 of the same character and whether a cell is empty in that row
        for (char o : grid[rowIndex]) {
            if (o == t) cSum++;
            if (o == ' ') blankSum++;
        }

        return new int[] {cSum, blankSum};
    }

    private int indexOfEmptyDiag(int diag) {
        // diag == 1 for positive diagonal, diag == -1 for negative diagonal

        int index = 0;

        if (diag == 1){
            for (int i = 0; i < 3; i++) {
                if (grid[i][i] == ' ') index = i;
            }
        } else if (diag == -1) {
            for (int i = 0; i < 3; i++) {
                if (grid[i][2 - i] == ' ') index = i;
            }
        }

        return index;
    }

    private int indexOfEmptyCol(int row) {
        // call this only when you have 2 same chars in the row and want the col index of the empty cell
        int index = 0;
        for (int i = 0; i < 3; i++) {
            if (grid[row][i] == ' ') index = i;
        }

        return index;
    }

    private int indexOfEmptyRow(int col) {
        // call this only when you have 2 same chars in the col and want the row index of the empty cell
        int index = 0;
        for (int i = 0; i < 3; i++) {
            if (grid[i][col] == ' ') index = i;
        }

        return index;
    }



    private void nextMove() {
        // Take the user input and place X or an O in the grid depending on whether the boolean b is true or false respectively.

        int[] input = getUserInput();
        int row = input[0];
        int col = input[1];

        grid[row - 1][col - 1] = c;
    }

    private int[] getUserInput() {
        // Take the coordinates for the next position to draw X or O

        Scanner in = new Scanner(System.in);
        int row;
        int col;

        while (true) {
            System.out.print("Enter the coordinates: ");

            if (in.hasNextInt()) {
                row = in.nextInt();
            } else {
                System.out.println("You should enter numbers!");
                in.nextLine();
                continue;
            }

            if (in.hasNextInt()) {
                col = in.nextInt();
            } else {
                System.out.println("You should enter numbers!");
                in.nextLine();
                continue;
            }

            if (row < 1 || row > 3 || col < 1 || col > 3) {
                System.out.println("Coordinates should be from 1 to 3!");
            } else if (cellOccupied(row, col)) {
                System.out.println("This cell is occupied! Choose another one!");
            } else break;
        }

        return new int[]{row, col};
    }

    private boolean cellOccupied(int row, int col) {
        // Check if the cell at the given row and col is occupied

        return grid[row - 1][col - 1] != ' ';
    }

    protected void nextHardMove() {

        scoreInitializer(c);
        int row = 1, col = 0;
        int bestScore = Integer.MIN_VALUE;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid[i][j] == ' ') {
                    grid[i][j] = c;
                    int score = minimax(grid, 0, false);
                    grid[i][j] = ' ';

                    if (score > bestScore) {
                        bestScore = score;
                        row = i;
                        col = j;
                    }
                }
            }
        }
        grid[row][col] = c;
        System.out.println("Making move level \"hard\"");
    }

    private int minimax(char[][] grid, int depth, boolean isMaximizing) {
        currentGame = new gameGrid(grid);
        GameState result = currentGame.getCurrentGameStatus();

        if (!result.equals(GameState.GNF)) {
            return score.get(result);
        }


        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (grid[i][j] == ' ') {
                        grid[i][j] = c;
                        int score = minimax(grid, depth + 1, false);
                        grid[i][j] = ' ';
                        bestScore = Math.max(score, bestScore);
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (grid[i][j] == ' ') {
                        grid[i][j] = other;
                        int score = minimax(grid, depth + 1, true);
                        grid[i][j] = ' ';
                        bestScore = Math.min(score, bestScore);
                    }
                }
            }
            return bestScore;
        }

    }

    void scoreInitializer(char ai) {

        if (ai == 'X') {
            score.put(GameState.X, 20);
            score.put(GameState.O, -15);
        } else {
            score.put(GameState.X, -15);
            score.put(GameState.O, 20);
        }
    }
}

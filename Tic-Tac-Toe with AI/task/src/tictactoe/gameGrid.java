package tictactoe;

public class gameGrid {

    private final char[][] grid = new char[3][3];
    private char currentTurn = 'X';
    private Player player1;
    private Player player2;

    public gameGrid(Player player1, Player player2) {
        getInitialState();
        this.player1 = player1;
        this.player2 = player2;
        player1.setGridReference(grid);
        player1.setPlayerChar('X');
        player2.setGridReference(grid);
        player2.setPlayerChar('O');
        gameLoop();
    }

    public gameGrid(char[][] grid) {
        readStateFromArray(grid);
    }

    private void gameLoop() {
        GameState state = getCurrentGameStatus();

        while (state == GameState.GNF) {

            if (currentTurn == 'X') {
                currentTurn = 'O';
//                nextMove();
                 player1.makeMove();
            } else {
                currentTurn = 'X';
//                nextAIMove();
                 player2.makeMove();
            }

            printGameGrid();
            state = getCurrentGameStatus();
        }

        System.out.println(state.getState());
    }

    private void getInitialState() {
        // Take input from the user of the initial positions of X and O in the grid
        String initialState = "_________";

        readStateFromString(initialState);
        printGameGrid();
    }

    public void printGameGrid() {
        // Print out the current state of the X and O grid

        System.out.println("---------");
        for (char[] chars : grid) {
            System.out.printf("| %c %c %c |%n", chars[0], chars[1], chars[2]);
        }
        System.out.println("---------");
    }

    private void readStateFromString(String s) {
        // Take the input string of the initial state of grid and put it in the grid array.

        int i = 0;
        int j = 0;
        for (char c : s.toCharArray()) {
            if (j == 3) {
                i++;
                j = 0;
            }
            if (c == '_') c = ' ';
            grid[i][j] = c;
            j++;
        }
    }

    private void readStateFromArray(char[][] gridArray) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            for (char t : gridArray[i]) {
                s.append(t);
            }
        }

        readStateFromString(s.toString());
    }

    protected GameState getCurrentGameStatus() {
        // return whether the game is won by X or won by O, or it is a draw or the game is still going on

        if (threeInDiagonal('X') || threeInRow('X') || threeInCol('X')) {
            return GameState.X;
        } else if (threeInDiagonal('O') || threeInRow('O') || threeInCol('O')) {
            return GameState.O;
        } else if (checkDraw()) {
            return GameState.DRAW;
        } else return GameState.GNF;

    }

    private boolean threeInDiagonal(char c) {
        // check if the given character c (X or O) is in a diagonal row

        boolean x = grid[0][0] == grid[1][1] && grid[1][1] == grid[2][2] && grid[1][1] == c;
        boolean y = grid[0][2] == grid[1][1] && grid[1][1] == grid[2][0] && grid[1][1] == c;

        return x || y;
    }

    private boolean threeInRow(char c) {
        // check if the given character c (X or O) is in a row

        for (int i = 0; i < 3; i++) {
            if (grid[i][0] == grid[i][1] && grid[i][1] == grid[i][2] && grid[i][1] == c) return true;
        }

        return false;
    }

    private boolean threeInCol(char c) {
        // check if the given character c (X or O) is in a column

        for (int i = 0; i < 3; i++) {
            if (grid[0][i] == grid[1][i] && grid[1][i] == grid[2][i] && grid[1][i] == c) return true;
        }
        return false;
    }

    private boolean checkDraw() {
        // check whether the game has ended in a draw
        int nX = 0;
        int nO = 0;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid[i][j] == 'X') nX++;
                if (grid[i][j] == 'O') nO++;
            }
        }

        int sumXO = nX + nO;

        return sumXO == 9;
    }


}

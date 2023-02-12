package tictactoe;

import java.util.Scanner;

public class menu {

    public void showMenu() {
        Scanner in = new Scanner(System.in);

        while(true) {
            System.out.print("Input command: ");
            String[] input = in.nextLine().trim().split(" ");

            if (input[0].equalsIgnoreCase("exit")) return;

            if (input.length != 3) {
                System.out.println("Bad parameters!");
                continue;
            }

            PlayerType p1, p2;

            p1 = getPlayerType(input[1]);

            p2 = getPlayerType(input[2]);


            gameGrid g = new gameGrid(new Player(p1), new Player(p2));
        }
    }

    private PlayerType getPlayerType(String s) {

        PlayerType p = PlayerType.EASY;

        switch (s.toLowerCase()) {
            case "user" -> p = PlayerType.HUMAN;
            case "medium" -> p = PlayerType.MEDIUM;
            case "hard" -> p = PlayerType.HARD;
        }

        return p;
    }
}

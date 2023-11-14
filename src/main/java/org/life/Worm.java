// Worm.java
package org.life;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Worm extends Organism {

    private Random random = new Random();

    public Worm(int energy, String name) {
        super(energy, name);
    }

    @Override
    public void move(Board board) {
        List<Position> validMoves = getValidMoves(board);

        // Wybierz losowy ruch
        Position randomMove = validMoves.get(random.nextInt(validMoves.size()));
        int newX = randomMove.getX();
        int newY = randomMove.getY();

        // Worm zużywa 1 energii podczas ruchu
        energy -= 1;
        if (energy <= 0) {
            alive = false;
            System.out.println(name + " zdechł z powodu braku energii!");
            board.getOrganisms()[position.getX()][position.getY()] = null; // usuń z planszy
        } else {
            board.moveOrganism(this, newX, newY);
        }
    }

    private List<Position> getValidMoves(Board board) {
        List<Position> validMoves = new ArrayList<>();

        for (int i = -1; i <= 1; i += 2) {
            for (int j = -1; j <= 1; j += 2) {
                int newX = position.getX() + i;
                int newY = position.getY() + j;

                if (isValidMove(board, newX, newY)) {
                    validMoves.add(new Position(newX, newY));
                }
            }
        }

        return validMoves;
    }

    private boolean isValidMove(Board board, int x, int y) {
        return x >= 0 && x < board.getWidth() && y >= 0 && y < board.getHeight()
                && (x != position.getX() || y != position.getY()); // nie chcemy tego samego miejsca
    }
}

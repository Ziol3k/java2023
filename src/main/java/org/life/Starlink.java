// Starlink.java
package org.life;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Starlink extends Organism {

    private Random random = new Random();

    public Starlink(int energy, String name) {
        super(energy, name);
    }

    @Override
    public void move(Board board) {
        List<Position> validMoves = getValidMoves(board);

        Organism detectedOrganism = detectOrganism(board, 2);
        if (detectedOrganism != null) {
            board.moveOrganism(this, detectedOrganism.getPosition().getX(), detectedOrganism.getPosition().getY());
        } else {
            Position randomMove = validMoves.get(random.nextInt(validMoves.size()));
            int newX = randomMove.getX();
            int newY = randomMove.getY();

                // Starlink zużywa 3 energii podczas ruchu
            energy -= 3;
            if (energy <= 0) {
                alive = false;
                System.out.println(name + " zdechł z powodu braku energii!");
                board.getOrganisms()[position.getX()][position.getY()] = null; // usuń z planszy
            } else {
                board.moveOrganism(this, newX, newY);
            }

        }
    }

    private Organism detectOrganism(Board board, int radius) {
        int x = position.getX();
        int y = position.getY();

        for (int i = Math.max(0, x - radius); i <= Math.min(board.getWidth() - 1, x + radius); i++) {
            for (int j = Math.max(0, y - radius); j <= Math.min(board.getHeight() - 1, y + radius); j++) {
                if (board.getOrganisms()[i][j] instanceof Organism && board.getOrganisms()[i][j] != this) {
                    return (Organism) board.getOrganisms()[i][j];
                }
            }
        }
        return null;
    }

    private List<Position> getValidMoves(Board board) {
        List<Position> validMoves = new ArrayList<>();

        for (int i = -2; i <= 2; i ++) {
            for (int j = -2; j <= 2; j ++) {
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

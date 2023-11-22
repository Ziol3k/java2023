// LifeSimulator.java
package org.life;

import java.util.ArrayList;
import java.util.List;

public class LifeSimulator {

  public static void main(String[] args) {
    // Create a board with dimensions 20x20
    Board board = new Board(20, 20);

    List<Organism> organisms = new ArrayList<>();

    // Add 3 Worms and 3 Starlinks
    organisms.add(new Worm(100, "Worm1"));
    organisms.add(new Worm(100, "Worm2"));
    organisms.add(new Worm(100, "Worm3"));
    organisms.add(new Starlink(100, "Starlink1"));
    organisms.add(new Starlink(100, "Starlink2"));
    organisms.add(new Starlink(100, "Starlink3"));

    // Add organisms to random positions on the board
    for (Organism organism : organisms) {
      int x = getRandomPosition(board.getWidth());
      int y = getRandomPosition(board.getHeight());
      board.addOrganism(organism, x, y);
    }

    // Simulate movements and display the state of the board
    while (countLivingOrganisms(organisms) > 1) {
      for (Organism organism : organisms) {
        if (countLivingOrganisms(organisms) > 1){
          if (organism.isAlive()) {
            organism.move(board);
          }
        } else {
          break;
        }
      }
    }

    // Find the winner and display the information
    Organism winner = findWinner(organisms);
    System.out.println("Organism " + winner.getName() + " won!");
  }

  private static int getRandomPosition(int max) {
    return (int) (Math.random() * max);
  }

  private static int countLivingOrganisms(List<Organism> organisms) {
    int count = 0;
    for (Organism organism : organisms) {
      if (organism.isAlive()) {
        count++;
      }
    }
    return count;
  }

  private static Organism findWinner(List<Organism> organisms) {
    for (Organism organism : organisms) {
      if (organism.isAlive()) {
        return organism;
      }
    }
    return null; // This should not happen as we assume there is always at least one living organism.
  }
}

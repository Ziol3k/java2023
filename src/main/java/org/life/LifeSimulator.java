// LifeSimulator.java
package org.life;

import java.util.ArrayList;
import java.util.List;

public class LifeSimulator {

  public static void main(String[] args) {
    Board board = new Board(20, 20);

    List<Organism> organisms = new ArrayList<>();

    // Dodaj 3 Worms
    organisms.add(new Worm(100, "Worm1"));
    organisms.add(new Worm(100, "Worm2"));
    organisms.add(new Worm(100, "Worm3"));
    organisms.add(new Starlink(100, "Starlink1"));
    organisms.add(new Starlink(100, "Starlink2"));
    organisms.add(new Starlink(100, "Starlink3"));


    // Dodaj organizmy na losowe pozycje na planszy
    for (Organism organism : organisms) {
      int x = getRandomPosition(board.getWidth());
      int y = getRandomPosition(board.getHeight());
      board.addOrganism(organism, x, y);
    }

    // Symulacja ruchów i wyświetlanie stanu planszy
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

    // Znajdź zwycięzcę i wyświetl informację
    Organism winner = findWinner(organisms);
    System.out.println("Organizm " + winner.getName() + " wygrał!");
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
    return null; // Nie powinno się zdarzyć, gdyż zakładamy, że zawsze jest co najmniej jeden żywy organizm.
  }
}

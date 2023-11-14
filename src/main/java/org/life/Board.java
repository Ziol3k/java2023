// Board.java
package org.life;

public class Board {

  private int width;
  private int height;
  private Organism[][] organisms;

  public Board(int width, int height) {
    this.width = width;
    this.height = height;
    this.organisms = new Organism[width][height];
  }

  public void addOrganism(Organism organism, int x, int y) {
    if (organisms[x][y] == null) {
      organisms[x][y] = organism;
      organism.setPosition(new Position(x, y));
      System.out.println(organism.getClass().getSimpleName() + " " + organism.getName() + " dodany na pozycję " + (x + 1) + "," + (y + 1));
    } else {
      System.out.println("Pozycja już zajęta!");
    }
  }

  public void moveOrganism(Organism organism, int newX, int newY) {
    if (newX >= 0 && newX < width && newY >= 0 && newY < height) {
      int oldX = organism.getPosition().getX();
      int oldY = organism.getPosition().getY();

      Organism existingOrganism = organisms[newX][newY];

      if (existingOrganism == null) {
        organisms[oldX][oldY] = null;
        organisms[newX][newY] = organism;
        organism.setPosition(new Position(newX, newY));
        System.out.println(organism.getName() + " przemieścił się z pozycji "
                + (oldX + 1) + "," + (oldY + 1) + " na pozycję " + (newX + 1) + "," + (newY + 1));
      } else {
        // Consume energy if there is an existing organism
        organism.consumeOrganism(existingOrganism);
        organisms[newX][newY] = organism;
        organisms[oldX][oldY] = null;
        System.out.println(organism.getName() + " skonsumował " + existingOrganism.getName()
                + " na pozycji " + (newX + 1) + "," + (newY + 1));

        // Remove consumed organism from the board
        organisms[existingOrganism.getPosition().getX()][existingOrganism.getPosition().getY()] = null;
      }
    } else {
      System.out.println("Nieprawidłowy ruch!");
    }
  }


  public Organism[][] getOrganisms() {
    return organisms;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }
}

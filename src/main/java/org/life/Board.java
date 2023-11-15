// Board.java
package org.life;

public class Board {

  private int width;
  private int height;
  private Organism[][] organisms;

  // Constructor for initializing the board with the specified width and height
  public Board(int width, int height) {
    this.width = width;
    this.height = height;
    this.organisms = new Organism[width][height];
  }

  // Method to add an organism to the board at the specified position
  public void addOrganism(Organism organism, int x, int y) {
    if (organisms[x][y] == null) {
      organisms[x][y] = organism;
      organism.setPosition(new Position(x, y));
      System.out.println(organism.getClass().getSimpleName() + " " + organism.getName() + " added at position " + (x + 1) + "," + (y + 1));
    } else {
      System.out.println("Position already occupied!");
    }
  }

  // Method to move an organism to a new position on the board
  public void moveOrganism(Organism organism, int newX, int newY) {
    if (newX >= 0 && newX < width && newY >= 0 && newY < height) {
      int oldX = organism.getPosition().getX();
      int oldY = organism.getPosition().getY();

      Organism existingOrganism = organisms[newX][newY];

      if (existingOrganism == null) {
        organisms[oldX][oldY] = null;
        organisms[newX][newY] = organism;
        organism.setPosition(new Position(newX, newY));
        System.out.println(organism.getName() + " moved from position "
                + (oldX + 1) + "," + (oldY + 1) + " to position " + (newX + 1) + "," + (newY + 1));
      } else {
        // Consume energy if there is an existing organism
        organism.consumeOrganism(existingOrganism);
        organisms[newX][newY] = organism;
        organisms[oldX][oldY] = null;
        System.out.println(organism.getName() + " consumed " + existingOrganism.getName()
                + " at position " + (newX + 1) + "," + (newY + 1));

        // Remove consumed organism from the board
        organisms[existingOrganism.getPosition().getX()][existingOrganism.getPosition().getY()] = null;
      }
    } else {
      System.out.println("Invalid move!");
    }
  }

  // Getter method to retrieve the 2D array of organisms on the board
  public Organism[][] getOrganisms() {
    return organisms;
  }

  // Getter method to retrieve the width of the board
  public int getWidth() {
    return width;
  }

  // Getter method to retrieve the height of the board
  public int getHeight() {
    return height;
  }
}

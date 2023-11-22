// Organism.java
package org.life;

public abstract class Organism {

  protected int energy;
  protected Position position;
  protected boolean alive = true; // Adding a flag to determine if the organism is alive
  protected String name; // Adding a field to store the name of the organism

  // Constructor to initialize the organism with energy and name
  public Organism(int energy, String name) {
    this.energy = energy;
    this.name = name;
  }

  // Abstract method for organisms to implement their specific movement logic
  public abstract void move(Board board);

  // Setter method to set the position of the organism
  public void setPosition(Position position) {
    this.position = position;
  }

  // Getter method to retrieve the position of the organism
  public Position getPosition() {
    return position;
  }

  // Getter method to retrieve the energy of the organism
  public int getEnergy() {
    return energy;
  }

  // Setter method to set the energy of the organism
  public void setEnergy(int energy) {
    this.energy = energy;
  }

  // Method for an organism to consume another organism
  public void consumeOrganism(Organism other) {
    energy += other.getEnergy();
    other.setEnergy(0);
    other.alive = false; // Setting the flag of the consumed organism to dead
  }

  // Getter method to check if the organism is alive
  public boolean isAlive() {
    return alive;
  }

  // Getter method to retrieve the name of the organism
  public String getName() {
    return name;
  }
}

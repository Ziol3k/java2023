// Organism.java
package org.life;

public abstract class Organism {

  protected int energy;
  protected Position position;
  protected boolean alive = true; // Dodajemy flagę określającą, czy organizm jest żywy
  protected String name; // Dodajemy pole przechowujące nazwę organizmu

  public Organism(int energy, String name) {
    this.energy = energy;
    this.name = name;
  }

  public abstract void move(Board board);

  public void setPosition(Position position) {
    this.position = position;
  }

  public Position getPosition() {
    return position;
  }

  public int getEnergy() {
    return energy;
  }

  public void setEnergy(int energy) {
    this.energy = energy;
  }

  public void consumeOrganism(Organism other) {
    energy += other.getEnergy();
    other.setEnergy(0);
    other.alive = false; // Ustawiamy flagę organizmu na martwą po skonsumowaniu
  }

  public boolean isAlive() {
    return alive;
  }

  public String getName() {
    return name;
  }
}

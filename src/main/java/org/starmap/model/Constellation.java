package org.starmap.model;

import java.util.List;

public class Constellation {
    private final String name;
    private final List<Star> stars;

    public Constellation(String name, List<Star> stars) {
        this.name = name;
        this.stars = stars;
    }

    // Getters
    public String getName() {
        return name;
    }

    public List<Star> getStars() {
        return stars;
    }

    // Dodawanie i usuwanie gwiazd z gwiazdozbioru
    public void addStar(Star star) {
        stars.add(star);
    }

    public void removeStar(Star star) {
        stars.remove(star);
    }
}

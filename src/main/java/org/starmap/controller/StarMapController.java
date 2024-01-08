package org.starmap.controller;

import org.starmap.model.Constellation;
import org.starmap.model.Star;
import org.starmap.utils.DataLoader;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class StarMapController {
    private List<Star> stars;
    private List<Constellation> constellations;

    public StarMapController(String dataFilePath) {
        loadData(dataFilePath);
    }

    private void loadData(String dataFilePath) {
        this.stars = DataLoader.loadStars(dataFilePath);
        this.constellations = DataLoader.loadConstellations(dataFilePath, stars);
    }

    public List<Star> getStars() {
        return stars;
    }

    public void setStars(List<Star> stars) {
        this.stars = stars;
    }

    public List<Constellation> getConstellations() {
        return constellations;
    }

    public void setConstellations(List<Constellation> constellations) {
        this.constellations = constellations;
    }

    public void addStar(Star star, String constellationName) {
        stars.add(star);
        getConstellationByName(constellationName).ifPresent(constellation -> constellation.addStar(star));
    }


    public void removeStar(String name) {
        stars.removeIf(star -> star.getName().equalsIgnoreCase(name));
    }

    public void updateStar(String originalName, String newName, double newX, double newY, double newBrightness, String constellationName) {
        Optional<Star> starOpt = getStarByName(originalName);

        starOpt.ifPresent(star -> {
            star.setName(newName);
            star.setXPosition(newX);
            star.setYPosition(newY);
            star.setBrightness(newBrightness);

            // Przypisanie do nowego gwiazdozbioru, jeśli jest inny niż obecny
            getConstellations().forEach(constellation -> constellation.removeStar(star));
            getConstellationByName(constellationName).ifPresent(constellation -> constellation.addStar(star));
        });
    }


    public Optional<Star> getStarByName(String name) {
        return stars.stream().filter(star -> star.getName().equalsIgnoreCase(name)).findFirst();
    }

    public void addConstellation(Constellation constellation) {
        constellations.add(constellation);
    }

    public void removeConstellation(String name) {
        constellations.removeIf(constellation -> constellation.getName().equalsIgnoreCase(name));
    }

    public List<String> getConstellationNames() {
        return constellations.stream()
                .map(Constellation::getName)
                .collect(Collectors.toList());
    }

    public Optional<Constellation> findConstellationByStar(Star star) {
        return constellations.stream()
                .filter(constellation -> constellation.getStars().contains(star))
                .findFirst();
    }
    public Optional<Constellation> getConstellationByName(String name) {
        return constellations.stream().filter(constellation -> constellation.getName().equalsIgnoreCase(name)).findFirst();
    }
}

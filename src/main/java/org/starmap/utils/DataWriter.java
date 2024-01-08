package org.starmap.utils;

import org.starmap.model.Constellation;
import org.starmap.model.Star;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class DataWriter {

    public static void saveDataToFile(String filePath, List<Star> stars, List<Constellation> constellations) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Star star : stars) {
                writer.write(formatStar(star));
            }

            for (Constellation constellation : constellations) {
                writer.write(formatConstellation(constellation));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String formatStar(Star star) {
        return String.format(Locale.US, "Star,%s,%.2f,%.2f,%.2f%n",
                star.getName(), star.getXPosition(), star.getYPosition(), star.getBrightness());
    }

    private static String formatConstellation(Constellation constellation) {
        String starNames = constellation.getStars().stream()
                .map(Star::getName)
                .collect(Collectors.joining(","));
        return String.format("Constellation,%s,%s%n", constellation.getName(), starNames);
    }
}

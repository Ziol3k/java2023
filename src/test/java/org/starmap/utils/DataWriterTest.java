package org.starmap.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.starmap.model.Constellation;
import org.starmap.model.Star;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertLinesMatch;

public class DataWriterTest {

    @TempDir
    Path tempDir;

    @Test
    void testSaveDataToFile() throws IOException {
        Path testFilePath = tempDir.resolve("test_output.json");
        List<Star> stars = new ArrayList<>();
        stars.add(new Star("Betelgeuse", 300, 400, 0.42));
        stars.add(new Star("Rigel", 200, 350, 0.18));

        List<Constellation> constellations = new ArrayList<>();
        Constellation orion = new Constellation("Orion", new ArrayList<>());
        orion.addStar(new Star("Bellatrix", 250, 500, 1.64));
        orion.addStar(new Star("Saiph", 280, 550, 2.07));
        constellations.add(orion);

        DataWriter.saveDataToFile(testFilePath.toString(), stars, constellations);

        List<String> fileContent = Files.readAllLines(testFilePath);

        List<String> expectedLines = List.of(
                "Star,Betelgeuse,300.00,400.00,0.42",
                "Star,Rigel,200.00,350.00,0.18",
                "Constellation,Orion,Bellatrix,Saiph"
        );

        assertLinesMatch(expectedLines, fileContent);
    }
}

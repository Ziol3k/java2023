package org.starmap.view;

import javafx.animation.PauseTransition;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.starmap.controller.StarMapController;
import org.starmap.model.Star;
import org.starmap.model.Constellation;

import java.util.*;

public class StarMapView extends Canvas {
    private final StarMapController controller;
    private Star selectedStar; // Gwiazda wybrana przez użytkownika
    private boolean showCoordinates = true;
    private PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
    private Star currentHoveredStar = null;
    private Map<String, Color> constellationColors = new HashMap<>();

    public StarMapView(StarMapController controller) {

        this.controller = controller;
        initializeCanvas();
        drawMap();
        initializeConstellationColors();
        addMouseMotionListener();
    }

    private void initializeCanvas() {
        this.setWidth(1024); // Możesz dostosować te wartości
        this.setHeight(768);
        setupEventHandlers();
    }

    private void initializeConstellationColors() {
        List<Constellation> constellations = controller.getConstellations();
        for (Constellation constellation : constellations) {
            int hash = constellation.getName().hashCode();
            Random rand = new Random(hash); // Use hash as a seed for random generator
            Color color = new Color(rand.nextDouble(), rand.nextDouble(), rand.nextDouble(), 1);
            constellationColors.put(constellation.getName(), color);
        }
    }

    private void setupEventHandlers() {
        this.addEventHandler(MouseEvent.MOUSE_PRESSED, this::onMousePressed);
        this.addEventHandler(MouseEvent.MOUSE_DRAGGED, this::onMouseDragged);
        this.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> selectedStar = null);
    }

    public void setShowCoordinates(boolean show) {
        this.showCoordinates = show;
        drawMap(); // Przerysowanie mapy z uwzględnieniem nowego ustawienia
    }


    private void drawCoordinates(GraphicsContext gc) {
        if (!showCoordinates) {
            return;
        }

        gc.setStroke(Color.GRAY);
        gc.setLineWidth(1);

        // Rysowanie osi X
        gc.strokeLine(0, getHeight() / 2, getWidth(), getHeight() / 2);

        // Rysowanie osi Y
        gc.strokeLine(getWidth() / 2, 0, getWidth() / 2, getHeight());
    }


    private void onMousePressed(MouseEvent event) {
        double x = event.getX();
        double y = event.getY();

        if (event.isSecondaryButtonDown()) {
            selectedStar = findStarAtPosition(x, y);
            if (selectedStar != null) {
                showEditStarDialog(selectedStar);
            } else {
                showAddStarDialog(x, y);
            }
        } else if (event.isPrimaryButtonDown()) {
            selectedStar = findStarAtPosition(x, y);
        }
    }


    private void onMouseDragged(MouseEvent event) {
        if (event.isPrimaryButtonDown() && selectedStar != null) {
            selectedStar.setXPosition(event.getX());
            selectedStar.setYPosition(event.getY());
            drawMap();
        }
    }


    private void showEditStarDialog(Star star) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Edytuj Gwiazdę");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField nameField = new TextField(star.getName());
        TextField brightnessField = new TextField(String.valueOf(star.getBrightness()));
        TextField xField = new TextField(String.valueOf(star.getXPosition()));
        TextField yField = new TextField(String.valueOf(star.getYPosition()));
        ComboBox<String> constellationBox = new ComboBox<>();
        constellationBox.getItems().addAll(controller.getConstellationNames());

        // Pobierz nazwę gwiazdozbioru, do którego należy gwiazda
        Optional<Constellation> currentConstellation = controller.findConstellationByStar(star);
        String currentConstellationName = currentConstellation.map(Constellation::getName).orElse("");

        constellationBox.setValue(currentConstellationName);

        grid.add(new Label("Nazwa:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Jasność:"), 0, 1);
        grid.add(brightnessField, 1, 1);
        grid.add(new Label("X:"), 0, 2);
        grid.add(xField, 1, 2);
        grid.add(new Label("Y:"), 0, 3);
        grid.add(yField, 1, 3);
        grid.add(new Label("Gwiazdozbiór:"), 0, 4);
        grid.add(constellationBox, 1, 4);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                try {
                    String newName = nameField.getText();
                    double newBrightness = Double.parseDouble(brightnessField.getText());
                    double newX = Double.parseDouble(xField.getText());
                    double newY = Double.parseDouble(yField.getText());
                    String newConstellation = constellationBox.getValue();

                    controller.updateStar(star.getName(), newName, newX, newY, newBrightness, newConstellation);
                } catch (NumberFormatException e) {
                    // Obsługa błędów formatowania liczby
                }
            }
            return null;
        });

        dialog.showAndWait();
        drawMap();
    }



    private void showAddStarDialog(double x, double y) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Dodaj Nową Gwiazdę");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField nameField = new TextField();
        TextField brightnessField = new TextField();
        TextField xField = new TextField(String.valueOf(x));
        TextField yField = new TextField(String.valueOf(y));
        ComboBox<String> constellationBox = new ComboBox<>();
        constellationBox.getItems().addAll(controller.getConstellationNames());

        grid.add(new Label("Nazwa:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Jasność:"), 0, 1);
        grid.add(brightnessField, 1, 1);
        grid.add(new Label("X:"), 0, 2);
        grid.add(xField, 1, 2);
        grid.add(new Label("Y:"), 0, 3);
        grid.add(yField, 1, 3);
        grid.add(new Label("Gwiazdozbiór:"), 0, 4);
        grid.add(constellationBox, 1, 4);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                try {
                    String newName = nameField.getText();
                    double newBrightness = Double.parseDouble(brightnessField.getText());
                    double newX = Double.parseDouble(xField.getText());
                    double newY = Double.parseDouble(yField.getText());
                    String constellation = constellationBox.getValue();

                    Star newStar = new Star(newName, newX, newY, newBrightness);
                    controller.addStar(newStar, constellation);
                } catch (NumberFormatException e) {
                    // Obsługa błędów formatowania liczby
                }
            }
            return null;
        });

        dialog.showAndWait();
        drawMap();
    }



    private Star findStarAtPosition(double x, double y) {
        for (Star star : controller.getStars()) {
            double distance = Math.sqrt(Math.pow(x - star.getXPosition(), 2) + Math.pow(y - star.getYPosition(), 2));
            if (distance < calculateStarRadius(star.getBrightness())) {
                return star;
            }
        }
        return null;
    }


    public void drawMap() {
        GraphicsContext gc = getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, getWidth(), getHeight());

        drawCoordinates(gc); // Dodaj to wywołanie
        drawStars(gc);
        drawConstellations(gc);
    }


    private void drawStars(GraphicsContext gc) {
        for (Star star : controller.getStars()) {
            drawStar(gc, star);
        }
    }

    private void drawStar(GraphicsContext gc, Star star) {
        double x = star.getXPosition();
        double y = star.getYPosition();
        double radius = calculateStarRadius(star.getBrightness());
        double innerRadius = radius / 2.5; // Wewnętrzny promień dla pięciokątnej gwiazdy
        int numPoints = 10; // Liczba punktów dla gwiazdy pięcioramiennej (5 ramion)

        double[] xPoints = new double[numPoints];
        double[] yPoints = new double[numPoints];

        for (int i = 0; i < numPoints; i++) {
            double angle = 2 * Math.PI / numPoints * i;
            double relRadius = i % 2 == 0 ? radius : innerRadius;
            xPoints[i] = x + relRadius * Math.sin(angle);
            yPoints[i] = y - relRadius * Math.cos(angle);
        }

        gc.setFill(Color.WHITE);
        gc.fillPolygon(xPoints, yPoints, numPoints);
    }


    private double calculateStarRadius(double brightness) {
        return 2 + brightness * 2;
    }

    private void drawConstellations(GraphicsContext gc) {
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(1);

        for (Constellation constellation : controller.getConstellations()) {
            Star previousStar = null;
            for (Star star : constellation.getStars()) {
                if (previousStar != null) {
                    gc.strokeLine(previousStar.getXPosition(), previousStar.getYPosition(),
                            star.getXPosition(), star.getYPosition());
                }
                previousStar = star;
            }
        }
    }


    private void addMouseMotionListener() {
        this.setOnMouseMoved(event -> {
            double mouseX = event.getX();
            double mouseY = event.getY();
            Star foundStar = null;

            List<Star> stars = controller.getStars();
            for (Star star : stars) {
                if (Math.abs(mouseX - star.getXPosition()) < 10 && Math.abs(mouseY - star.getYPosition()) < 10) {
                    foundStar = star;
                    break;
                }
            }

            if (foundStar != null && foundStar != currentHoveredStar) {
                currentHoveredStar = foundStar;
                pause.stop(); // Zatrzymaj poprzednie opóźnienie
                drawStarName(foundStar);
            } else if (foundStar == null && currentHoveredStar != null) {
                pause.setOnFinished(e -> {
                    hideStarName();
                    currentHoveredStar = null;
                });
                pause.playFromStart();
            }
        });
    }

    private void drawStarName(Star star) {
        GraphicsContext gc = getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillText(star.getName(), star.getXPosition() + 10, star.getYPosition() - 10);
    }

    private void hideStarName() {
        if (currentHoveredStar != null) {
            pause.setOnFinished(e -> {
                clearCanvas();
                drawMap(); // Rysuj wszystko od nowa
            });
            pause.playFromStart();
        }
    }

    private void clearCanvas() {
        GraphicsContext gc = getGraphicsContext2D();
        gc.clearRect(0, 0, getWidth(), getHeight());
    }

}

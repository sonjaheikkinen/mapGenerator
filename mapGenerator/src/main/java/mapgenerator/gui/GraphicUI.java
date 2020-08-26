package mapgenerator.gui;

import java.util.Random;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import mapgenerator.domain.Biomes;
import mapgenerator.domain.Map;
import mapgenerator.logic.ProgramHandler;

/**
 * Class creates the graphic user interface of the program
 */
public class GraphicUI {

    private Painter painter;
    private Canvas canvas;
    private GraphicsContext brush;
    private Biomes biomes;

    /**
     * Method creates a window and calls for method drawMap to draw the
     * generated map on screen.
     *
     * @param stage A window which shows things on screen.
     * @param map Contains all information of the generated map.
     */
    public GraphicUI(Random random, double waterlevel, Biomes biomes, Map map, int canvasSize) {
        this.biomes = biomes;
        this.canvas = new Canvas(canvasSize, canvasSize);
        this.brush = canvas.getGraphicsContext2D();
        this.painter = new Painter(map, biomes, canvasSize, waterlevel, brush, random);
    }

    public void start(Stage stage, ProgramHandler handler, int exponent) {
        Button newMapButton = createNewMapButton(handler, exponent);
        BorderPane layout = createLayout(newMapButton, canvas);
        painter.setBiomeDrawType("simple");
        painter.setDrawType("biome");
        painter.setShowBiome("none");
        painter.drawMap();
        Scene scene = new Scene(layout);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Method creates a scene layout from provided elements
     *
     * @param newMapButton A button element
     * @param canvas A canvas element
     * @return Produced layout
     */
    public BorderPane createLayout(Button newMapButton, Canvas canvas) {
        VBox options = createOptionBar(newMapButton);
        VBox selectBiome = createBiomeSelectorLayout();
        BorderPane layout = new BorderPane();
        layout.setRight(options);
        layout.setLeft(selectBiome);
        layout.setCenter(canvas);
        return layout;
    }

    public VBox createBiomeSelectorLayout() {
        VBox selectBiome = new VBox();
        Label select = new Label("Select biome to show");
        Button noBiome = new Button("None");
        noBiome.setOnAction(actionEvent -> {
            painter.setShowBiome("None");
            painter.drawMap();
        });
        GridPane biomeButtons = createBiomeButtonGrid(noBiome);
        selectBiome.getChildren().addAll(select, biomeButtons);
        return selectBiome;
    }

    public GridPane createBiomeButtonGrid(Button noBiome) {
        GridPane biomeButtons = new GridPane();
        String[][] biomeNames = biomes.getBiomeSelection();
        int row = 1;
        int col = 0;
        biomeButtons.add(noBiome, 0, 0);
        for (int i = 0; i < biomeNames.length; i++) {
            for (int j = 0; j < biomeNames[i].length; j++) {
                Button biomeButton = createBiomeButton(biomeNames[i][j]);
                biomeButtons.add(biomeButton, col, row);
                row++;
                if (row == 11) {
                    row = 0;
                    col++;
                }
            }
        }
        return biomeButtons;
    }

    public Button createBiomeButton(String biomeName) {
        Button biomeButton = new Button(biomeName);
        biomeButton.setOnAction(actionEvent -> {
            painter.setShowBiome(biomeName);
            painter.drawMap();
        });
        return biomeButton;
    }

    public VBox createOptionBar(Button newMapButton) {
        VBox options = new VBox();
        Label drawtype = new Label("Select biome drawing type:");
        HBox drawtypeButtons = createDrawTypeButtons();
        Label showButtons = new Label("Show selected noise map:");
        VBox noisemapButtons = createNoiseMapButtons();
        options.getChildren().addAll(newMapButton, drawtype, drawtypeButtons, showButtons, noisemapButtons);
        return options;
    }

    public HBox createDrawTypeButtons() {
        HBox drawtypeButtons = new HBox();
        Button simple = new Button("Simple");
        simple.setOnAction(actionEvent -> {
            painter.setBiomeDrawType("simple");
            painter.drawMap();
        });
        Button smooth = new Button("Smooth");
        smooth.setOnAction(actionEvent -> {
            painter.setBiomeDrawType("smooth");
            painter.drawMap();
        });
        drawtypeButtons.getChildren().addAll(simple, smooth);
        return drawtypeButtons;
    }

    public VBox createNoiseMapButtons() {
        VBox noiseMapButtons = new VBox();
        Button height = createDrawtypeChangingButton("Show heightmap", "height");
        Button moisture = createDrawtypeChangingButton("Show moisture", "moisture");
        Button none = createDrawtypeChangingButton("None", "biome");
        noiseMapButtons.getChildren().addAll(height, moisture, none);
        return noiseMapButtons;
    }

    public Button createDrawtypeChangingButton(String label, String drawtype) {
        Button button = new Button(label);
        button.setOnAction(actionEvent -> {
            painter.setDrawType(drawtype);
            painter.drawMap();
        });
        return button;
    }

    /**
     * Method creates a button and its eventhandler for producing new maps
     *
     * @param handler A ProgramHandler object which is in control of creating
     * necessary elements for creating new map
     * @param brush A graphicsContext element which draws the map on canvas
     * @param map Contains the information of the generated map
     * @param canvasSize Size of the canvas
     * @param multiplier Canvas and drawing size can be increased via multiplier
     * @return A button element which creates and draws a new map when pressed
     */
    public Button createNewMapButton(ProgramHandler handler, int exponent) {
        Button newMapButton = new Button("New map");
        newMapButton.setOnAction(actionEvent -> {
            handler.newMap(exponent);
            painter.drawMap();
        });
        return newMapButton;
    }

}

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
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import mapgenerator.domain.Biomes;
import mapgenerator.domain.Map;
import mapgenerator.logic.ProgramHandler;

/**
 * Class creates the graphic user interface of the program
 */
public class GraphicUI {

    private Random random;
    private double waterlevel;
    private Canvas canvas;
    private int canvasSize;
    private GraphicsContext brush;
    private Map mapStorage;
    private String biomeDrawType;
    private String drawType;
    private String showBiome;
    private Biomes biomes;

    /**
     * Method creates a window and calls for method drawMap to draw the
     * generated map on screen.
     *
     * @param stage A window which shows things on screen.
     * @param map Contains all information of the generated map.
     */
    public GraphicUI(Random random, double waterlevel, Biomes biomes) {
        this.random = random;
        this.waterlevel = waterlevel;
        this.biomeDrawType = "simple";
        this.drawType = "biome";
        this.showBiome = "none";
        this.biomes = biomes;
    }

    public void start(Stage stage, Map map, int canvasSize, ProgramHandler handler, int exponent) {
        this.canvas = new Canvas(canvasSize, canvasSize);
        this.brush = canvas.getGraphicsContext2D();
        this.mapStorage = map;
        this.canvasSize = canvasSize;
        Button newMapButton = createNewMapButton(handler, exponent);
        BorderPane layout = createLayout(newMapButton, canvas);
        drawMap();
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
            this.showBiome = "None";
            drawMap();
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
            this.showBiome = biomeName;
            drawMap();
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
            this.biomeDrawType = "simple";
            drawMap();
        });
        Button smooth = new Button("Smooth");
        smooth.setOnAction(actionEvent -> {
            this.biomeDrawType = "smooth";
            drawMap();
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
            this.drawType = drawtype;
            drawMap();
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
            drawMap();
        });
        return newMapButton;
    }

    /**
     * Draws a map on screen based on a map object
     *
     * @param brush Draws map on canvas defined in parent method
     * @param map Contains all information of the generated map
     * @param canvasSize Size of the canvas
     * @param multiplier Canvas and drawing size can be increased via multiplier
     */
    public void drawMap() {
        Long drawingStarts = System.nanoTime();
        double maxHeight = mapStorage.getMaxHeight();
        double maxMoisture = mapStorage.getMaxMoisture();
        Color[] colors = new Color[20];
        fillColorArray();
        for (int x = 0; x < canvasSize; x++) {
            for (int y = 0; y < canvasSize; y++) {
                drawMapCell(x, y, maxHeight, maxMoisture, colors);
            }
        }
        Long drawingEnds = System.nanoTime();
        Long drawingTime = drawingEnds - drawingStarts;
        System.out.println("Map drawn in " + drawingTime + " nanoseconds (" + (drawingTime / 1000000) + " milliseconds)");
    }

    public void drawMapCell(int x, int y, double maxHeight, double maxMoisture, Color[] colors) {
        double height = mapStorage.getMap()[x][y].getHeight();
        double moisture = mapStorage.getMap()[x][y].getMoisture();
        double landHeightRange = maxHeight - (waterlevel * maxHeight);
        double landHeight = height - (waterlevel * maxHeight);
        double heightShade = betweenZeroAndOne(height / maxHeight);
        double moistureShade = betweenZeroAndOne(moisture / maxMoisture);
        double biomeShade = betweenZeroAndOne(landHeight / landHeightRange);
        String biome = mapStorage.getMap()[x][y].getBiome();
        calculateWaterColor(heightShade);
        Color color = chooseColor(heightShade, moistureShade, biomeShade, biome);
        brush.setFill(color);
        brush.fillRect(x, y, 1, 1);
    }

    public Color chooseColor(double heightShade, double moistureShade, double biomeShade, String biome) {
        if (drawType.equals("height")) {
            int drawShade = (int) Math.round(heightShade * 255);
            return Color.grayRgb(drawShade);
        } else if (drawType.equals("moisture")) {
            int drawShade = (int) Math.round(moistureShade * 255);
            return Color.grayRgb(drawShade);
        } else {
            Color color = pickBiomeColor(biomeShade, biome);
            if (biome.equals(showBiome)) {
                color = Color.RED;
            }
            return color;
        }
    }

    /**
     * Picks correct brush color based on biome.
     *
     * @param shade A height map based integer which can be used to affect color
     * @param biome An integer telling which biome to base color on
     * @param shadow Has an effect of color brightness
     * @return brush color
     */

    public Color pickBiomeColor(double shade, String biome) {
        Color color;
        if (biomeDrawType.equals("smooth")) {
            fillColorArrayUsingShades((int) Math.round(shade * 255));
        }
        color = biomes.getBiomeColor(biome);
        color = color.deriveColor(random.nextInt(1), 0.7 + random.nextDouble() * 0.3, 0.95 + random.nextDouble() * 0.05, 1);
        return color;
    }

    public void fillColorArray() {
        biomes.getBiomeColors()[1].setColor(Color.rgb(212, 209, 197)); //sand
        biomes.getBiomeColors()[2].setColor(Color.rgb(168, 181, 141)); //beachGrass
        biomes.getBiomeColors()[3].setColor(Color.rgb(159, 194, 132)); //beachForest
        biomes.getBiomeColors()[4].setColor(Color.rgb(154, 181, 138)); //dryGrass
        biomes.getBiomeColors()[5].setColor(Color.rgb(91, 181, 85)); //grass
        biomes.getBiomeColors()[6].setColor(Color.rgb(152, 222, 138)); //sparseLeaf
        biomes.getBiomeColors()[7].setColor(Color.rgb(120, 173, 95)); //dryGrassWithTrees
        biomes.getBiomeColors()[8].setColor(Color.rgb(84, 184, 84)); //leaf
        biomes.getBiomeColors()[9].setColor(Color.rgb(63, 166, 92)); //mixedForest
        biomes.getBiomeColors()[10].setColor(Color.rgb(102, 140, 80)); //dryLeaf
        biomes.getBiomeColors()[11].setColor(Color.rgb(71, 125, 57)); //taigaPine
        biomes.getBiomeColors()[12].setColor(Color.rgb(56, 128, 88)); //taigaSpruce
        biomes.getBiomeColors()[13].setColor(Color.rgb(120, 153, 112)); //dryMountainForest
        biomes.getBiomeColors()[14].setColor(Color.rgb(112, 156, 112)); //mountainForest
        biomes.getBiomeColors()[15].setColor(Color.rgb(156, 184, 172)); //tundra
        biomes.getBiomeColors()[16].setColor(Color.rgb(130, 162, 163)); //bare
        biomes.getBiomeColors()[17].setColor(Color.rgb(140, 170, 168)); //bareTundra
        biomes.getBiomeColors()[18].setColor(Color.rgb(110, 142, 145)); //volcano   
        biomes.getBiomeColors()[19].setColor(Color.rgb(222, 234, 233)); //snow
    }

    public void fillColorArrayUsingShades(int shade) {
        int lightshade = betweenZeroAnd255(255 - shade);
        shade = betweenZeroAnd255(shade);
        biomes.getBiomeColors()[1].setColor(Color.rgb(Math.min(255, lightshade + 5), Math.max(0, lightshade - 10), Math.max(0, lightshade - 10))); //sand
        biomes.getBiomeColors()[2].setColor(Color.rgb(Math.min(255, lightshade + 5), Math.max(0, lightshade - 15), Math.max(0, lightshade - 15))); //beachGrass
        biomes.getBiomeColors()[3].setColor(Color.rgb(lightshade, Math.max(0, lightshade - 20), Math.max(0, lightshade - 20))); //beachForest
        biomes.getBiomeColors()[4].setColor(Color.rgb(Math.max(0, lightshade - 50), lightshade, Math.max(0, lightshade - 70))); //dryGrass
        biomes.getBiomeColors()[5].setColor(Color.rgb(Math.max(0, lightshade - 100), lightshade, Math.max(0, lightshade - 100))); //grass
        biomes.getBiomeColors()[6].setColor(Color.rgb(Math.max(0, lightshade - 90), lightshade, Math.max(0, lightshade - 120))); //sparseLeaf
        biomes.getBiomeColors()[7].setColor(Color.rgb(Math.max(0, lightshade - 60), lightshade, Math.max(0, lightshade - 80))); //dryGrassWithTrees
        biomes.getBiomeColors()[8].setColor(Color.rgb(Math.max(0, lightshade - 100), lightshade, Math.max(0, lightshade - 120))); //leaf
        biomes.getBiomeColors()[9].setColor(Color.rgb(0, lightshade, Math.max(0, lightshade - 100))); //mixedForest
        biomes.getBiomeColors()[10].setColor(Color.rgb(Math.max(0, lightshade - 60), lightshade, Math.max(0, lightshade - 80))); //dryLeaf
        biomes.getBiomeColors()[11].setColor(Color.rgb(Math.max(0, lightshade - 60), lightshade, Math.max(0, lightshade - 80))); //taigaPine
        biomes.getBiomeColors()[12].setColor(Color.rgb(Math.max(0, lightshade - 100), lightshade, Math.max(0, lightshade - 80))); //taigaSpruce
        biomes.getBiomeColors()[13].setColor(Color.rgb(Math.max(0, shade - 50), shade, Math.max(0, shade - 100))); //dryMountainForest
        biomes.getBiomeColors()[14].setColor(Color.rgb(0, shade, 0)); //mountainForest
        biomes.getBiomeColors()[15].setColor(Color.rgb(Math.max(0, shade - 60), shade, Math.max(0, shade - 40))); //tundra
        biomes.getBiomeColors()[16].setColor(Color.rgb(Math.max(0, shade - 60), Math.max(0, shade - 50), Math.max(0, shade - 60))); //bare
        biomes.getBiomeColors()[17].setColor(Color.rgb(Math.max(0, shade - 50), Math.max(0, shade - 40), Math.max(0, shade - 40))); //bareTundra
        biomes.getBiomeColors()[18].setColor(Color.rgb(Math.max(0, shade - 50), Math.max(0, shade - 50), Math.max(0, shade - 60))); //volcano   
        biomes.getBiomeColors()[19].setColor(Color.rgb(Math.min(255, shade + 10), Math.min(255, shade + 10), Math.min(255, shade + 20))); //snow
    }

    public void calculateWaterColor(double shade) {
        double blueShade = shade * 255;
        blueShade = Math.min(140, blueShade);
        blueShade = Math.max(100, blueShade);
        int waterBlue = (int) Math.round(blueShade);
        int waterGreen = (int) Math.round(0.75 * blueShade);
        biomes.getBiomeColors()[0].setColor(Color.rgb(0, waterGreen, waterBlue));
    }

    public double betweenZeroAndOne(double number) {
        if (number < 0) {
            return 0;
        }
        if (number > 1) {
            return 1;
        }
        return number;
    }
    
    public int betweenZeroAnd255(double number) {
        if (number < 0) {
            return 0;
        }
        if (number > 255) {
            return 255;
        }
        return (int) Math.round(number);
    }

}

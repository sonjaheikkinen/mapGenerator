package mapgenerator.gui;

import java.util.Arrays;
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
import mapgenerator.datastructures.MapCell;
import mapgenerator.domain.Biomes;
import mapgenerator.domain.Map;
import mapgenerator.logic.ProgramHandler;

/**
 * Class creates the graphic user interface of the program.
 */
public class GraphicUI {

    private Random random;
    private double waterlevel;
    private Canvas canvas;
    private int canvasSize;
    private GraphicsContext brush;
    private Map mapStorage;
    private int multiplier;
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

    public void start(Stage stage, Map map, int canvasSize, ProgramHandler handler, int multiplier, int exponent) {
        this.canvas = new Canvas(canvasSize, canvasSize);
        this.brush = canvas.getGraphicsContext2D();
        this.mapStorage = map;
        this.canvasSize = canvasSize;
        this.multiplier = multiplier;
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
                String biomeName = biomeNames[i][j];
                Button biomeButton = new Button(biomeName);
                biomeButton.setOnAction(actionEvent -> {
                    this.showBiome = biomeName;
                    drawMap();
                });
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
        Button height = new Button("Show heightmap");
        height.setOnAction(actionEvent -> {
            this.drawType = "height";
            drawMap();
        });
        Button moisture = new Button("Show moisture");
        moisture.setOnAction(actionEvent -> {
            this.drawType = "moisture";
            drawMap();
        });
        Button none = new Button("None");
        none.setOnAction(actionEvent -> {
            this.drawType = "biome";
            drawMap();
        });
        noiseMapButtons.getChildren().addAll(height, moisture, none);
        return noiseMapButtons;
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
        //colors = fillColorArray(colors);
        fillColorArray();
        for (int x = 0; x < canvasSize / multiplier; x++) {
            for (int y = 0; y < canvasSize / multiplier; y++) {
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
        double heightShade = height / maxHeight;
        double moistureShade = moisture / maxMoisture;
        double biomeShade = heightShade;
        if (!mapStorage.getMap()[x][y].isWater()) {
            biomeShade = landHeight / landHeightRange;
        }
        //int biome = mapStorage.getMap()[x][y].getBiome();
        String biome = mapStorage.getMap()[x][y].getBiome();

        biomeShade = Math.min(1, biomeShade);
        //colors = calculateWaterColor(colors, biomeShade);
        calculateWaterColor(heightShade);
        Color color;
        //System.out.println("drawtype is " + drawType);
        if (drawType.equals("height")) {
            int drawShade = (int) Math.round(heightShade * 255);
            color = Color.grayRgb(drawShade);
        } else if (drawType.equals("moisture")) {
            int drawShade = (int) Math.round(moistureShade * 255);
            color = Color.grayRgb(drawShade);
        } else {
            //color = pickColor(colors, biomeShade, biome);
            color = pickColor(biomeShade, biome);
            if (biome.equals(showBiome)) {
                color = Color.RED;
            }
        }
        brush.setFill(color);
        brush.fillRect(multiplier * x, multiplier * y, multiplier, multiplier);
    }

    /**
     * Picks correct brush color based on biome.
     *
     * @param shade A height map based integer which can be used to affect color
     * @param biome An integer telling which biome to base color on
     * @param shadow Has an effect of color brightness
     * @return brush color
     */
    /*
    public Color pickColor(Color[] colors, double shade, int biome) {
        //biomes: "0-water;1-sand;2-drygrass;3-grasss;4-leaf;5-taiga;6-tundra;7-bare;8-snow";
        //TODO: make color array a hashmap and get color by biome name instead of number?
        Color color;
        if (biomeDrawType.equals("smooth")) {
            colors = fillColorArrayUsingShades(colors, (int) Math.round(shade * 255));
        }
        color = colors[biome];
        color = color.deriveColor(random.nextInt(1), 0.7 + random.nextDouble() * 0.3, 0.95 + random.nextDouble() * 0.05, 1);
        return color;
    }
     */
 /*
    public Color pickColor(Color[] colors, double shade, String biome) {
        //biomes: "0-water;1-sand;2-drygrass;3-grasss;4-leaf;5-taiga;6-tundra;7-bare;8-snow";
        //TODO: make color array a hashmap and get color by biome name instead of number?
        Color color;
        if (biomeDrawType.equals("smooth")) {
            colors = fillColorArrayUsingShades(colors, (int) Math.round(shade * 255));
        }
        color = colors[biome];
        color = color.deriveColor(random.nextInt(1), 0.7 + random.nextDouble() * 0.3, 0.95 + random.nextDouble() * 0.05, 1);
        return color;
    }
     */
    public Color pickColor(double shade, String biome) {
        //biomes: "0-water;1-sand;2-drygrass;3-grasss;4-leaf;5-taiga;6-tundra;7-bare;8-snow";
        //TODO: make color array a hashmap and get color by biome name instead of number?
        Color color;
        if (biomeDrawType.equals("smooth")) {
            fillColorArrayUsingShades((int) Math.round(shade * 255));
        }
        color = biomes.getBiomeColor(biome);
        color = color.deriveColor(random.nextInt(1), 0.7 + random.nextDouble() * 0.3, 0.95 + random.nextDouble() * 0.05, 1);
        return color;
    }

    /*
    public Color[] fillColorArray(Color[] colors) {
        colors[1] = Color.rgb(212, 209, 197); //sand
        colors[2] = Color.rgb(168, 181, 141); //beachGrass
        colors[3] = Color.rgb(159, 194, 132); //beachForest
        colors[4] = Color.rgb(154, 181, 138); //dryGrass
        colors[5] = Color.rgb(91, 181, 85); //grass
        colors[6] = Color.rgb(152, 222, 138); //sparseLeaf
        colors[7] = Color.rgb(120, 173, 95); //dryGrassWithTrees
        colors[8] = Color.rgb(84, 184, 84); //leaf
        colors[9] = Color.rgb(63, 166, 92); //mixedForest
        colors[10] = Color.rgb(102, 140, 80); //dryLeaf
        colors[11] = Color.rgb(71, 125, 57); //taigaPine
        colors[12] = Color.rgb(56, 128, 88); //taigaSpruce
        colors[13] = Color.rgb(120, 153, 112); //dryMountainForest
        colors[14] = Color.rgb(112, 156, 112); //mountainForest
        colors[15] = Color.rgb(156, 184, 172); //tundra
        colors[16] = Color.rgb(130, 162, 163); //bare
        colors[17] = Color.rgb(140, 170, 168); //bareTundra
        colors[18] = Color.rgb(110, 142, 145); //volcano   
        colors[19] = Color.rgb(222, 234, 233); //snow
        return colors;
    }
     */
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

    /*
    public Color[] fillColorArrayUsingShades(Color[] colors, int shade) {
        int lightshade = Math.max(0, 255 - shade);
        lightshade = Math.min(255, lightshade);
        shade = Math.min(255, shade);
        shade = Math.max(0, shade);
        colors[1] = Color.rgb(Math.min(255, lightshade + 5), Math.max(0, lightshade - 10), Math.max(0, lightshade - 10)); //sand
        colors[2] = Color.rgb(Math.min(255, lightshade + 5), Math.max(0, lightshade - 15), Math.max(0, lightshade - 15)); //beachGrass
        colors[3] = Color.rgb(lightshade, Math.max(0, lightshade - 20), Math.max(0, lightshade - 20)); //beachForest
        colors[4] = Color.rgb(Math.max(0, lightshade - 50), lightshade, Math.max(0, lightshade - 70)); //dryGrass
        colors[5] = Color.rgb(Math.max(0, lightshade - 100), lightshade, Math.max(0, lightshade - 100)); //grass
        colors[6] = Color.rgb(Math.max(0, lightshade - 90), lightshade, Math.max(0, lightshade - 120)); //sparseLeaf
        colors[7] = Color.rgb(Math.max(0, lightshade - 60), lightshade, Math.max(0, lightshade - 80)); //dryGrassWithTrees
        colors[8] = Color.rgb(Math.max(0, lightshade - 100), lightshade, Math.max(0, lightshade - 120)); //leaf
        colors[9] = Color.rgb(0, lightshade, Math.max(0, lightshade - 100)); //mixedForest
        colors[10] = Color.rgb(Math.max(0, lightshade - 60), lightshade, Math.max(0, lightshade - 80)); //dryLeaf
        colors[11] = Color.rgb(Math.max(0, lightshade - 60), lightshade, Math.max(0, lightshade - 80)); //taigaPine
        colors[12] = Color.rgb(Math.max(0, lightshade - 100), lightshade, Math.max(0, lightshade - 80)); //taigaSpruce
        colors[13] = Color.rgb(Math.max(0, shade - 50), shade, Math.max(0, shade - 100)); //dryMountainForest
        colors[14] = Color.rgb(0, shade, 0); //mountainForest
        colors[15] = Color.rgb(x(0, shade - 60), Math.max(0, shade - 50), Math.max(0, shade - 60)Math.max(0, shade - 60), shade, Math.max(0, shade - 40)); //tundra
        colors[16] = Color.rgb(Math.max(0, shade - 60), Math.max(0, shade - 50), Math.max(0, shade - 60)); //bare
        colors[17] = Color.rgb(Math.max(0, shade - 50), Math.max(0, shade - 40), Math.max(0, shade - 40)); //bareTundra
        colors[18] = Color.rgb(Math.max(0, shade - 50), Math.max(0, shade - 50), Math.max(0, shade - 60)); //volcano
        colors[19] = Color.rgb(Math.min(255, shade + 10), Math.min(255, shade + 10), Math.min(255, shade + 20)); //snow
        return colors;
    }
     */
    public void fillColorArrayUsingShades(int shade) {
        int lightshade = Math.max(0, 255 - shade);
        lightshade = Math.min(255, lightshade);
        shade = Math.min(255, shade);
        shade = Math.max(0, shade);
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

    /*
    public Color[] calculateWaterColor(Color[] colors, double shade) {
        double blueShade = shade * 255;
        blueShade = Math.min(140, blueShade);
        blueShade = Math.max(100, blueShade);
        int waterBlue = (int) Math.round(blueShade);
        int waterGreen = (int) Math.round(0.75 * blueShade);
        colors[0] = Color.rgb(0, waterGreen, waterBlue);
        return colors;
    }
     */
    public void calculateWaterColor(double shade) {
        double blueShade = shade * 255;
        blueShade = Math.min(140, blueShade);
        blueShade = Math.max(100, blueShade);
        int waterBlue = (int) Math.round(blueShade);
        int waterGreen = (int) Math.round(0.75 * blueShade);
        biomes.getBiomeColors()[0].setColor(Color.rgb(0, waterGreen, waterBlue));
    }

}

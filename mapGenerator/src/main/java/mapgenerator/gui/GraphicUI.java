package mapgenerator.gui;

import java.util.Random;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import mapgenerator.datastructures.MapCell;
import mapgenerator.domain.Map;
import mapgenerator.logic.ProgramHandler;

/**
 * Class creates the graphic user interface of the program.
 */
public class GraphicUI {

    private Random random;
    private double waterlevel;

    /**
     * Method creates a window and calls for method drawMap to draw the
     * generated map on screen.
     *
     * @param stage A window which shows things on screen.
     * @param map Contains all information of the generated map.
     */
    public GraphicUI(Stage stage, Map map, int canvasSize, ProgramHandler handler, Random random, int multiplier, double waterlevel) {

        this.random = random;
        this.waterlevel = waterlevel;

        Canvas canvas = new Canvas(canvasSize, canvasSize);
        GraphicsContext brush = canvas.getGraphicsContext2D();
        Button newMapButton = createNewMapButton(handler, brush, map, canvasSize, multiplier);

        drawMap(brush, map, canvasSize, multiplier);

        BorderPane layout = createLayout(newMapButton, canvas);
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
        BorderPane layout = new BorderPane();
        layout.setRight(newMapButton);
        layout.setCenter(canvas);
        return layout;
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
    public Button createNewMapButton(ProgramHandler handler, GraphicsContext brush, Map map, int canvasSize, int multiplier) {
        Button newMapButton = new Button("New map");
        newMapButton.setOnAction(actionEvent -> {
            handler.newMap();
            drawMap(brush, map, canvasSize, multiplier);
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
    /*
    public void drawMap(GraphicsContext brush, Map map, int canvasSize, int multiplier) {
        double[][] heightMap = map.getHeightMap();
        double maxHeight = map.getMaxHeight();
        int[][] biomes = map.getBiomes();
        for (int x = 0; x < canvasSize / multiplier; x++) {
            for (int y = 0; y < canvasSize / multiplier; y++) {
                double height = heightMap[x][y];
                double shadow = calculateShadow(x, canvasSize, y, heightMap, biomes);
                double shade = height / maxHeight;
                int biome = biomes[x][y];
                shade = Math.min(255, shade);
                Color color = pickColor(shade, shadow, biome);
                brush.setFill(color);
                brush.fillRect(multiplier * x, multiplier * y, multiplier, multiplier);
            }
        }
    }
     */
    public void drawMap(GraphicsContext brush, Map map, int canvasSize, int multiplier) {
        Long drawingStarts = System.nanoTime();
        double maxHeight = map.getMaxHeight();
        for (int x = 0; x < canvasSize / multiplier; x++) {
            for (int y = 0; y < canvasSize / multiplier; y++) {
                double height = map.getMap()[x][y].getHeight();
                //double shadow = calculateShadow(x, canvasSize, y, map.getMap());
                double shadow = 1;
                double landHeightRange = maxHeight - (waterlevel * maxHeight);
                double landHeight = height - (waterlevel * maxHeight);
                double shade;
                if (!map.getMap()[x][y].isWater()) {
                    shade = landHeight / landHeightRange;
                } else {
                    shade = height / maxHeight;
                }
                int biome = map.getMap()[x][y].getBiome();
                shade = Math.min(1, shade);
                Color color = pickColor(shade, shadow, biome);
                brush.setFill(color);
                brush.fillRect(multiplier * x, multiplier * y, multiplier, multiplier);
            }
        }
        Long drawingEnds = System.nanoTime();
        Long drawingTime = drawingEnds - drawingStarts;
        System.out.println("Map drawn in " + drawingTime + " nanoseconds (" + (drawingTime / 1000000) + " milliseconds)");
    }

    //TODO improve this, calculates shadow
    /*
    public double calculateShadow(int x, int canvasSize, int y, double[][] heightMap, int[][] biomes) {
        double shadow = 1;
        if (x > 0 && x < canvasSize - 1 && y > 0 && y < canvasSize - 1) {
            if (heightMap[x - 1][y - 1] < heightMap[x][y] && biomes[x][y] != 0) {
                shadow = 0.8;
            } else if (heightMap[x - 1][y] < heightMap[x][y]
                    || heightMap[x][y] - 1 < heightMap[x][y]) {
                shadow = 0.9;
            }
        }
        return shadow;
    }
     */
    public double calculateShadow(int x, int canvasSize, int y, MapCell[][] map) {
        double shadow = 1;
        if (x > 0 && x < canvasSize - 1 && y > 0 && y < canvasSize - 1) {
            if (map[x - 1][y - 1].getHeight() < map[x][y].getHeight() && map[x][y].getBiome() != 0) {
                shadow = 0.8;
            } else if (map[x - 1][y].getHeight() < map[x][y].getHeight()
                    || map[x][y].getHeight() - 1 < map[x][y].getHeight()) {
                shadow = 0.9;
            }
        }
        return shadow;
    }

    /**
     * Picks correct brush color based on biome.
     *
     * @param shade A height map based integer which can be used to affect color
     * @param biome An integer telling which biome to base color on
     * @param shadow Has an effect of color brightness
     * @return brush color
     */
    public Color pickColor(double shade, double shadow, int biome) {
        double blueShade = shade * 255;
        blueShade = Math.min(140, blueShade);
        blueShade = Math.max(100, blueShade);
        //biomes: "0-water;1-sand;2-drygrass;3-grasss;4-leaf;5-taiga;6-tundra;7-bare;8-snow";
        //TODO: make color array a hashmap and get color by biome name instead of number?
        Color color;
        int waterBlue = (int) Math.round(blueShade);
        int waterGreen = (int) Math.round(0.75 * blueShade);
        Color[] colors = fillColorArray(waterBlue, waterGreen, (int) Math.round(shade * 255));
        color = colors[biome];
        color = color.deriveColor(random.nextInt(1), 0.7 + random.nextDouble() * 0.3, 0.95 + random.nextDouble() * 0.05, 1);
        //color = color.deriveColor(random.nextInt(1), 0.7 + random.nextDouble() * 0.3, shadow, 1);
        return color;
    }

    public Color[] fillColorArray(int waterBlue, int waterGreen, int shade) {
        int lightshade = Math.max(0, 255 - shade);
        lightshade = Math.min(255, lightshade);
        shade = Math.min(255, shade);
        shade = Math.max(0, shade);
        Color[] colors = new Color[20];
        colors[0] = Color.rgb(0, waterGreen, waterBlue);

        
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
         
        
        /*
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
        colors[15] = Color.rgb(Math.max(0, shade - 60), shade, Math.max(0, shade - 40)); //tundra
        colors[16] = Color.rgb(Math.max(0, shade - 60), Math.max(0, shade - 50), Math.max(0, shade - 60)); //bare
        colors[17] = Color.rgb(Math.max(0, shade - 50), Math.max(0, shade - 40), Math.max(0, shade - 40)); //bareTundra
        colors[18] = Color.rgb(Math.max(0, shade - 50), Math.max(0, shade - 50), Math.max(0, shade - 60)); //volcano
        colors[19] = Color.rgb(Math.min(255, shade + 10), Math.min(255, shade + 10), Math.min(255, shade + 20)); //snow
        */
        return colors;
    }

}

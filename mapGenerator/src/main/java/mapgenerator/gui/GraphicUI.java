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

    /**
     * Method creates a window and calls for method drawMap to draw the
     * generated map on screen.
     *
     * @param stage A window which shows things on screen.
     * @param map Contains all information of the generated map.
     */
    public GraphicUI(Stage stage, Map map, int canvasSize, ProgramHandler handler, Random random, int multiplier) {

        this.random = random;

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
                double shade = height / maxHeight;
                int biome = map.getMap()[x][y].getBiome();
                shade = Math.min(255, shade);
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
        blueShade = Math.min(170, blueShade);
        blueShade = Math.max(100, blueShade);
        //biomes: "0-water;1-sand;2-drygrass;3-grasss;4-leaf;5-taiga;6-tundra;7-bare;8-snow";
        //TODO: make color array a hashmap and get color by biome name instead of number?
        Color color;
        Color[] colors = fillColorArray(blueShade);
        color = colors[biome];
        //color = color.deriveColor(random.nextInt(1), 0.7 + random.nextDouble() * 0.3, 0.95 + random.nextDouble() * 0.05, 1);
        color = color.deriveColor(random.nextInt(1), 0.7 + random.nextDouble() * 0.3, shadow, 1);
        return color;
    }

    public Color[] fillColorArray(double blueShade) {
        Color[] colors = new Color[9];
        colors[0] = Color.rgb(0, (int) Math.round(0.75 * blueShade), (int) Math.round(blueShade));
        colors[1] = Color.rgb(231, 232, 207);
        colors[2] = Color.rgb(199, 209, 157);
        colors[3] = Color.rgb(122, 232, 100);
        colors[4] = Color.rgb(91, 194, 110);
        colors[5] = Color.rgb(28, 117, 66);
        colors[6] = Color.rgb(140, 171, 145);
        colors[7] = Color.rgb(208, 216, 217);
        colors[8] = Color.rgb(230, 240, 239);
        return colors;
    }

}

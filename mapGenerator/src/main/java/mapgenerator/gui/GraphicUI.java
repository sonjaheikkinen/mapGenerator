package mapgenerator.gui;

import java.util.ArrayList;
import java.util.Arrays;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import mapgenerator.domain.Map;
import mapgenerator.logic.ProgramHandler;

/**
 * Class creates the graphic user interface of the program.
 */
public class GraphicUI {

    /**
     * Method creates a window and calls for method drawMap to draw the
     * generated map on screen.
     *
     * @param stage A window which shows things on screen.
     * @param map Contains all information of the generated map.
     * @see mapgenerator.gui.GraphicUI#drawMap(GraphicsContext)
     */
    public GraphicUI(Stage stage, Map map, int canvasSize, ProgramHandler handler) {

        Canvas canvas = new Canvas(canvasSize, canvasSize);
        GraphicsContext brush = canvas.getGraphicsContext2D();
        Button newMapButton = createNewMapButton(handler, brush, map, canvasSize);

        drawMap(brush, map, canvasSize);

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
     * @return A button element which creates and draws a new map when pressed
     */
    public Button createNewMapButton(ProgramHandler handler, GraphicsContext brush, Map map, int canvasSize) {
        Button newMapButton = new Button("New map");
        newMapButton.setOnAction(actionEvent -> {
            handler.newMap();
            drawMap(brush, map, canvasSize);
        });
        return newMapButton;
    }

    /**
     * Draws a map on screen based on a map object
     *
     * @param brush Draws map on canvas defined in parent method
     * @param map Contains all information of the generated map
     */
    public void drawMap(GraphicsContext brush, Map map, int canvasSize) {
        double[][] heightMap = map.getHeightMap();
        boolean[][] water = map.getWater();
        int[][] biomes = createBiomes(map);
        for (int x = 0; x < canvasSize / 3; x++) {
            for (int y = 0; y < canvasSize / 3; y++) {
                int height = (int) Math.round(heightMap[x][y]);
                int shade = 255 / 100 * height;
                //Color green = Color.rgb(0, shade, 0);
                //Color blue = Color.rgb(0, 0, shade);
                //Color moisture = Color.rgb(map.getMoisture()[x][y] * 255 / 6, 0, 0);
                Color color = Color.rgb(0, 0, 0);
                Color blue = Color.rgb(0, 0, shade);
                brush.setFill(color);
                int biome = biomes[x][y];
                //biomes: "sand;grass;leaf;taiga;tundra;snow";
                Color sand = Color.rgb(231, 232, 207);
                Color grass = Color.rgb(199, 209, 157);
                Color leaf = Color.rgb(91, 194, 110);
                Color taiga = Color.rgb(27, 122, 43);
                Color tundra = Color.rgb(140, 171, 145);
                Color snow = Color.rgb(230, 240, 239);
                if (water[x][y]) {
                    color = blue;
                } else if (biome == 1) {
                    color = sand;
                } else if (biome == 2) {
                    color = grass;
                } else if (biome == 3) {
                    color = leaf;
                } else if (biome == 4) {
                    color = taiga;
                } else if (biome == 5) {
                    color = tundra;
                } else if (biome == 6) {
                    color = snow;
                }
                brush.setFill(color);
                brush.fillRect(x * 3, y * 3, 3, 3);
            }
        }
    }

    public int[][] createBiomes(Map map) {
        double[][] heightMap = map.getHeightMap();
        boolean[][] water = map.getWater();
        int[][] moisture = map.getMoisture();
        int[][] biomes = new int[heightMap.length][heightMap.length];
        int[] biomeSelection = new int[6];
        biomeSelection = fillBiomes(biomeSelection);
        //biomes: 1-desert, 2-grassland, 3-deciduousforest, 4-rainforest, 5-taiga, 6-baremountain, 7-tundra, 8-snowymountain
        //heights: 50-60=0, 60-70=1, 70-80=2, 80+=3
        //moisture: moisture - 1;
        for (int x = 0; x < heightMap.length; x++) {
            for (int y = 0; y < heightMap.length; y++) {
                if (!water[x][y]) {
                    int heightlevel;
                    double height = heightMap[x][y];
                    if (height >= 50 && height < 52) {
                        heightlevel = 0;
                    } else if (height >= 52 && height < 55) {
                        heightlevel = 1;
                    } else if (height >= 55 && height < 65) {
                        heightlevel = 2;
                    } else if (height >= 65 && height < 80){
                        heightlevel = 3;
                    } else if (height >= 80 && height < 90) {
                        heightlevel = 4;
                    } else {
                        heightlevel = 5;
                    }
                    biomes[x][y] = biomeSelection[heightlevel];
                }
            }
        }
        return biomes;
    }

    public int[] fillBiomes(int[] biomes) {
        //biomes: 1-desert, 2-grass, 3-leaf, 4-rain, 5-taiga, 6-mountain, 7-tundra, 8-snow
        /*
        String biomeString = "desert;grass;leaf;leaf;rain;rain;"
                + "desert;grass;grass;leaf;leaf;rain;"
                + "desert;grass;taiga;taiga;tundra;tundra;"
                + "mountain;taiga;tundra;tundra;snow;snow";
        */
        String biomeString = "sand;grass;leaf;taiga;tundra;snow";
        String[] biomeList = biomeString.split(";");
        
        int index = 0;
        for (int height = 0; height < 6; height++) {
            //for (int moisture = 0; moisture < 6; moisture++) {
                int biome = getBiomeNumber(biomeList[index]);
                biomes[height] = biome;
                index++;
            //}
        }
        /*
        biomes[0][0] = biomes[1][0] = biomes[2][0] = biomes[2][1] = 1;
        biomes[0][1] = biomes[1][1] = biomes[1][2] = biomes[2][2] = biomes[2][3] = 2;
        biomes[0][2] = biomes[0][3] = biomes[1][3] = biomes[1][4] = 3;
        biomes[0][4] = biomes[0][5] = biomes[1][5] = 4;
        biomes[2][4] = biomes[2][5] = 5;
        biomes[3][0] = biomes[3][1] = 6;
        biomes[3][2] = biomes[3][3] = 7;
        biomes[3][4] = biomes[3][5] = 8;
        */
        return biomes;
    }

    public int getBiomeNumber(String biomeName) {
        //biomes: 1-desert, 2-grass, 3-leaf, 4-rain, 5-taiga, 6-mountain, 7-tundra, 8-snow
        switch (biomeName) {
            case "sand":
                return 1;
            case "grass":
                return 2;
            case "leaf":
                return 3;
            case "taiga":
                return 4;
            case "tundra":
                return 5;
            case "snow":
                return 6;
            /*
            case "tundra":
                return 7;
            case "snow":
                return 8;
            */
        }
        return 0;
    }

}

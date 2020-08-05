package mapgenerator.gui;

import java.util.Random;
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
        int[][] biomes = map.getBiomes();
        for (int x = 0; x < canvasSize; x++) {
            for (int y = 0; y < canvasSize; y++) {
                int height = (int) Math.round(heightMap[x][y]);
                int shade = 255 / 100 * height;
                shade = Math.min(255, shade);
                Color color = Color.rgb(0, 0, 0);
                int biome = biomes[x][y];
                color = pickColor(x, y, color, shade, biome);
                brush.setFill(color);
                brush.fillRect(x, y, 1, 1);
            }
        }
    }

    public Color pickColor(int x, int y, Color color, int shade, int biome) {
        Random random = new Random();
        //biomes: "water;sand;grass;leaf;taiga;tundra;snow";
        Color water = Color.rgb(0, 0, shade);
        Color sand = Color.rgb(231, 232, 207);
        Color drygrass = Color.rgb(199, 209, 157);
        Color grass = Color.rgb(122, 232, 100);
        Color leaf = Color.rgb(91, 194, 110);
        Color taiga = Color.rgb(27, 122, 43);
        Color tundra = Color.rgb(140, 171, 145);
        Color bare = Color.rgb(208, 216, 217);
        Color snow = Color.rgb(230, 240, 239);
        if (biome == 0) {
            color = water;
        } else if (biome == 1) {
            color = sand;
        } else if (biome == 2) {
            color = drygrass;
        } else if (biome == 3) {
            color = grass;
        } else if (biome == 4) {
            color = leaf;
        } else if (biome == 5) {
            color = taiga;
        } else if (biome == 6) {
            color = tundra;
        } else if (biome == 7) {
            color = bare;
        } else if (biome == 8) {
            color = snow;
        }
        //color = color.deriveColor(random.nextInt(1), 0.7 + random.nextDouble() * 0.3, 0.95 + random.nextDouble() * 0.05, 1);
        return color;
    }



}

package mapgenerator.gui;

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

    public BorderPane createLayout(Button newMapButton, Canvas canvas) {
        BorderPane layout = new BorderPane();
        layout.setRight(newMapButton);
        layout.setCenter(canvas);
        return layout;
    }

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
        for (int x = 0; x < canvasSize / 10; x++) {
            for (int y = 0; y < canvasSize / 10; y++) {
                int height = (int) Math.round(heightMap[x][y]);
                int shade = 255 / 100 * height;
                Color green = Color.rgb(0, shade, 0);
                Color blue = Color.rgb(0, 0, shade);
                brush.setFill(green);
                if (water[x][y]) {
                    brush.setFill(blue);
                }
                brush.fillRect(x * 10, y * 10, 10, 10);
            }
        }
    }

}

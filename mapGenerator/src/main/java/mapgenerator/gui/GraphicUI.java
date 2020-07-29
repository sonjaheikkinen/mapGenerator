
package mapgenerator.gui;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import mapgenerator.domain.Map;

/**
 * Class creates the graphic user interface of the program.
 */
public class GraphicUI {

    /**
     * Method creates a window and calls for method drawMap to draw the generated map on screen.
     * @param stage A window which shows things on screen.
     * @param map Contains all information of the generated map.
     * @see mapgenerator.gui.GraphicUI#drawMap(GraphicsContext)
     */
    public GraphicUI(Stage stage, Map map, int canvasSize) {

        Canvas canvas = new Canvas(canvasSize, canvasSize);
        GraphicsContext brush = canvas.getGraphicsContext2D();

        BorderPane layout = new BorderPane();
        layout.setCenter(canvas);

        drawMap(brush, map, canvasSize);

        Scene scene = new Scene(layout);
        stage.setScene(scene);
        stage.show();

    }

    /**
     * Draws a map on screen based on a map object 
     * @param brush Draws map on canvas defined in parent method
     * @param map Contains all information of the generated map
     */
    public void drawMap(GraphicsContext brush, Map map, int canvasSize) {
        double[][] heightMap = map.getHeightMap();
        for (int x = 0; x < canvasSize / 10; x++) {
            for (int y = 0; y < canvasSize / 10; y++) {
                int height = (int) Math.round(heightMap[x][y]);
                int shade = 255 / 100 * height;
                Color green = Color.rgb(0, shade, 0);
                Color blue = Color.rgb(0, 0, shade);
                brush.setFill(green);
                if (heightMap[x][y] < 50) {
                    brush.setFill(blue);
                }
                brush.fillRect(x * 10, y * 10, 10, 10);
            }
        }
    }

}

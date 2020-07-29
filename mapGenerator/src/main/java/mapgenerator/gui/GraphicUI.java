
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
    public GraphicUI(Stage stage, Map map) {

        Canvas canvas = new Canvas(650, 650);
        GraphicsContext brush = canvas.getGraphicsContext2D();

        BorderPane layout = new BorderPane();
        layout.setCenter(canvas);

        drawMap(brush, map);

        Scene scene = new Scene(layout);
        stage.setScene(scene);
        stage.show();

    }

    /**
     * Draws a map on screen based on a map object 
     * @param brush Draws map on canvas defined in parent method
     * @param map Contains all information of the generated map
     */
    public void drawMap(GraphicsContext brush, Map map) {
        double[][] heightMap = map.getHeightMap();
        for (int x = 0; x < 65; x++) {
            for (int y = 0; y < 65; y++) {
                int height = (int) Math.round(heightMap[x][y]);
                int shade = 255 / 100 * height;
                Color color = Color.grayRgb(shade);
                brush.setFill(color);
                brush.fillRect(x * 10, y * 10, 10, 10);
            }
        }
    }

}

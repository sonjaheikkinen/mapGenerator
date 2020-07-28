/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapGenerator.gui;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import mapGenerator.domain.Map;

/**
 *
 * @author heisonja
 */
public class GraphicUI {
    
    int[][] heightMap;
    
    public GraphicUI (Stage stage, Map map) {     
        
        this.heightMap = map.getHeightMap();
      
        Canvas canvas = new Canvas(600, 600);
        GraphicsContext brush = canvas.getGraphicsContext2D();

        BorderPane layout = new BorderPane();
        layout.setCenter(canvas);
        
        drawMap(brush);
        
        Scene scene = new Scene(layout);
        stage.setScene(scene);
        stage.show();
     
    }
 
    
    public void drawMap(GraphicsContext brush) {
       for (int x = 0; x < 100; x++) {
           for (int y = 0; y < 100; y++) {
               brush.setFill(Color.grayRgb(this.heightMap[x][y]));
               brush.fillRect(x * 6, y * 6, 6, 6);
           }
       }
    }
   
    
}

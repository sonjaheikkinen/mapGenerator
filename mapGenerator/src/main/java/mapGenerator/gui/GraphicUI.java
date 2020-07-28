/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapGenerator.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author heisonja
 */
public class GraphicUI extends Application {
    
    @Override
    public void start(Stage stage) {
      
        Canvas canvas = new Canvas(650, 650);
        GraphicsContext brush = canvas.getGraphicsContext2D();
        Color color = Color.BLACK;

        BorderPane layout = new BorderPane();
        layout.setCenter(canvas);
        
        brush.setFill(color);
        brush.fillRect(100, 100, 100, 100);
        
        Scene scene = new Scene(layout);
        stage.setScene(scene);
        stage.show();
        
    }
    
}

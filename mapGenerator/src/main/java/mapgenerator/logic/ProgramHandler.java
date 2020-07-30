/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapgenerator.logic;

import java.util.Random;
import javafx.stage.Stage;
import mapgenerator.domain.Map;
import mapgenerator.gui.GraphicUI;

/**
 *
 * @author heisonja
 */
public class ProgramHandler {

    public void initialize(Stage stage) {
        Random random = new Random();
        Map map = new Map();
        int mapSizeExponent = 6;
        int canvasSize = (int) (Math.pow(2, mapSizeExponent) + 1) * 10;
        int mapSeed = 50;
        int mapRandomizerRange = 50;
        HeightmapGenerator hmGenerator = new HeightmapGenerator(random, mapSizeExponent, mapSeed, mapRandomizerRange);
        WaterGenerator waterGenerator = new WaterGenerator(mapSizeExponent);
        MapConstructor constructor = new MapConstructor(map, hmGenerator, waterGenerator);
        GraphicUI gui = new GraphicUI(stage, map, canvasSize);
    }

}

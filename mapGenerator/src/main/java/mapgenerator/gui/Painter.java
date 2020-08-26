/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapgenerator.gui;

import java.util.Random;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import mapgenerator.domain.Biomes;
import mapgenerator.domain.Map;

/**
 *
 * @author heisonja
 */
public class Painter {

    private Map mapStorage;
    private Biomes biomes;
    private int canvasSize;
    private double waterlevel;
    private GraphicsContext brush;
    private String biomeDrawType;
    private String drawType;
    private String showBiome;
    private Random random;

    public Painter(Map map, Biomes biomes, int canvasSize, double waterlevel, GraphicsContext brush, Random random) {
        this.mapStorage = map;
        this.biomes = biomes;
        this.canvasSize = canvasSize;
        this.waterlevel = waterlevel;
        this.brush = brush;
        this.random = random;
    }

    /**
     * Draws a map on screen based on a map object
     *
     * @param brush Draws map on canvas defined in parent method
     * @param map Contains all information of the generated map
     * @param canvasSize Size of the canvas
     * @param multiplier Canvas and drawing size can be increased via multiplier
     */
    public void drawMap() {
        Long drawingStarts = System.nanoTime();
        double maxHeight = mapStorage.getMaxHeight();
        double maxMoisture = mapStorage.getMaxMoisture();
        Color[] colors = new Color[20];
        fillColorArray();
        for (int x = 0; x < canvasSize; x++) {
            for (int y = 0; y < canvasSize; y++) {
                drawMapCell(x, y, maxHeight, maxMoisture, colors);
            }
        }
        Long drawingEnds = System.nanoTime();
        Long drawingTime = drawingEnds - drawingStarts;
        System.out.println("Map drawn in " + drawingTime + " nanoseconds (" + (drawingTime / 1000000) + " milliseconds)");
    }

    public void drawMapCell(int x, int y, double maxHeight, double maxMoisture, Color[] colors) {
        double height = mapStorage.getMap()[x][y].getHeight();
        double moisture = mapStorage.getMap()[x][y].getMoisture();
        double landHeightRange = maxHeight - (waterlevel * maxHeight);
        double landHeight = height - (waterlevel * maxHeight);
        double heightShade = betweenZeroAndOne(height / maxHeight);
        double moistureShade = betweenZeroAndOne(moisture / maxMoisture);
        double biomeShade = betweenZeroAndOne(landHeight / landHeightRange);
        String biome = mapStorage.getMap()[x][y].getBiome();
        calculateWaterColor(heightShade);
        Color color = chooseColor(heightShade, moistureShade, biomeShade, biome);
        brush.setFill(color);
        brush.fillRect(x, y, 1, 1);
    }

    public Color chooseColor(double heightShade, double moistureShade, double biomeShade, String biome) {
        if (drawType.equals("height")) {
            int drawShade = (int) Math.round(heightShade * 255);
            return Color.grayRgb(drawShade);
        } else if (drawType.equals("moisture")) {
            int drawShade = (int) Math.round(moistureShade * 255);
            return Color.grayRgb(drawShade);
        } else {
            Color color = pickBiomeColor(biomeShade, biome);
            if (biome.equals(showBiome)) {
                color = Color.RED;
            }
            return color;
        }
    }
    
        /**
     * Picks correct brush color based on biome.
     *
     * @param shade A height map based integer which can be used to affect color
     * @param biome An integer telling which biome to base color on
     * @param shadow Has an effect of color brightness
     * @return brush color
     */
    public Color pickBiomeColor(double shade, String biome) {
        Color color;
        if (biomeDrawType.equals("smooth")) {
            fillColorArrayUsingShades((int) Math.round(shade * 255));
        }
        color = biomes.getBiomeColor(biome);
        color = color.deriveColor(random.nextInt(1), 0.7 + random.nextDouble() * 0.3, 0.95 + random.nextDouble() * 0.05, 1);
        return color;
    }


    public void fillColorArray() {
        biomes.getBiomeColors()[1].setColor(Color.rgb(212, 209, 197)); //sand
        biomes.getBiomeColors()[2].setColor(Color.rgb(168, 181, 141)); //beachGrass
        biomes.getBiomeColors()[3].setColor(Color.rgb(159, 194, 132)); //beachForest
        biomes.getBiomeColors()[4].setColor(Color.rgb(154, 181, 138)); //dryGrass
        biomes.getBiomeColors()[5].setColor(Color.rgb(91, 181, 85)); //grass
        biomes.getBiomeColors()[6].setColor(Color.rgb(152, 222, 138)); //sparseLeaf
        biomes.getBiomeColors()[7].setColor(Color.rgb(120, 173, 95)); //dryGrassWithTrees
        biomes.getBiomeColors()[8].setColor(Color.rgb(84, 184, 84)); //leaf
        biomes.getBiomeColors()[9].setColor(Color.rgb(63, 166, 92)); //mixedForest
        biomes.getBiomeColors()[10].setColor(Color.rgb(102, 140, 80)); //dryLeaf
        biomes.getBiomeColors()[11].setColor(Color.rgb(71, 125, 57)); //taigaPine
        biomes.getBiomeColors()[12].setColor(Color.rgb(56, 128, 88)); //taigaSpruce
        biomes.getBiomeColors()[13].setColor(Color.rgb(120, 153, 112)); //dryMountainForest
        biomes.getBiomeColors()[14].setColor(Color.rgb(112, 156, 112)); //mountainForest
        biomes.getBiomeColors()[15].setColor(Color.rgb(156, 184, 172)); //tundra
        biomes.getBiomeColors()[16].setColor(Color.rgb(130, 162, 163)); //bare
        biomes.getBiomeColors()[17].setColor(Color.rgb(140, 170, 168)); //bareTundra
        biomes.getBiomeColors()[18].setColor(Color.rgb(110, 142, 145)); //volcano   
        biomes.getBiomeColors()[19].setColor(Color.rgb(222, 234, 233)); //snow
    }

    public void fillColorArrayUsingShades(int shade) {
        int lightshade = betweenZeroAnd255(255 - shade);
        shade = betweenZeroAnd255(shade);
        biomes.getBiomeColors()[1].setColor(Color.rgb(Math.min(255, lightshade + 5), Math.max(0, lightshade - 10), Math.max(0, lightshade - 10))); //sand
        biomes.getBiomeColors()[2].setColor(Color.rgb(Math.min(255, lightshade + 5), Math.max(0, lightshade - 15), Math.max(0, lightshade - 15))); //beachGrass
        biomes.getBiomeColors()[3].setColor(Color.rgb(lightshade, Math.max(0, lightshade - 20), Math.max(0, lightshade - 20))); //beachForest
        biomes.getBiomeColors()[4].setColor(Color.rgb(Math.max(0, lightshade - 50), lightshade, Math.max(0, lightshade - 70))); //dryGrass
        biomes.getBiomeColors()[5].setColor(Color.rgb(Math.max(0, lightshade - 100), lightshade, Math.max(0, lightshade - 100))); //grass
        biomes.getBiomeColors()[6].setColor(Color.rgb(Math.max(0, lightshade - 90), lightshade, Math.max(0, lightshade - 120))); //sparseLeaf
        biomes.getBiomeColors()[7].setColor(Color.rgb(Math.max(0, lightshade - 60), lightshade, Math.max(0, lightshade - 80))); //dryGrassWithTrees
        biomes.getBiomeColors()[8].setColor(Color.rgb(Math.max(0, lightshade - 100), lightshade, Math.max(0, lightshade - 120))); //leaf
        biomes.getBiomeColors()[9].setColor(Color.rgb(0, lightshade, Math.max(0, lightshade - 100))); //mixedForest
        biomes.getBiomeColors()[10].setColor(Color.rgb(Math.max(0, lightshade - 60), lightshade, Math.max(0, lightshade - 80))); //dryLeaf
        biomes.getBiomeColors()[11].setColor(Color.rgb(Math.max(0, lightshade - 60), lightshade, Math.max(0, lightshade - 80))); //taigaPine
        biomes.getBiomeColors()[12].setColor(Color.rgb(Math.max(0, lightshade - 100), lightshade, Math.max(0, lightshade - 80))); //taigaSpruce
        biomes.getBiomeColors()[13].setColor(Color.rgb(Math.max(0, shade - 50), shade, Math.max(0, shade - 100))); //dryMountainForest
        biomes.getBiomeColors()[14].setColor(Color.rgb(0, shade, 0)); //mountainForest
        biomes.getBiomeColors()[15].setColor(Color.rgb(Math.max(0, shade - 60), shade, Math.max(0, shade - 40))); //tundra
        biomes.getBiomeColors()[16].setColor(Color.rgb(Math.max(0, shade - 60), Math.max(0, shade - 50), Math.max(0, shade - 60))); //bare
        biomes.getBiomeColors()[17].setColor(Color.rgb(Math.max(0, shade - 50), Math.max(0, shade - 40), Math.max(0, shade - 40))); //bareTundra
        biomes.getBiomeColors()[18].setColor(Color.rgb(Math.max(0, shade - 50), Math.max(0, shade - 50), Math.max(0, shade - 60))); //volcano   
        biomes.getBiomeColors()[19].setColor(Color.rgb(Math.min(255, shade + 10), Math.min(255, shade + 10), Math.min(255, shade + 20))); //snow
    }

    public void calculateWaterColor(double shade) {
        double blueShade = shade * 255;
        blueShade = Math.min(140, blueShade);
        blueShade = Math.max(100, blueShade);
        int waterBlue = (int) Math.round(blueShade);
        int waterGreen = (int) Math.round(0.75 * blueShade);
        biomes.getBiomeColors()[0].setColor(Color.rgb(0, waterGreen, waterBlue));
    }

    public double betweenZeroAndOne(double number) {
        if (number < 0) {
            return 0;
        }
        if (number > 1) {
            return 1;
        }
        return number;
    }

    public int betweenZeroAnd255(double number) {
        if (number < 0) {
            return 0;
        }
        if (number > 255) {
            return 255;
        }
        return (int) Math.round(number);
    }

    public void setBiomeDrawType(String biomeDrawType) {
        this.biomeDrawType = biomeDrawType;
    }

    public void setDrawType(String drawType) {
        this.drawType = drawType;
    }

    public void setShowBiome(String showBiome) {
        this.showBiome = showBiome;
    }

}

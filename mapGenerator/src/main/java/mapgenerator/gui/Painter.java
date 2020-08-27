package mapgenerator.gui;

import java.util.Random;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import mapgenerator.domain.BiomeColor;
import mapgenerator.domain.Biomes;
import mapgenerator.domain.Map;

/**
 * Class is responsible for drawing maps on canvas.
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

    /**
     * Constructor initializes all necessary variables.
     *
     * @param map
     * @param biomes
     * @param canvasSize
     * @param waterlevel
     * @param brush
     * @param random
     */
    public Painter(Map map, Biomes biomes, int canvasSize, double waterlevel, GraphicsContext brush, Random random) {
        this.mapStorage = map;
        this.biomes = biomes;
        this.canvasSize = canvasSize;
        this.waterlevel = waterlevel;
        this.brush = brush;
        this.random = random;
    }

    /**
     * Draws a map on screen. Gets the maximum values of height and moisture,
     * then goes trough every cell in the map and draws a rectangle the color of
     * which is based on the information stored in the mapCell of the location
     * and the maximum height and moisture values of the map.
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
        System.out.println("");
    }

    /**
     * Draws a rectangle on screen based on a mapCell object. The placement of
     * the rectange is based on the coordinates of the mapCell- The color of the
     * rectangle is based on information contained in mapCell and the maximum
     * values of height and moisture.
     *
     * @param x
     * @param y
     * @param maxHeight
     * @param maxMoisture
     * @param colors
     */
    public void drawMapCell(int x, int y, double maxHeight, double maxMoisture, Color[] colors) {
        double height = mapStorage.getMap()[x][y].getHeight();
        double moisture = mapStorage.getMap()[x][y].getMoisture();
        double landHeightRange = maxHeight - (waterlevel * maxHeight);
        double landHeight = height - (waterlevel * maxHeight);
        double heightShade = betweenZeroAndOne(height / maxHeight);
        double moistureShade = betweenZeroAndOne(moisture / maxMoisture);
        //double biomeShade = betweenZeroAndOne(landHeight / landHeightRange);
        double biomeShade = heightShade;
        String biome = mapStorage.getMap()[x][y].getBiome();
        calculateWaterColor(heightShade);
        Color color = chooseColor(heightShade, moistureShade, biomeShade, biome);
        brush.setFill(color);
        brush.fillRect(x, y, 1, 1);
    }

    /**
     * Chooses the color of the rectangle based on given information. If
     * drawtype is height, then the color is a shade of grey based on
     * heightShade. If drawtype is moisture, then the color is a shade of grey
     * based on moistureShade. If drawtype is biome the method calls for another
     * method to choose the color of the biome. After that if the biome in this
     * location is biome that is selected for showing, the color is set to red.
     *
     * @param heightShade
     * @param moistureShade
     * @param biomeShade
     * @param biome
     * @return
     */
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
     * Picks correct brush color based on biome. If biomeDrawType is smooth, the
     * new biome colors are calculated based on shade.
     *
     * @param shade A height map based integer which can be used to affect color
     * @param biome An integer telling which biome to base color on
     * @return brush color
     */
    public Color pickBiomeColor(double shade, String biome) {
        Color color;
        if (biomeDrawType.equals("shaded")) {
            fillColorArrayUsingShades((int) Math.round(shade * 255));
        }
        color = getBiomeColor(biome);
        color = color.deriveColor(random.nextInt(1), 0.7 + random.nextDouble() * 0.3, 0.95 + random.nextDouble() * 0.05, 1);
        return color;
    }

    /**
     * Sets a different color to every biome.
     */
    public void fillColorArray() {
        biomes.getBiomeColors()[1].setColor(Color.rgb(212, 230, 200)); //sand
        biomes.getBiomeColors()[2].setColor(Color.rgb(180, 230, 141)); //beachGrass
        biomes.getBiomeColors()[3].setColor(Color.rgb(140, 194, 132)); //beachForest
        biomes.getBiomeColors()[4].setColor(Color.rgb(154, 181, 138)); //dryGrass
        biomes.getBiomeColors()[5].setColor(Color.rgb(120, 200, 140)); //grass
        biomes.getBiomeColors()[6].setColor(Color.rgb(152, 222, 138)); //sparseLeaf
        biomes.getBiomeColors()[7].setColor(Color.rgb(120, 173, 95)); //dryGrassWithTrees
        biomes.getBiomeColors()[8].setColor(Color.rgb(84, 184, 84)); //leaf
        biomes.getBiomeColors()[9].setColor(Color.rgb(63, 166, 92)); //mixedForest
        biomes.getBiomeColors()[10].setColor(Color.rgb(102, 140, 80)); //dryLeaf
        biomes.getBiomeColors()[11].setColor(Color.rgb(71, 125, 57)); //taigaPine
        biomes.getBiomeColors()[12].setColor(Color.rgb(56, 128, 88)); //taigaSpruce
        biomes.getBiomeColors()[13].setColor(Color.rgb(120, 153, 112)); //dryMountainForest
        biomes.getBiomeColors()[14].setColor(Color.rgb(90, 153, 100)); //mountainForest
        biomes.getBiomeColors()[15].setColor(Color.rgb(100, 180, 130)); //tundra
        biomes.getBiomeColors()[16].setColor(Color.rgb(130, 162, 163)); //bare
        biomes.getBiomeColors()[17].setColor(Color.rgb(130, 162, 163)); //bare
        biomes.getBiomeColors()[18].setColor(Color.rgb(140, 180, 168)); //bareTundra
        biomes.getBiomeColors()[19].setColor(Color.rgb(110, 142, 145)); //volcano   
        biomes.getBiomeColors()[20].setColor(Color.rgb(222, 234, 233)); //snow
        biomes.getBiomeColors()[21].setColor(Color.rgb(222, 234, 233)); //snow
    }

    /**
     * Sets a color to every biome that is calculated using a shade which varies
     * based on the height of the location.
     *
     * @param shade
     */
    public void fillColorArrayUsingShades(int shade) {
        shade = betweenZeroAnd255(shade);
        biomes.getBiomeColors()[1].setColor(Color.rgb(Math.min(255, shade + 100), Math.min(255, shade + 100), Math.min(255, shade + 70))); //sand
        biomes.getBiomeColors()[2].setColor(Color.rgb(Math.min(255, shade + 60), Math.min(255, shade + 100), Math.min(255, shade + 20))); //beachGrass
        biomes.getBiomeColors()[3].setColor(Color.rgb(shade, Math.min(255, shade + 50), Math.max(0, shade - 20))); //beachForest
        biomes.getBiomeColors()[4].setColor(Color.rgb(Math.max(0, shade - 10), Math.min(255, shade + 30), Math.max(0, shade - 30))); //dryGrass
        biomes.getBiomeColors()[5].setColor(Color.rgb(Math.max(0, shade - 30), Math.min(255, shade + 50), Math.max(0, shade - 10))); //grass
        biomes.getBiomeColors()[6].setColor(Color.rgb(Math.min(255, shade), Math.min(255, shade + 100), Math.max(0, shade - 20))); //sparseLeaf
        biomes.getBiomeColors()[7].setColor(Color.rgb(Math.max(0, shade - 40), Math.min(255, shade + 20), Math.max(0, shade - 60))); //dryGrassWithTrees
        biomes.getBiomeColors()[8].setColor(Color.rgb(Math.max(0, shade - 60), Math.min(255, shade + 30), Math.max(0, shade - 60))); //leaf
        biomes.getBiomeColors()[9].setColor(Color.rgb(0, Math.min(255, shade + 20), Math.max(0, shade - 50))); //mixedForest
        biomes.getBiomeColors()[10].setColor(Color.rgb(Math.max(0, shade - 80), Math.max(0, shade - 40), Math.max(0, shade - 100))); //dryDeciduous
        biomes.getBiomeColors()[11].setColor(Color.rgb(Math.max(0, shade - 100), Math.max(0, shade - 40), Math.max(0, shade - 120))); //Pine
        biomes.getBiomeColors()[12].setColor(Color.rgb(Math.max(0, shade - 120), Math.max(0, shade - 50), Math.max(0, shade - 90))); //taigaSpruce
        biomes.getBiomeColors()[13].setColor(Color.rgb(Math.max(0, shade - 70), Math.max(0, shade - 40), Math.max(0, shade - 90))); //dryMountainForest
        biomes.getBiomeColors()[14].setColor(Color.rgb(Math.max(0, shade - 90), Math.max(0, shade - 30), Math.max(0, shade - 100))); //mountainForest
        biomes.getBiomeColors()[15].setColor(Color.rgb(Math.max(0, shade - 60), Math.max(0, shade - 5), Math.max(0, shade - 50))); //tundra
        biomes.getBiomeColors()[16].setColor(Color.rgb(Math.max(0, shade - 80), Math.max(0, shade - 60), Math.max(0, shade - 60))); //bare
        biomes.getBiomeColors()[17].setColor(Color.rgb(Math.max(0, shade - 80), Math.max(0, shade - 60), Math.max(0, shade - 60))); //bare
        biomes.getBiomeColors()[18].setColor(Color.rgb(Math.max(0, shade - 60), Math.max(0, shade - 30), Math.max(0, shade - 40))); //bareTundra
        biomes.getBiomeColors()[19].setColor(Color.rgb(Math.max(0, shade - 90), Math.max(0, shade - 80), Math.max(0, shade - 70))); //volcano   
        biomes.getBiomeColors()[20].setColor(Color.rgb(Math.min(255, shade + 5), Math.min(255, shade + 5), Math.min(255, shade + 10))); //snow
        biomes.getBiomeColors()[21].setColor(Color.rgb(Math.min(255, shade + 5), Math.min(255, shade + 5), Math.min(255, shade + 10))); //snow
    }

    /**
     * Calculates the color of water using a shade which varies based on the
     * height of the location.
     *
     * @param shade
     */
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

    /**
     * Returns the color currently paired with given biome.
     *
     * @param biome
     * @return
     */
    public Color getBiomeColor(String biome) {
        BiomeColor[] biomeColors = biomes.getBiomeColors();
        for (int i = 0; i < biomeColors.length; i++) {
            if (biomeColors[i].getBiome().equals(biome)) {
                return biomeColors[i].getColor();
            }
        }
        return biomeColors[0].getColor();
    }

}

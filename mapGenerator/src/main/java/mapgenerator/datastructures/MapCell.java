/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapgenerator.datastructures;

/**
 *
 * @author heisonja
 */
public class MapCell {

    private int x;
    private int y;
    private double height;
    private double moisture;
    private double roughener;
    private boolean water;
    //private int biome;
    private String biome;

    public double getNoiseValue(String attribute) {
        if (attribute.equals("height")) {
            return height;
        } else if (attribute.equals("moisture")) {
            return moisture;
        } else if (attribute.equals("roughener")) {
            return roughener;
        }
        return 0;
    }

    public void setNoiseValue(String attribute, double value) {
        if (attribute.equals("height")) {
            this.height = value;
        } else if (attribute.equals("moisture")) {
            this.moisture = value;
        } else if (attribute.equals("roughener")) {
            this.roughener = value;
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getMoisture() {
        return moisture;
    }

    public void setMoisture(double moisture) {
        this.moisture = moisture;
    }

    public double getRoughener() {
        return roughener;
    }

    public void setRoughener(double roughener) {
        this.roughener = roughener;
    }

    public boolean isWater() {
        return water;
    }

    public void setWater(boolean water) {
        this.water = water;
    }

    /*
    public int getBiome() {
        return biome;
    }

    public void setBiome(int biome) {
        this.biome = biome;
    }
     */
    public String getBiome() {
        return biome;
    }

    public void setBiome(String biome) {
        this.biome = biome;
    }

}

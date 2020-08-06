/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapgenerator.math;

/**
 *
 * @author heisonja
 */
public class Calculator {

    public int min(int val1, int val2) {
        if (val1 < val2) {
            return val1;
        }
        return val2;
    }

    public double min(double val1, double val2) {
        if (val1 < val2) {
            return val1;
        }
        return val2;
    }

    public int max(int val1, int val2) {
        if (val1 > val2) {
            return val1;
        }
        return val2;
    }

    public double max(double val1, double val2) {
        if (val1 > val2) {
            return val1;
        }
        return val2;
    }

    public int pow(int value, int exponent) {
        for (int i = 0; i < exponent; i++) {
            value = value * exponent;
        }
        return value;
    }

    public double pow(double value, double exponent) {
        for (int i = 0; i < exponent; i++) {
            value = value * exponent;
        }
        return value;
    }

}

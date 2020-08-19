package mapgenerator.math;

/**
 * Class is used to make simple calculations needed in the program.
 */
public class Calculator {

    /**
     * Returns smaller of the two values.
     *
     * @param val1
     * @param val2
     * @return
     */
    public int min(int val1, int val2) {
        if (val1 < val2) {
            return val1;
        }
        return val2;
    }

    /**
     * Returns smaller of the two values.
     *
     * @param val1
     * @param val2
     * @return
     */
    public double min(double val1, double val2) {
        if (val1 < val2) {
            return val1;
        }
        return val2;
    }

    /**
     * Returns bigger of the two values.
     *
     * @param val1
     * @param val2
     * @return
     */
    public int max(int val1, int val2) {
        if (val1 > val2) {
            return val1;
        }
        return val2;
    }

    /**
     * Returns bigger of the two values.
     *
     * @param val1
     * @param val2
     * @return
     */
    public double max(double val1, double val2) {
        if (val1 > val2) {
            return val1;
        }
        return val2;
    }

    /**
     * Raises the given value to the power of given exponent
     *
     * @param value
     * @param exponent
     * @return
     */
    public int pow(int value, int exponent) {
        int result = value;
        if (exponent > 0) {
            for (int i = 0; i < exponent - 1; i++) {
                result = result * value;
            }
        } else if (exponent < 0) {
            for (int i = 0; i < (-1) * exponent - 1; i++) {
                result = result * value;
            }
            result = 1 / result;
        } else {
            result = 1;
        }
        return result;
    }

    /**
     * Raises the given value to the power of given exponent
     *
     * @param value
     * @param exponent
     * @return
     */
    public double pow(double value, double exponent) {
        double result = value;
        if (exponent > 0) {
            for (int i = 0; i < exponent - 1; i++) {
                result = result * value;
            }
        } else if (exponent < 0) {
            for (int i = 0; i < (-1) * exponent - 1; i++) {
                result = result * value;
            }
            result = 1 / result;
        } else {
            result = 1;
        }
        return result;
    }

}

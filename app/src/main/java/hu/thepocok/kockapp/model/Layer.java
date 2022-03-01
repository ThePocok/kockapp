package hu.thepocok.kockapp.model;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Representing a column or row of the cube. <br>
 * It contains the colors from left to right
 */
public class Layer {
    private final int dimension;
    private ArrayList<Color> colors;

    public Layer(Color... colors) {
        this.colors = new ArrayList<>(Arrays.asList(colors));
        dimension = colors.length;
    }

    public Layer(ArrayList<Color> colors) {
        this.colors = (ArrayList<Color>) colors.clone();
        this.dimension = colors.size();
    }

    public Layer(Color color, int dimension) {
        this.colors = new ArrayList<>();
        this.dimension = dimension;
        for (int i = 0; i < dimension; i++) {
            colors.add(color);
        }
    }

    public Color getNthPiece(int n) {
        return colors.get(n);
    }

    public void setNthPiece(int n, Color color) {
        colors.set(n, color);
    }

    public ArrayList<Color> getDataSet() {
        return colors;
    }

    public void copyTo(Layer layer) {
        for (int i = 0; i < this.dimension; i++) {
            this.setNthPiece(i, layer.getNthPiece(i));
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Color color : colors) {
            sb.append(color.stringValue + " ");
        }
        sb.append("\n");
        return sb.toString();
    }
}

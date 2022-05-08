package hu.thepocok.kockapp.model.cube.component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Representing a column or row of the cube. <br>
 * It contains the colors from left to right
 */
public class Layer implements Serializable {
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
            if (layer.getNthPiece(i) == Color.EMPTY) {
                continue;
            }
            this.setNthPiece(i, layer.getNthPiece(i));
        }
    }

    public Layer reverse() {
        Collections.reverse(colors);
        return this;
    }

    public boolean hasColor(Color colorToFind) {
        boolean l = false;

        for (Color c : colors) {
            l = l || c.equals(colorToFind);
        }

        return l;
    }

    public List<Integer> findColor(Color colorToFind) {
        ArrayList<Integer> indexes = new ArrayList<>();

        for (int i = 0; i < colors.size(); i++) {
            if (colors.get(i).equals(colorToFind)) {
                indexes.add(i);
            }
        }

        return indexes;
    }

    /**
     * This method is used to wildcard the two ends of the layer.
     * It helps rotating the face layer by layer.
     * @param n The number of pieces, that should be wildcarded at both ends of the layer
     */
    public Layer replaceWithEmpty(int n) {
        n = n / 2;
        for(int i = 0; i < n; i++) {
            colors.set(i, Color.EMPTY);
            colors.set(colors.size() - 1 - i, Color.EMPTY);
        }
        return this;
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

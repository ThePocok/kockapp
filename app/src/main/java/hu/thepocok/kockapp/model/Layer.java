package hu.thepocok.kockapp.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Layer {
    private int dimension;
    private ArrayList<Color> colors;

    public Layer(Color... colors) {
        this.colors = new ArrayList<>(Arrays.asList(colors));
        dimension = colors.length;
    }

    public Layer(ArrayList<Color> colors) {
        this.colors = new ArrayList<>();
        Collections.copy(this.colors, colors);
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

    public void reverse(){
        ArrayList<Color> newOrder = new ArrayList<>();
        for (int i = dimension; i > 0; i--) {
            newOrder.add(colors.get(i));
        }
        colors = newOrder;
    }

    public static void copy(Layer source, Layer destination, boolean reverseOrder) {
        if (reverseOrder)
            source.reverse();

        for (int i = 0; i < source.dimension; i++) {
            destination.setNthPiece(i, source.getNthPiece(i));
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

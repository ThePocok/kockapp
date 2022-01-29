package hu.thepocok.kockapp.model;

import java.util.ArrayList;
import java.util.Arrays;

public class Face {
    private int dimensions;
    private ArrayList<Layer> layers;

    public Face(Layer... layers) {
        this.layers = new ArrayList<>(Arrays.asList(layers));
        this.dimensions = layers.length;
    }

    public Layer getNthRow(int n) {
        return layers.get(n);
    }

    public Layer getNthColumn(int n) {
        ArrayList<Color> colors = new ArrayList<>();
        for (Layer layer : layers) {
            colors.add(layer.getNthPiece(n));
        }
        return new Layer(colors);
    }

    public void setNthRow(int n, Layer layer, boolean reverseOrder) {
        Layer.copy(layers.get(n), layer, reverseOrder);
    }

    public void setNthColumn(int n, Layer layer, boolean reverseOrder) {
        if (reverseOrder)
            layer.reverse();

        int i = 0;
        for (Layer l : layers) {
            l.setNthPiece(n, layer.getNthPiece(i++));
        }
    }

    public int getDimensions() {
        return dimensions;
    }

    public static Face generateFace(Color color, int dimension) {
        ArrayList<Layer> layers = new ArrayList<>();
        for (int i = 0; i < dimension; i++) {
            Layer layer = new Layer(color, dimension);
            layers.add(layer);
        }

        return new Face(layers.toArray(new Layer[dimension]));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Layer layer : layers) {
            sb.append(layer.toString());
        }

        return sb.toString();
    }
}

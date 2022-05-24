package hu.thepocok.kockapp.model.cube.component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * Representing a side of the cube. <br>
 * It contains the layers from top to bottom.
 */
public class Face implements Serializable {
    private Color faceColor;
    private final int dimensions;
    private final ArrayList<Layer> layers;

    public Face(Layer... layers) {
        this.layers = new ArrayList<>(Arrays.asList(layers));
        this.dimensions = layers.length;
    }

    public Face(Color faceColor, Layer... layers) {
        this(layers);
        this.faceColor = faceColor;
    }

    public Layer getNthRow(int n) {
        return new Layer(layers.get(n).getDataSet());
    }

    public Layer getNthColumn(int n) {
        ArrayList<Color> colors = new ArrayList<>();
        for (Layer layer : layers) {
            colors.add(layer.getNthPiece(n));
        }
        return new Layer(colors);
    }

    public ArrayList<Position> getNthRowPositions(int n) {
        ArrayList<Position> positions = new ArrayList<>();

        for (int i = 0; i < dimensions; i++) {
            Position p = new Position(layers.get(n).getNthPiece(i), faceColor, n, i);
            positions.add(p);
        }

        return positions;
    }

    public ArrayList<Position> getNthColumnPositions(int n) {
        ArrayList<Position> positions = new ArrayList<>();
        Layer layer = getNthColumn(n);

        for (int i = 0; i < dimensions; i++) {
            Position p = new Position(layer.getNthPiece(i), faceColor, i, n);
            positions.add(p);
        }

        return positions;
    }

    public void setNthRow(int n, Layer layer) {
        layers.get(n).copyTo(layer);
    }

    public void setNthColumn(int n, Layer layer) {
        int i = 0;
        for (Layer l : layers) {
            if (l.getNthPiece(i) == Color.EMPTY) {
                i++;
                continue;
            }
            l.setNthPiece(n, layer.getNthPiece(i++));
        }
    }

    public int getDimensions() {
        return dimensions;
    }

    public int getColorCount(Color color) {
        int n = 0;
        for (Layer layer : layers) {
            for (Color c : layer.getDataSet()) {
                if (c.equals(color)) {
                    n++;
                }
            }
        }

        return n;
    }

    public static Face generateFace(Color color, int dimension) {
        ArrayList<Layer> layers = new ArrayList<>();
        for (int i = 0; i < dimension; i++) {
            Layer layer = new Layer(color, dimension);
            layers.add(layer);
        }

        return new Face(color, layers.toArray(new Layer[dimension]));
    }

    public void rotateClockwise() {
        for (int i = dimensions; i > 1; i = i-2) {
            int completedLayers = dimensions - i;
            Layer topLayer = getNthRow(dimensions - i).replaceWithEmpty(completedLayers);
            Layer rightLayer = getNthColumn(i - 1).replaceWithEmpty(completedLayers);
            Layer bottomLayer = getNthRow(i - 1).replaceWithEmpty(completedLayers);
            Layer leftLayer = getNthColumn(dimensions - i).replaceWithEmpty(completedLayers);

            setNthColumn(i - 1, topLayer);
            setNthRow(i - 1, rightLayer.reverse());
            setNthColumn(dimensions - i, bottomLayer);
            setNthRow(dimensions - i, leftLayer.reverse());
        }
    }

    public void rotateCounterClockwise() {
        for (int i = dimensions; i > 1; i = i-2) {
            int completedLayers = dimensions - i;
            Layer topLayer = getNthRow(dimensions - i).replaceWithEmpty(completedLayers);
            Layer rightLayer = getNthColumn(i - 1).replaceWithEmpty(completedLayers);
            Layer bottomLayer = getNthRow(i - 1).replaceWithEmpty(completedLayers);
            Layer leftLayer = getNthColumn(dimensions - i).replaceWithEmpty(completedLayers);

            setNthColumn(dimensions - i, topLayer.reverse());
            setNthRow(i - 1, leftLayer);
            setNthColumn(i - 1, bottomLayer.reverse());
            setNthRow(dimensions - i, rightLayer);
        }
    }

    public ArrayList<Color> getAllColors() {
        ArrayList<Color> colors = new ArrayList<>();
        for (Layer layer : layers) {
            colors.addAll(layer.getDataSet());
        }
        return colors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Face that = (Face) o;

        if (this.dimensions != that.dimensions)
            return false;

        for (int i = 0; i < this.dimensions; i++) {
            for (int j = 0; j < this.dimensions; j++) {
                boolean l = this.layers.get(i).getNthPiece(j)
                        .equals(that.layers.get(i).getNthPiece(j));
                if (!l) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean matchPattern(String... pattern) {
        boolean l = true;
        int i = 0;
        while (i < dimensions && l) {
            int j = 0;
            while (j < dimensions && l) {
                if (!pattern[i*dimensions + j].equals("_")) {
                    l = l && getNthRow(i).getNthPiece(j).equals(Color.valueOf(pattern[i*dimensions + j]));
                }
                j++;
            }
            i++;
        }

        return l;
    }

    public Face duplicate() {
        ArrayList<Layer> copyOfLayers = new ArrayList<>();

        for (Layer layer : layers) {
            Layer copyOfLayer = new Layer(layer.getDataSet().toArray(new Color[layer.getDataSet().size()]));
            copyOfLayers.add(copyOfLayer);
        }

        return new Face(faceColor, copyOfLayers.toArray(new Layer[copyOfLayers.size()]));
    }

    @Override
    public int hashCode() {
        return Objects.hash(dimensions, layers);
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

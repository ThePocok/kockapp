package hu.thepocok.kockapp.model;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Face {
    private final int dimensions;
    private ArrayList<Layer> layers;

    public Face(Layer... layers) {
        this.layers = new ArrayList<>(Arrays.asList(layers));
        this.dimensions = layers.length;
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

    public void setNthRow(int n, Layer layer) {
        layers.get(n).copyTo(layer);
    }

    public void setNthColumn(int n, Layer layer) {
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

    public void rotateClockwise() {
        int i = (dimensions % 2 == 0) ? 2 : 3;

        for (; i <= dimensions; i = i+2) {
            int firstIndex = (dimensions - i) / 2;
            int lastIndex = dimensions - firstIndex - 1;
            Layer piecesToMove = getNthRow(firstIndex);

            //putting pieces to the right side, putting back pieces to the Layer from the right side
            Layer rightSide = getNthColumn(lastIndex);
            for (int p = firstIndex; p < lastIndex; p++) {
                Color oldPiece = rightSide.getNthPiece(p);
                rightSide.setNthPiece(p, piecesToMove.getNthPiece(p));
                piecesToMove.setNthPiece(p, oldPiece);
            }
            setNthColumn(lastIndex, rightSide);

            //putting pieces to the bottom side, putting back pieces to the Layer from the bottom side
            Layer bottomSide = getNthRow(lastIndex);
            for (int p = lastIndex; p >= firstIndex; p--) {
                Color oldPiece = bottomSide.getNthPiece(p);
                bottomSide.setNthPiece(p, piecesToMove.getNthPiece(dimensions - p - 1));
                piecesToMove.setNthPiece(dimensions - p - 1, oldPiece);
            }
            setNthRow(lastIndex, bottomSide);

            //putting pieces to the left side, putting back pieces to the Layer from the left side
            Layer leftSide = getNthColumn(firstIndex);
            for (int p = lastIndex; p >= firstIndex; p--) {
                Color oldPiece = leftSide.getNthPiece(p);
                leftSide.setNthPiece(dimensions - p - 1, piecesToMove.getNthPiece(p));
                piecesToMove.setNthPiece(p, oldPiece);
            }
            setNthColumn(firstIndex, leftSide);

            //every piece is now in order, put back piecesToMove to the top layer
            setNthRow(firstIndex, piecesToMove);
        }
    }

    public void rotateCounterClockwise() {
        int i = (dimensions % 2 == 0) ? 2 : 3;

        for (; i <= dimensions; i = i+2) {
            int firstIndex = (dimensions - i) / 2;
            int lastIndex = dimensions - firstIndex - 1;
            Layer piecesToMove = getNthRow(firstIndex);

            //putting pieces to the right side, putting back pieces to the Layer from the right side
            Layer leftSide = getNthColumn(firstIndex);
            for (int p = lastIndex; p >= firstIndex; p--) {
                Color oldPiece = leftSide.getNthPiece(p);
                leftSide.setNthPiece(dimensions - p - 1, piecesToMove.getNthPiece(p));
                piecesToMove.setNthPiece(p, oldPiece);
            }
            setNthColumn(lastIndex, leftSide);

            Layer bottomSide = getNthRow(lastIndex);
            for (int p = firstIndex; p < lastIndex; p++) {
                Color oldPiece = bottomSide.getNthPiece(p);
                bottomSide.setNthPiece(p, piecesToMove.getNthPiece(p));
                piecesToMove.setNthPiece(p, oldPiece);
            }
            setNthRow(lastIndex, bottomSide);

            Layer rightSide = getNthColumn(lastIndex);
            for (int p = lastIndex; p >= firstIndex; p--) {
                Color oldPiece = rightSide.getNthPiece(p);
                rightSide.setNthPiece(p, piecesToMove.getNthPiece(dimensions - p - 1));
                piecesToMove.setNthPiece(dimensions - p - 1, oldPiece);
            }
            setNthColumn(lastIndex, rightSide);

            setNthRow(firstIndex, piecesToMove);
        }
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

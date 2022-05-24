package hu.thepocok.kockapp.model.cube.component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Piece implements Serializable {
    private final ArrayList<Position> positions;

    public Piece(Position... positions) {
        this.positions = new ArrayList<>();
        this.positions.addAll(Arrays.asList(positions));
    }

    public ArrayList<Position> getPositions() {
        return positions;
    }

    //TODO incomplete
    public boolean isAdjacent(Piece that, int cubeDimensions) {
        ArrayList<ArrayList<Position>> positionsOnSameFace = new ArrayList<>();
        for (Position p1 : positions) {
            for (Position p2 : that.positions) {
                if (p1.getFace().equals(p2.getFace())) {
                    positionsOnSameFace.add(new ArrayList<>(Arrays.asList(p1, p2)));
                    break;
                }
            }
        }

        if (positionsOnSameFace.size() != 2) {
            return false;
        }

        boolean l = true;
        for (ArrayList<Position> positionPair : positionsOnSameFace) {
            Position p1 = positionPair.get(0);
            Position p2 = positionPair.get(1);

            l = l && !p1.getPositionAcross(cubeDimensions).equals(p2);
        }

        return l;
    }

    public boolean isAdjacent(Piece that, Color color) {
        Position thisPosition = this.getPosition(color);
        Position thatPosition = that.getPosition(color);

        if (thisPosition == null || thatPosition == null) {
            return false;
        }
        if (!thisPosition.getFace().equals(thatPosition.getFace())) {
            return false;
        }

        int distanceBetweenColumns = thisPosition.getColumn() - thatPosition.getColumn();
        int distanceBetweenRows = thisPosition.getRow() - thatPosition.getRow();

        return Math.abs(distanceBetweenColumns + distanceBetweenRows) % 2 == 1;

    }

    public boolean isAdjacent(Piece that, Color color1, Color color2) {
        return isAdjacent(that, color1) && isAdjacent(that, color2);
    }

    public Position getPosition(Color color) {
        for (Position p : positions) {
            if (p.getColor().equals(color)) {
                return p;
            }
        }

        return null;
    }

    public boolean hasPosition(Position thatPosition) {
        for (Position thisPosition : positions) {
            if (thisPosition.equals(thatPosition)) {
                return true;
            }
        }
        return false;
    }

    public boolean isFacing(Color face1, Color face2, Color face3) {
        ArrayList<Color> pieceFaces = new ArrayList<>();

        for (Position p : positions) {
            pieceFaces.add(p.getFace());
        }

        return pieceFaces.containsAll(Arrays.asList(face1, face2, face3));
    }

    public boolean hasFace(Color face) {
        boolean l = false;

        for (Position p : positions) {
            l = l || p.getFace().equals(face);
        }

        return l;
    }

    public boolean hasColor(Color color) {
        boolean l = false;

        for (Position p : positions) {
            l = l || p.getColor().equals(color);
        }

        return l;
    }

    public ArrayList<Color> getColors() {
        ArrayList<Color> colors = new ArrayList<>();

        for (Position p : positions) {
            colors.add(p.getColor());
        }

        return colors;
    }

    public ArrayList<Color> getFaceColors() {
        ArrayList<Color> colors = new ArrayList<>();

        for (Position p : positions) {
            colors.add(p.getFace());
        }

        return colors;
    }

    public Color getColorOnFace(Color face) {
        for (Position p : positions) {
            if (p.getFace().equals(face)) {
                return p.getColor();
            }
        }

        return null;
    }

    public Position getOtherColor(Color color) {
        if (positions.size() != 2) {
            return null;
        }

        for (Position p : positions) {
            if (!p.getColor().equals(color)) {
                return p;
            }
        }

        return null;
    }

    public Color getOtherFace(Color face) {
        if (positions.size() != 2) {
            return null;
        }

        for (Position p : positions) {
            if (!p.getFace().equals(face)) {
                return p.getFace();
            }
        }

        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Piece that = (Piece) o;
        List<Color> colorsOnThis = positions.stream().map(Position::getColor).collect(Collectors.toList());
        List<Color> colorsOnThat = that.positions.stream().map(Position::getColor).collect(Collectors.toList());

        if (colorsOnThis.size() != colorsOnThat.size()) {
            return false;
        }
        Collections.sort(colorsOnThis);
        Collections.sort(colorsOnThat);

        boolean l = true;
        int i = 0;

        while (i < colorsOnThis.size() && l) {
            l = l && colorsOnThis.get(i).equals(colorsOnThat.get(i));
            i++;
        }

        return l && i == colorsOnThat.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Piece{");

        for(Position p : positions) {
            sb.append(p.getColor()).append(" ");
        }

        sb.append("}");

        return sb.toString();
    }
}

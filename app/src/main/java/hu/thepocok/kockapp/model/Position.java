package hu.thepocok.kockapp.model;

import java.util.Objects;

public class Position {
    private Color faceColor;
    private int row;
    private int column;

    public Position(Color faceColor, int row, int column) {
        this.faceColor = faceColor;
        this.row = row;
        this.column = column;
    }

    public Color getFace() {
        return faceColor;
    }

    public void setFace(Color face) {
        this.faceColor = face;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    @Override
    public String toString() {
        return "Position{" +
                "face='" + faceColor + '\'' +
                ", row=" + row +
                ", column=" + column +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return row == position.row && column == position.column && faceColor == position.faceColor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(faceColor, row, column);
    }
}

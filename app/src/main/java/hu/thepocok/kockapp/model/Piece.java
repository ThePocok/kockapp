package hu.thepocok.kockapp.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Piece {
    private ArrayList<Position> positions;

    public Piece(Position... positions) {
        this.positions = new ArrayList<>();
        this.positions.addAll(Arrays.asList(positions));
    }

    public ArrayList<Position> getPositions() {
        return positions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Piece that = (Piece) o;
        List<Color> colorsOnThis = positions.stream().map(Position::getColor).collect(Collectors.toList());
        List<Color> colorsOnThat = that.positions.stream().map(Position::getColor).collect(Collectors.toList());
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
            sb.append(p.getColor() + " ");
        }

        sb.append("}");

        return sb.toString();
    }
}

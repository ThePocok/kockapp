package hu.thepocok.kockapp.model;

import java.util.ArrayList;
import java.util.Arrays;

import hu.thepocok.kockapp.model.exception.InvalidOrientationException;

public class Orientation {
    private Color faceUp;
    private Color faceFront;
    private Color faceLeft;

    public Orientation() {
        this.faceUp = Color.WHITE;
        this.faceFront = Color.RED;
        this.faceLeft = Color.GREEN;
    }

    public Color getFaceUp() {
        return faceUp;
    }

    public Color getFaceDown() {
        return getOppositeColor(faceUp);
    }

    public Color getFaceLeft() {
        return faceLeft;
    }

    public Color getFaceRight() {
        return getOppositeColor(faceLeft);
    }

    public Color getFaceFront() {
        return faceFront;
    }

    public Color getFaceBack() {
        return getOppositeColor(faceFront);
    }

    public void setOrientation(Color faceUp, Color faceTowardsPlayer, Color faceLeft) throws InvalidOrientationException{
        ArrayList<Color> allColors = new ArrayList<>(Arrays.asList(faceUp, getOppositeColor(faceUp),
                faceTowardsPlayer, getOppositeColor(faceTowardsPlayer), faceLeft, getOppositeColor(faceLeft)));

        if (!allColors.containsAll(Arrays.asList(Color.WHITE, Color.RED, Color.GREEN, Color.ORANGE, Color.BLUE, Color.YELLOW))) {
            throw new InvalidOrientationException();
        }

        this.faceUp = faceUp;
        this.faceFront = faceTowardsPlayer;
        this.faceLeft = faceLeft;
    }

    public Color getOppositeColor(Color color) {
        switch (color) {
            case WHITE:
                return Color.YELLOW;
            case RED:
                return Color.ORANGE;
            case GREEN:
                return Color.BLUE;
            case ORANGE:
                return Color.RED;
            case BLUE:
                return Color.GREEN;
            case YELLOW:
                return Color.WHITE;
        }
        return null;
    }

    @Override
    public String toString() {
        return "Orientation{" +
                "faceUp=" + faceUp +
                ", faceTowardsPlayer=" + faceFront +
                '}';
    }
}

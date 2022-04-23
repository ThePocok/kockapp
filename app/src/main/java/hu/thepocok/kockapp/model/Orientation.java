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

    public void setOrientation(Color faceUp, Color faceFront) throws InvalidOrientationException{
        Color[] previousOrientation = new Color[]{this.faceUp, this.faceFront, this.faceLeft};
        this.faceUp = faceUp;
        this.faceFront = faceFront;

        switch (faceUp) {
            case WHITE:
                switch (faceFront) {
                    case RED:
                        this.faceLeft = Color.GREEN;
                        break;
                    case GREEN:
                        this.faceLeft = Color.ORANGE;
                        break;
                    case ORANGE:
                        this.faceLeft = Color.BLUE;
                        break;
                    case BLUE:
                        this.faceLeft = Color.RED;
                        break;
                }
                break;
            case RED:
                switch (faceFront) {
                    case WHITE:
                        this.faceLeft = Color.BLUE;
                        break;
                    case BLUE:
                        this.faceLeft = Color.YELLOW;
                        break;
                    case YELLOW:
                        this.faceLeft = Color.GREEN;
                        break;
                    case GREEN:
                        this.faceLeft = Color.WHITE;
                        break;
                }
                break;
            case GREEN:
                switch (faceFront) {
                    case WHITE:
                        this.faceLeft = Color.RED;
                        break;
                    case RED:
                        this.faceLeft = Color.YELLOW;
                        break;
                    case YELLOW:
                        this.faceLeft = Color.ORANGE;
                        break;
                    case ORANGE:
                        this.faceLeft = Color.WHITE;
                        break;
                }
                break;
            case ORANGE:
                switch (faceFront) {
                    case WHITE:
                        this.faceLeft = Color.GREEN;
                        break;
                    case GREEN:
                        this.faceLeft = Color.YELLOW;
                        break;
                    case YELLOW:
                        this.faceLeft = Color.BLUE;
                        break;
                    case BLUE:
                        this.faceLeft = Color.WHITE;
                        break;
                }
                break;
            case BLUE:
                switch (faceFront) {
                    case WHITE:
                        this.faceLeft = Color.ORANGE;
                        break;
                    case ORANGE:
                        this.faceLeft = Color.YELLOW;
                        break;
                    case YELLOW:
                        this.faceLeft = Color.RED;
                        break;
                    case RED:
                        this.faceLeft = Color.WHITE;
                        break;
                }
                break;
            case YELLOW:
                switch (faceFront) {
                    case RED:
                        this.faceLeft = Color.BLUE;
                        break;
                    case BLUE:
                        this.faceLeft = Color.ORANGE;
                        break;
                    case ORANGE:
                        this.faceLeft = Color.GREEN;
                        break;
                    case GREEN:
                        this.faceLeft = Color.RED;
                        break;
                }
                break;
        }

        ArrayList<Color> allColors = new ArrayList<>(Arrays.asList(this.faceUp, getOppositeColor(this.faceUp),
                this.faceFront, getOppositeColor(this.faceFront), faceLeft, getOppositeColor(faceLeft)));

        if (!allColors.containsAll(Arrays.asList(Color.WHITE, Color.RED, Color.GREEN, Color.ORANGE, Color.BLUE, Color.YELLOW))) {
            this.faceUp = previousOrientation[0];
            this.faceFront = previousOrientation[1];
            this.faceLeft = previousOrientation[2];
            throw new InvalidOrientationException();
        }
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

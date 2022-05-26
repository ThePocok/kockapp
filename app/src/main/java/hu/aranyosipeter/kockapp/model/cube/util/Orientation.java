package hu.aranyosipeter.kockapp.model.cube.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import hu.aranyosipeter.kockapp.model.cube.component.Color;
import hu.aranyosipeter.kockapp.model.exception.InvalidOrientationException;

public class Orientation implements Serializable {
    private Color faceUp;
    private Color faceFront;
    private Color faceLeft;

    public Orientation() {
        this.faceUp = Color.WHITE;
        this.faceFront = Color.RED;
        this.faceLeft = Color.GREEN;
    }

    public Orientation(Color faceUp, Color faceFront) {
        super();
        try {
            setOrientation(faceUp, faceFront);
        } catch (InvalidOrientationException e) {
            this.faceUp = null;
            this.faceFront = null;
            this.faceLeft = null;
        }
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

    public int getRotationDegree(Color faceColor) {
        switch (getFaceUp()) {
            case WHITE:
                if (faceColor.equals(Color.RED) || faceColor.equals(Color.GREEN)
                        || faceColor.equals(Color.ORANGE) || faceColor.equals(Color.BLUE)) {
                    return 0;
                }

                switch (getFaceFront()) {
                    case RED:
                        return 0;
                    case BLUE:
                        switch (faceColor) {
                            case WHITE:
                                return  90;
                            case YELLOW:
                                return 270;
                        }
                    case ORANGE:
                        return 180;
                    case GREEN:
                        switch (faceColor) {
                            case WHITE:
                                return 270;
                            case YELLOW:
                                return 90;
                        }
                }
            case RED:
                if (faceColor.equals(Color.YELLOW)) {
                    return 0;
                } else if (faceColor.equals(Color.BLUE)) {
                    return 90;
                } else if (faceColor.equals(Color.WHITE)) {
                    return 180;
                } else if (faceColor.equals(Color.GREEN)) {
                    return 270;
                }

                switch (getFaceFront()) {
                    case WHITE:
                        switch (faceColor) {
                            case RED:
                                return 180;
                            case ORANGE:
                                return 0;
                        }
                    case BLUE:
                        return 90;
                    case YELLOW:
                        switch (faceColor) {
                            case ORANGE:
                                return 180;
                            case RED:
                                return 0;
                        }
                    case GREEN:
                        return 270;
                }
            case GREEN:
                if (faceColor.equals(Color.YELLOW) || faceColor.equals(Color.WHITE) || faceColor.equals(Color.RED)) {
                    return 90;
                } else if (faceColor.equals(Color.ORANGE)) {
                    return 270;
                }

                switch (getFaceFront()) {
                    case WHITE:
                        switch (faceColor) {
                            case GREEN:
                                return 180;
                            case BLUE:
                                return 0;
                        }
                    case RED:
                        return 90;
                    case YELLOW:
                        switch (faceColor) {
                            case GREEN:
                                return 0;
                            case BLUE:
                                return 180;
                        }
                    case ORANGE:
                        return 270;
                }
            case ORANGE:
                if (faceColor.equals(Color.WHITE)) {
                    return 0;
                } else if (faceColor.equals(Color.GREEN)) {
                    return 90;
                } else if (faceColor.equals(Color.YELLOW)) {
                    return 180;
                } else if (faceColor.equals(Color.BLUE)) {
                    return 270;
                }

                switch (getFaceFront()) {
                    case WHITE:
                        switch (faceColor) {
                            case RED:
                                return 0;
                            case ORANGE:
                                return 180;
                        }
                    case GREEN:
                        return 90;
                    case YELLOW:
                        switch (faceColor) {
                            case ORANGE:
                                return 0;
                            case RED:
                                return 180;
                        }
                    case BLUE:
                        return 270;
                }
            case BLUE:
                if (faceColor.equals(Color.YELLOW) || faceColor.equals(Color.WHITE) || faceColor.equals(Color.RED)) {
                    return 270;
                } else if (faceColor.equals(Color.ORANGE)) {
                    return 90;
                }

                switch (getFaceFront()) {
                    case WHITE:
                        switch (faceColor) {
                            case GREEN:
                                return 0;
                            case BLUE:
                                return 180;
                        }
                    case ORANGE:
                        return 90;
                    case YELLOW:
                        switch (faceColor) {
                            case GREEN:
                                return 180;
                            case BLUE:
                                return 0;
                        }
                    case RED:
                       return 270;
                }
            case YELLOW:
                if (faceColor.equals(Color.RED) || faceColor.equals(Color.GREEN)
                        || faceColor.equals(Color.ORANGE) || faceColor.equals(Color.BLUE)) {
                    return 180;
                }

                switch (getFaceFront()) {
                    case RED:
                        return 180;
                    case BLUE:
                        switch (faceColor) {
                            case WHITE:
                                return 270;
                            case YELLOW:
                                return 90;
                        }
                        break;
                    case ORANGE:
                        return 0;
                    case GREEN:
                        switch (faceColor) {
                            case WHITE:
                                return 90;
                            case YELLOW:
                                return 270;
                        }
                }
        }
        return -1;
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

    public Orientation duplicate() {
        Orientation duplicate = new Orientation();
        try {
            duplicate.setOrientation(this.getFaceUp(), this.getFaceFront());
        } catch (InvalidOrientationException e) {
            e.printStackTrace(); // This will never occur
        }
        return duplicate;
    }

    @Override
    public String toString() {
        return "Orientation{" +
                "faceUp=" + faceUp +
                ", faceTowardsPlayer=" + faceFront +
                '}';
    }
}

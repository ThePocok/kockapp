package hu.thepocok.kockapp.model.cube.component;

import java.io.Serializable;

public enum Color implements Comparable<Color>, Serializable {
    WHITE("W", 255, 255, 255),
    RED("R", 183, 18, 52),
    GREEN("G", 0, 155, 72),
    BLUE("B", 0, 70, 173),
    ORANGE("O", 255, 88, 0),
    YELLOW("Y", 255, 213, 0),
    EMPTY("_", 0, 0, 0);

    public final String stringValue;
    public final int redValue;
    public final int greenValue;
    public final int blueValue;

    Color(String stringValue, int redValue, int greenValue, int blueValue) {
            this.stringValue = stringValue;
            this.redValue = redValue;
            this.greenValue = greenValue;
            this.blueValue = blueValue;
        }
    }
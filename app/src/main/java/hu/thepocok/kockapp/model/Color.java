package hu.thepocok.kockapp.model;

public enum Color {
    WHITE("W"),
    RED("R"),
    GREEN("G"),
    BLUE("B"),
    ORANGE("O"),
    YELLOW("Y"),
    EMPTY("_");

    public final String stringValue;

    Color(String stringValue) {
        this.stringValue = stringValue;
    }
}

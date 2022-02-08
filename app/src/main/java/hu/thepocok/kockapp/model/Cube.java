package hu.thepocok.kockapp.model;

import java.util.ArrayList;
import java.util.Collections;

/**
 * The Cube class models an abstract Rubik's cube.
 * By default, the white faces is on top, the yellow is on bottom,
 * the red is facing towards us, on it's right side is the green face,
 * on it's left side is the blue one, and on the back, there is the orange face.
 */
public abstract class Cube {
    private Face whiteFace;
    private Face redFace;
    private Face greenFace;
    private Face orangeFace;
    private Face blueFace;
    private Face yellowFace;

    public Cube(Face whiteFace, Face redFace, Face greenFace, Face orangeFace, Face blueFace, Face yellowFace) {
        this.whiteFace = whiteFace;
        this.redFace = redFace;
        this.greenFace = greenFace;
        this.orangeFace = orangeFace;
        this.blueFace = blueFace;
        this.yellowFace = yellowFace;
    }

    public Cube(int dimensions) {
        this(Face.generateFace(Color.WHITE, dimensions),
                Face.generateFace(Color.RED, dimensions),
                Face.generateFace(Color.GREEN, dimensions),
                Face.generateFace(Color.ORANGE, dimensions),
                Face.generateFace(Color.BLUE, dimensions),
                Face.generateFace(Color.YELLOW, dimensions));
    }

    /**
     * Move: U
     * Turning the face on the top clockwise.
     * Affected layers: Whole white face, first layer of red, orange, green and blue faces.
     */
    public void rotateUpClockwise() {
        whiteFace.rotateClockwise();

        Layer layerToRotate = greenFace.getNthRow(0);
        greenFace.setNthRow(0, redFace.getNthRow(0));

        Layer originalLayer = orangeFace.getNthRow(0);
        orangeFace.setNthRow(0, layerToRotate);
        layerToRotate = originalLayer;

        originalLayer = blueFace.getNthRow(0);
        blueFace.setNthRow(0, layerToRotate);
        layerToRotate = originalLayer;

        redFace.setNthRow(0, layerToRotate);
    }

    /**
     * Move: U'
     * Turning the face on the top counterclockwise.
     * Affected layers: Whole white face, first layer of red, orange, green and blue faces.
     */
    public void rotateUpCounterClockwise() {
        whiteFace.rotateCounterClockwise();

        Layer layerToRotate = blueFace.getNthRow(0);
        blueFace.setNthRow(0, redFace.getNthRow(0));

        Layer originalLayer = orangeFace.getNthRow(0);
        orangeFace.setNthRow(0, layerToRotate);
        layerToRotate = originalLayer;

        originalLayer = greenFace.getNthRow(0);
        greenFace.setNthRow(0, layerToRotate);
        layerToRotate = originalLayer;

        redFace.setNthRow(0, layerToRotate);
    }

    private ArrayList<Color> determineSideFaces(Color color, boolean reverseOrder) {
        ArrayList<Color> colors = new ArrayList<>();
        switch (color) {
            case WHITE:
            case YELLOW:
                colors.add(Color.RED);
                colors.add(Color.GREEN);
                colors.add(Color.ORANGE);
                colors.add(Color.BLUE);
                break;
            case RED:
                colors.add(Color.YELLOW);
                colors.add(Color.GREEN);
                colors.add(Color.WHITE);
                colors.add(Color.BLUE);
                break;
            case GREEN:
                colors.add(Color.YELLOW);
                colors.add(Color.ORANGE);
                colors.add(Color.WHITE);
                colors.add(Color.BLUE);
                break;
            case ORANGE:
                colors.add(Color.YELLOW);
                colors.add(Color.BLUE);
                colors.add(Color.WHITE);
                colors.add(Color.GREEN);
                break;
            case BLUE:
                colors.add(Color.YELLOW);
                colors.add(Color.RED);
                colors.add(Color.WHITE);
                colors.add(Color.ORANGE);
                break;
            default:
                break;
        }

        if (reverseOrder)
            Collections.reverse(colors);
        return colors;
    }

    public Face getFace(Color color) {
        switch (color) {
            case WHITE: return whiteFace;
            case RED: return redFace;
            case GREEN: return greenFace;
            case ORANGE: return orangeFace;
            case BLUE: return blueFace;
            case YELLOW: return yellowFace;
            default: return null;
        }
    }

    public abstract void solve();

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Cube: \n");
        sb.append(whiteFace.toString());
        sb.append(redFace.toString());
        sb.append(greenFace.toString());
        sb.append(orangeFace.toString());
        sb.append(blueFace.toString());
        sb.append(yellowFace.toString());
        return sb.toString();
    }
}

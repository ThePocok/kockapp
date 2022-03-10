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
    private final Face whiteFace;
    private final Face redFace;
    private final Face greenFace;
    private final Face orangeFace;
    private final Face blueFace;
    private final Face yellowFace;

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
     * Turning the white face clockwise.
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
     * Turning the white face counterclockwise.
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

    /**
     * Move: D
     * Turning the yellow face clockwise.
     * Affected faces: Whole yellow face, last layer of red, orange, green and blue faces.
     */
    public void rotateDownClockwise() {
        yellowFace.rotateCounterClockwise();

        Layer layerToRotate = greenFace.getNthRow(greenFace.getDimensions() - 1);
        greenFace.setNthRow(greenFace.getDimensions() - 1, redFace.getNthRow(redFace.getDimensions() - 1));

        Layer originalLayer = orangeFace.getNthRow(orangeFace.getDimensions() - 1);
        orangeFace.setNthRow(orangeFace.getDimensions() - 1, layerToRotate);
        layerToRotate = originalLayer;

        originalLayer = blueFace.getNthRow(blueFace.getDimensions() - 1);
        blueFace.setNthRow(blueFace.getDimensions() - 1, layerToRotate);
        layerToRotate = originalLayer;

        redFace.setNthRow(redFace.getDimensions() - 1, layerToRotate);
    }

    /**
     * Move: D'
     * Turning the yellow face counterclockwise.
     * Affected faces: Whole yellow face, last layer of red, orange, green and blue faces.
     */
    public void rotateDownCounterClockwise() {
        yellowFace.rotateClockwise();

        Layer layerToRotate = blueFace.getNthRow(blueFace.getDimensions() - 1);
        blueFace.setNthRow(blueFace.getDimensions() - 1, redFace.getNthRow(redFace.getDimensions() - 1));

        Layer originalLayer = orangeFace.getNthRow(orangeFace.getDimensions() - 1);
        orangeFace.setNthRow(orangeFace.getDimensions() - 1, layerToRotate);
        layerToRotate = originalLayer;

        originalLayer = greenFace.getNthRow(greenFace.getDimensions() - 1);
        greenFace.setNthRow(greenFace.getDimensions() - 1, layerToRotate);
        layerToRotate = originalLayer;

        redFace.setNthRow(redFace.getDimensions() - 1, layerToRotate);
    }

    /**
     * Move: L
     * Turning the green face clockwise.
     * Affected faces: Whole green face, right column of white, red, yellow and orange faces.
     */
    public void rotateLeftClockwise() {
        greenFace.rotateClockwise();

        Layer layerToRotate = redFace.getNthColumn(0);
        redFace.setNthColumn(0, whiteFace.getNthColumn(0));

        Layer originalLayer = yellowFace.getNthColumn(0);
        yellowFace.setNthColumn(0, layerToRotate);
        layerToRotate = originalLayer;

        originalLayer = orangeFace.getNthColumn(orangeFace.getDimensions() - 1);
        orangeFace.setNthColumn(orangeFace.getDimensions() - 1,  layerToRotate);
        layerToRotate = originalLayer;

        whiteFace.setNthColumn(0, layerToRotate);
    }

    /**
     * Move: L'
     * Turning the green face counterclockwise.
     * Affected faces: Whole green face, right column of white, red, yellow and orange faces.
     */
    public void rotateLeftCounterClockwise() {
        greenFace.rotateCounterClockwise();

        Layer layerToRotate = orangeFace.getNthColumn(orangeFace.getDimensions() - 1);
        orangeFace.setNthColumn(orangeFace.getDimensions() - 1, whiteFace.getNthColumn(0));

        Layer originalLayer = yellowFace.getNthColumn(0);
        yellowFace.setNthColumn(0, layerToRotate);
        layerToRotate = originalLayer;

        originalLayer = redFace.getNthColumn(0);
        redFace.setNthColumn(0,  layerToRotate);
        layerToRotate = originalLayer;

        whiteFace.setNthColumn(0, layerToRotate);
    }

    /**
     * Move: R
     * Turning the blue face clockwise.
     * Affected faces: Whole blue face, right column of white, red, yellow and orange faces.
     */
    public void rotateRightClockwise() {
        blueFace.rotateClockwise();

        Layer layerToRotate = orangeFace.getNthColumn(0);
        orangeFace.setNthColumn(0,  whiteFace.getNthColumn(whiteFace.getDimensions() - 1));

        Layer originalLayer = yellowFace.getNthColumn(yellowFace.getDimensions() - 1);
        yellowFace.setNthColumn(yellowFace.getDimensions() - 1, layerToRotate);
        layerToRotate = originalLayer;

        originalLayer = redFace.getNthColumn(redFace.getDimensions() - 1);
        redFace.setNthColumn(redFace.getDimensions() - 1, layerToRotate);
        layerToRotate = originalLayer;

        whiteFace.setNthColumn(whiteFace.getDimensions() - 1, layerToRotate);
    }

    /**
     * Move: R'
     * Turning the blue face counterclockwise.
     * Affected faces: Whole blue face, right column of white, red, yellow and orange faces.
     */
    public void rotateRightCounterClockwise() {
        blueFace.rotateCounterClockwise();

        Layer layerToRotate = redFace.getNthColumn(redFace.getDimensions() - 1);
        redFace.setNthColumn(redFace.getDimensions() - 1,  whiteFace.getNthColumn(whiteFace.getDimensions() - 1));

        Layer originalLayer = yellowFace.getNthColumn(yellowFace.getDimensions() - 1);
        yellowFace.setNthColumn(yellowFace.getDimensions() - 1, layerToRotate);
        layerToRotate = originalLayer;

        originalLayer = orangeFace.getNthColumn(0);
        orangeFace.setNthColumn(0, layerToRotate);
        layerToRotate = originalLayer;

        whiteFace.setNthColumn(whiteFace.getDimensions() - 1, layerToRotate);
    }

    /**
     * Move: F
     * Turning the red face clockwise.
     * Affected faces: Whole red face, bottom row of white face, left column of blue face,
     * top row of yellow face, right column of green face
     */
    public void rotateFrontClockwise() {
        redFace.rotateClockwise();

        Layer layerToRotate = blueFace.getNthColumn(0);
        blueFace.setNthColumn(0, whiteFace.getNthRow(whiteFace.getDimensions() - 1));

        Layer originalLayer = yellowFace.getNthRow(0);
        yellowFace.setNthRow(0, layerToRotate);
        layerToRotate = originalLayer;

        originalLayer = greenFace.getNthColumn(greenFace.getDimensions() - 1);
        greenFace.setNthColumn(greenFace.getDimensions() - 1, layerToRotate);
        layerToRotate = originalLayer;

        whiteFace.setNthRow(whiteFace.getDimensions() - 1, layerToRotate);
    }

    /**
     * Move: F'
     * Turning the red face counterclockwise.
     * Affected faces: Whole red face, bottom row of white face, left column of blue face,
     * top row of yellow face, right column of green face
     */
    public void rotateFrontCounterClockwise() {
        redFace.rotateCounterClockwise();

        Layer layerToRotate = greenFace.getNthColumn(greenFace.getDimensions() - 1);
        greenFace.setNthColumn(greenFace.getDimensions() - 1, whiteFace.getNthRow(whiteFace.getDimensions() - 1));

        Layer originalLayer = yellowFace.getNthRow(0);
        yellowFace.setNthRow(0, layerToRotate);
        layerToRotate = originalLayer;

        originalLayer = blueFace.getNthColumn(0);
        blueFace.setNthColumn(0, layerToRotate);
        layerToRotate = originalLayer;

        whiteFace.setNthRow(whiteFace.getDimensions() - 1, layerToRotate);
    }

    /**
     * Move: B
     * Turning the orange face clockwise.
     * Affected faces: Whole orange face, top row of white face, right column of blue face,
     * bottom row of yellow face, left column of green face
     */
    public void rotateBackClockwise() {
        orangeFace.rotateClockwise();

        Layer layerToRotate = greenFace.getNthColumn(0);
        greenFace.setNthColumn(0, whiteFace.getNthRow(0));

        Layer originalLayer = yellowFace.getNthRow(yellowFace.getDimensions() - 1);
        yellowFace.setNthRow(yellowFace.getDimensions() - 1, layerToRotate);
        layerToRotate = originalLayer;

        originalLayer = blueFace.getNthColumn(blueFace.getDimensions() - 1);
        blueFace.setNthColumn(blueFace.getDimensions() - 1, layerToRotate);
        layerToRotate = originalLayer;

        whiteFace.setNthRow(0, layerToRotate);
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
        String cube = "Cube: \n" +
                whiteFace.toString() +
                redFace.toString() +
                greenFace.toString() +
                orangeFace.toString() +
                blueFace.toString() +
                yellowFace.toString();
        return cube;
    }
}

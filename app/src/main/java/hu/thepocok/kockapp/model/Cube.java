package hu.thepocok.kockapp.model;

import java.util.ArrayList;

/**
 * The Cube class models an abstract Rubik's cube.
 * By default, the white faces is on top, the yellow is on bottom,
 * the red is facing towards us, on it's right side is the green face,
 * on it's left side is the blue one, and on the back, there is the orange face.
 */
public class Cube {
    private Face whiteFace;
    private Face redFace;
    private Face greenFace;
    private Face orangeFace;
    private Face blueFace;
    private Face yellowFace;
    private Orientation orientation;

    public Cube(Face whiteFace, Face redFace, Face greenFace, Face orangeFace, Face blueFace, Face yellowFace) {
        this.whiteFace = whiteFace;
        this.redFace = redFace;
        this.greenFace = greenFace;
        this.orangeFace = orangeFace;
        this.blueFace = blueFace;
        this.yellowFace = yellowFace;
        orientation = new Orientation(Color.WHITE, Color.RED);
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
     * Turning the face on the top clockwise.
     * Affected layers: the whole top face,
     */
    public void turnUpClockwise() {

    }

    public void turnClockwise(Face turningFace, ArrayList<Face> sideFaces) {
        // Turning the face itself
        Layer currentTurningFaceLayer = turningFace.getNthColumn(0);

        Layer turningFaceLayer = turningFace.getNthRow(turningFace.getDimensions());
        turningFace.setNthRow(turningFace.getDimensions(), currentTurningFaceLayer, false);
        currentTurningFaceLayer = turningFaceLayer;

        turningFaceLayer = turningFace.getNthColumn(turningFace.getDimensions());
        turningFace.setNthColumn(turningFace.getDimensions(), currentTurningFaceLayer, false);
        currentTurningFaceLayer = turningFaceLayer;

        turningFaceLayer = turningFace.getNthRow(0);
        turningFace.setNthRow(0, currentTurningFaceLayer, false);
        currentTurningFaceLayer = turningFaceLayer;

        turningFace.setNthColumn(0, currentTurningFaceLayer, false);

        // Turning the side face layers
        Layer currentLayer = sideFaces.get(0).getNthRow(0);
        for (int i = 1; i < 3; i++) {
            Layer layer = sideFaces.get(i).getNthRow(0);
            sideFaces.get(i).setNthRow(0, currentLayer, false);
            currentLayer = layer;
        }
        sideFaces.get(0).setNthRow(0, currentLayer, false);
    }

    public void turnCounterClockwise(Face turningFace, ArrayList<Face> sideFaces) {
        // Turning the face itself
        Layer currentTurningFaceLayer = turningFace.getNthColumn(0);

        Layer turningFaceLayer = turningFace.getNthRow(0);
        turningFace.setNthRow(0, currentTurningFaceLayer, true);
        currentTurningFaceLayer = turningFaceLayer;

        turningFaceLayer = turningFace.getNthColumn(turningFace.getDimensions());
        turningFace.setNthColumn(turningFace.getDimensions(), currentTurningFaceLayer, true);
        currentTurningFaceLayer = turningFaceLayer;

        turningFaceLayer = turningFace.getNthRow(turningFace.getDimensions());
        turningFace.setNthRow(turningFace.getDimensions(), currentTurningFaceLayer, true);
        currentTurningFaceLayer = turningFaceLayer;

        turningFace.setNthColumn(0, currentTurningFaceLayer, true);

        // Turning the side face layers
        Layer currentLayer = sideFaces.get(3).getNthRow(0);
        for (int i = 2; i >= 0; i++) {
            Layer layer = sideFaces.get(i).getNthRow(0);
            sideFaces.get(i).setNthRow(0, currentLayer, false);
            currentLayer = layer;
        }
        sideFaces.get(3).setNthRow(0, currentLayer, false);
    }

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

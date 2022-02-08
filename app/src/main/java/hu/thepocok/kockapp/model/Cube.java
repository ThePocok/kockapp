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
     * Turning the face on the top clockwise.
     * Affected layers: the whole top face,
     */
    public void rotateUpClockwise() {
        whiteFace.rotateClockwise();
    }

    //U'
    public void rotateUpCounterClockwise() {
        whiteFace.rotateCounterClockwise();

        // Turning the side face layers
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


//    private void turnClockwise(Face turningFace, ArrayList<Face> sideFaces) {
//        // Turning the face itself
//        Layer currentTurningFaceLayer = turningFace.getNthColumn(0);
//
//        Layer turningFaceLayer = turningFace.getNthRow(turningFace.getDimensions());
//        turningFace.setNthRow(turningFace.getDimensions(), currentTurningFaceLayer);
//        currentTurningFaceLayer = turningFaceLayer;
//
//        turningFaceLayer = turningFace.getNthColumn(turningFace.getDimensions());
//        turningFace.setNthColumn(turningFace.getDimensions(), currentTurningFaceLayer, false);
//        currentTurningFaceLayer = turningFaceLayer;
//
//        turningFaceLayer = turningFace.getNthRow(0);
//        turningFace.setNthRow(0, currentTurningFaceLayer);
//        currentTurningFaceLayer = turningFaceLayer;
//
//        turningFace.setNthColumn(0, currentTurningFaceLayer, false);
//
//        // Turning the side face layers
//        Layer currentLayer = sideFaces.get(0).getNthRow(0);
//        for (int i = 1; i < 3; i++) {
//            Layer layer = sideFaces.get(i).getNthRow(0);
//            sideFaces.get(i).setNthRow(0, currentLayer);
//            currentLayer = layer;
//        }
//        sideFaces.get(0).setNthRow(0, currentLayer);
//    }

//    private void turnCounterClockwise(Color turningFaceColor) {
//        ArrayList<Color> sideFaceColors = determineSideFaces(turningFaceColor, true);
//        // Turning the face itself
//        Layer currentTurningFaceLayer = getFace(turningFaceColor).getNthColumn(0);
//
//        Layer turningFaceLayer = getFace(turningFaceColor).getNthRow(0);
//        getFace(turningFaceColor).setNthRow(0, currentTurningFaceLayer, true);
//        currentTurningFaceLayer = turningFaceLayer;
//
//        turningFaceLayer = getFace(turningFaceColor).getNthColumn(getFace(turningFaceColor).getDimensions() - 1);
//        getFace(turningFaceColor).setNthColumn(getFace(turningFaceColor).getDimensions() - 1, currentTurningFaceLayer, true);
//        currentTurningFaceLayer = turningFaceLayer;
//
//        turningFaceLayer = getFace(turningFaceColor).getNthRow(getFace(turningFaceColor).getDimensions() - 1);
//        getFace(turningFaceColor).setNthRow(getFace(turningFaceColor).getDimensions() - 1, currentTurningFaceLayer, true);
//        currentTurningFaceLayer = turningFaceLayer;
//
//        getFace(turningFaceColor).setNthColumn(0, currentTurningFaceLayer, true);
//
//        // Turning the side face layers
//        Layer currentLayer = getFace(sideFaceColors.get(3)).getNthRow(0);
//        for (int i = 2; i >= 0; i--) {
//            Layer layer = getFace(sideFaceColors.get(i)).getNthRow(0);
//            getFace(sideFaceColors.get(i)).setNthRow(0, currentLayer, false);
//            currentLayer = layer;
//        }
//        getFace(sideFaceColors.get(3)).setNthRow(0, currentLayer, false);
//    }

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

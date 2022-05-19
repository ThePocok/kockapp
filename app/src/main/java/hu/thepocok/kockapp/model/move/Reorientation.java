package hu.thepocok.kockapp.model.move;

import java.io.Serializable;

import hu.thepocok.kockapp.model.cube.util.Orientation;
import hu.thepocok.kockapp.model.exception.InvalidOrientationException;

public class Reorientation extends Move implements Serializable {
    private Orientation previousOrientation;
    private final Orientation orientation;

    public Reorientation(Orientation previousOrientation, Orientation orientation) {
        this.previousOrientation = previousOrientation;
        this.orientation = new Orientation(orientation.getFaceUp(), orientation.getFaceFront());
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public Orientation getPreviousOrientation() {
        return previousOrientation;
    }

    public void setPreviousOrientation(Orientation previousOrientation) {
        this.previousOrientation = previousOrientation;
    }

    @Override
    public String toString() {
        return "Reorientation{" +
                "orientation=" + orientation +
                '}';
    }
}

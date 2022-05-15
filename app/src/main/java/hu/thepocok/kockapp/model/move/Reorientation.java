package hu.thepocok.kockapp.model.move;

import java.io.Serializable;

import hu.thepocok.kockapp.model.cube.util.Orientation;
import hu.thepocok.kockapp.model.exception.InvalidOrientationException;

public class Reorientation extends Move implements Serializable {
    private final Orientation orientation;

    public Reorientation(Orientation orientation) {
        this.orientation = new Orientation();
        try {
            this.orientation.setOrientation(orientation.getFaceUp(), orientation.getFaceFront());
        } catch (InvalidOrientationException e) {
            e.printStackTrace(); //This will never occur
        }
    }

    public Orientation getOrientation() {
        return orientation;
    }

    @Override
    public String toString() {
        return "Reorientation{" +
                "orientation=" + orientation +
                '}';
    }
}

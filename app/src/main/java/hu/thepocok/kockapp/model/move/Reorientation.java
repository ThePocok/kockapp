package hu.thepocok.kockapp.model.move;

import hu.thepocok.kockapp.model.cube.util.Orientation;
import hu.thepocok.kockapp.model.exception.InvalidOrientationException;

public class Reorientation extends Move {
    private Orientation orientation;

    public Reorientation(Orientation orientation) {
        this.orientation = new Orientation();
        try {
            this.orientation.setOrientation(orientation.getFaceUp(), orientation.getFaceFront());
        } catch (InvalidOrientationException e) {
            //This can not occur
            e.printStackTrace();
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

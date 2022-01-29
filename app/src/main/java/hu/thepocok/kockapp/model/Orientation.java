package hu.thepocok.kockapp.model;

public class Orientation {
    public Color faceUp;
    public Color faceTowardsPlayer;

    public Orientation(Color faceUp, Color faceTowardsPlayer) {
        this.faceUp = faceUp;
        this.faceTowardsPlayer = faceTowardsPlayer;
    }

    public Color getFaceUp() {
        return faceUp;
    }

    public void setFaceUp(Color faceUp) {
        this.faceUp = faceUp;
    }

    public Color getFaceTowardsPlayer() {
        return faceTowardsPlayer;
    }

    public void setFaceTowardsPlayer(Color faceTowardsPlayer) {
        this.faceTowardsPlayer = faceTowardsPlayer;
    }
}

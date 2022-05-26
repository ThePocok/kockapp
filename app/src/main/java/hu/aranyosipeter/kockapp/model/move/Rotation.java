package hu.aranyosipeter.kockapp.model.move;

import java.io.Serializable;

public class Rotation extends Move implements Serializable {
    private String key;

    public Rotation(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "Rotation{" +
                "key=" + key +
                '}';
    }
}

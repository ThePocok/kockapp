package hu.thepocok.kockapp.ui;

import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;

import java.util.HashMap;
import java.util.Map;

import hu.thepocok.kockapp.model.cube.component.Color;

public class CubeTile {
    private Scalar scalarColor;
    private Rect rect;
    private Point center;
    private int size;

    private final Map<Color, Scalar[]> colorRanges = new HashMap<Color, Scalar[]>() {{
            put(Color.WHITE, new Scalar[]{new Scalar(200, 200, 200), new Scalar(255, 255, 255)});
            put(Color.RED, new Scalar[]{new Scalar(139, 0, 0), new Scalar(255, 255, 255)});
            put(Color.GREEN, new Scalar[]{new Scalar(150, 150, 150), new Scalar(255, 255, 255)});
            put(Color.ORANGE, new Scalar[]{new Scalar(150, 150, 150), new Scalar(255, 255, 255)});
            put(Color.BLUE, new Scalar[]{new Scalar(150, 150, 150), new Scalar(255, 255, 255)});
            put(Color.YELLOW, new Scalar[]{new Scalar(150, 150, 150), new Scalar(255, 255, 255)});
    }};

    public CubeTile(Point center, int size) {
        scalarColor = new Scalar(0, 0, 0);
        this.size = size;
        this.center = center;

        rect = new Rect();
        rect.x = (int) (center.x - size / 2);
        rect.y = (int) (center.y - size / 2);
        rect.width = size;
        rect.height = size;
    }

    public void setScalarColor(Scalar scalarColor) {
        this.scalarColor = scalarColor;
    }

    public Rect getRect() {
        return rect;
    }

    public Color getTileColor() {
        for (Color colorRangeKey : colorRanges.keySet()) {
            Scalar from = colorRanges.get(colorRangeKey)[0];
            Scalar to = colorRanges.get(colorRangeKey)[1];
            if (scalarColor.val[0] >= from.val[0] && scalarColor.val[0] <= to.val[0] &&
                    scalarColor.val[1] >= from.val[1] && scalarColor.val[1] <= to.val[1] &&
                    scalarColor.val[2] >= from.val[2] && scalarColor.val[2] <= to.val[2]) {
                return colorRangeKey;
            }
        }

        return null;
    }
}

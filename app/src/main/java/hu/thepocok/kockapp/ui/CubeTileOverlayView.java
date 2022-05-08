package hu.thepocok.kockapp.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import org.opencv.core.Point;

import java.util.ArrayList;

import hu.thepocok.kockapp.model.cube.component.Color;

public class CubeTileOverlayView extends View {
    private final String TAG = "CubeTileOverlayView";
    private final int BORDERWIDTH = 10;
    private final int TILESIZE = 100;

    private int width = 0;
    private int height = 0;
    private Point centerPoint;

    private ArrayList<Color> tileColors = new ArrayList<>();

    private Point[] cubeThreePieceOffset = new Point[]{
            new Point(-1, -1), new Point(-1, 0), new Point(-1, 1),
            new Point(0, -1), new Point(0, 0), new Point(0, 1),
            new Point(1, -1), new Point(1, 0), new Point(1, 1)
    };

    private Point[] cubeTwoPieceOffset = new Point[]{
            new Point(-1, -1), new Point(-1, 1),
            new Point(1, -1), new Point(1, 1)
    };

    public CubeTileOverlayView(Context context, AttributeSet attr) {
        super(context, attr);

        for (int i = 0; i < cubeThreePieceOffset.length; i++) {
            tileColors.add(Color.EMPTY);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(BORDERWIDTH);

        for (int i = 0; i < cubeThreePieceOffset.length; i++) {
            paint.setColor(android.graphics.Color.rgb(tileColors.get(i).redValue,
                    tileColors.get(i).greenValue,
                    tileColors.get(i).blueValue));

            Point tileOffset = cubeThreePieceOffset[i];
            canvas.drawRect((float) (centerPoint.x + (tileOffset.x * TILESIZE) + (tileOffset.x * BORDERWIDTH)),
                    (float) (centerPoint.y + (tileOffset.y * TILESIZE) + (tileOffset.y * BORDERWIDTH)),
                    (float) (centerPoint.x + ((tileOffset.x + 1) * TILESIZE) + ((tileOffset.x + 1) * BORDERWIDTH)),
                    (float) (centerPoint.y + ((tileOffset.y + 1) * TILESIZE) + ((tileOffset.y + 1) * BORDERWIDTH)),
                    paint);
        }

    }

    @Override
    protected void onSizeChanged(int xNew, int yNew, int xOld, int yOld){
        super.onSizeChanged(xNew, yNew, xOld, yOld);

        width = xNew;
        height = yNew;

        Log.d(TAG, "Width: " + width + " Height: " + height);

        centerPoint = new Point(xNew / 2, yNew / 2);
    }

    public void setTileColors(ArrayList<Color> tileColors) {
        this.tileColors.clear();
        this.tileColors = (ArrayList<Color>) tileColors.clone();

        invalidate();
    }
}

package hu.thepocok.kockapp.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

public class CubeFacePreviewView extends View {
    private final String TAG = "CubeFacePreviewView";
    private final int BORDERWIDTH = 1;
    private final int PREVIEWTEXTSIZE = 32;
    private final int FACETEXTSIZE = 16;

    private int fullWidth;
    private int fullHeight;
    private double sectionWidth;
    private double sectionHeight;
    private double tileWidth;

    private double textPositionX;
    private double textPositionY;
    private String previewText = "Cube faces";
    private String[] faceNames = new String[]{
            "WHITE",
            "RED",
            "GREEN",
            "ORANGE",
            "BLUE",
            "YELLOW"
    };

    private int cubeDimensions = 3;

    public CubeFacePreviewView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int xNew, int yNew, int xOld, int yOld){
        super.onSizeChanged(xNew, yNew, xOld, yOld);

        fullWidth = xNew;
        fullHeight = yNew;

        Log.d(TAG, "Width: " + fullWidth + " Height: " + fullWidth);

        sectionWidth = fullWidth * 0.2;
        sectionHeight = fullHeight * 0.4;

        textPositionX = (fullWidth / 2.0f) - (previewText.length() / 2.0f * PREVIEWTEXTSIZE / 2);
        textPositionY = fullHeight * 0.1;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        // Draw text
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(PREVIEWTEXTSIZE);
        canvas.drawText(previewText, (float) textPositionX, (float) textPositionY, paint);

        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(BORDERWIDTH);

        for (int i = 0; i < 2; i++) {
            double coordY = (fullHeight * 0.2) + (i * fullHeight * 0.4);

            for (int j = 0; j < 3; j++) {
                double coordX = (fullWidth * 0.2) + (j * sectionWidth);

                drawCubeFace(canvas, faceNames[(i * 3) + j], coordX, coordY);
            }
        }
    }

    public void drawCubeFace(Canvas canvas, String faceName, double coordX, double coordY) {
        double textPositionX = (sectionWidth / 2.0f) - (faceName.length() / 2.0f * FACETEXTSIZE / 2);
        double textPositionY = sectionHeight * 0.05;

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(FACETEXTSIZE);
        canvas.drawText(faceName, (float) (coordX + textPositionX), (float) (coordY + textPositionY), paint);

        double tileSize = (sectionWidth * 0.8) / 3.0f;
        double leftMargin = sectionWidth * 0.1;

        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(BORDERWIDTH);

        for (int y = 0; y < cubeDimensions; y++) {
            for (int x = 0; x < cubeDimensions; x++) {
                canvas.drawRect((float) (leftMargin + coordX + (tileSize * x)),
                        (float) ((textPositionY * 2) + coordY + (tileSize * y)),
                        (float) (leftMargin + coordX + (tileSize * (x + 1))),
                        (float) ((textPositionY * 2) + coordY + (tileSize * (y + 1))),
                        paint);
            }
        }
    }

    public void setCubeDimensions(int cubeDimensions) {
        this.cubeDimensions = cubeDimensions;
    }
}

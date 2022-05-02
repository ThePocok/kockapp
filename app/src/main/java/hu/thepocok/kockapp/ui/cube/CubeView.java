package hu.thepocok.kockapp.ui.cube;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class CubeView extends GLSurfaceView {
    private final CubeRenderer renderer;

    public CubeView(Context context){
        super(context);

        setEGLContextClientVersion(2);

        renderer = new CubeRenderer();
        setRenderer(renderer);
    }
}

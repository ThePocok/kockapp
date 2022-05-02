package hu.thepocok.kockapp.ui.cube;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class CubeFragment extends Fragment {
    private GLSurfaceView glSurfaceView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        glSurfaceView = new CubeView(getActivity());
        return glSurfaceView;
    }
}

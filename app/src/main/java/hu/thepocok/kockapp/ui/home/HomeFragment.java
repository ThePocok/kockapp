package hu.thepocok.kockapp.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import hu.thepocok.kockapp.CubeSolutionActivity;
import hu.thepocok.kockapp.R;
import hu.thepocok.kockapp.databinding.FragmentHomeBinding;
import hu.thepocok.kockapp.ui.ReadCubeFromCameraActivity;
import hu.thepocok.kockapp.ui.ReadCubeManuallyActivity;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        LinearLayout layout = root.findViewById(R.id.linear_layout_home);

        Button cubeByCameraBtn = binding.cubeByCamera;
        cubeByCameraBtn.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), ReadCubeFromCameraActivity.class);
            startActivity(intent);
        });

        Button cube3Manually = binding.cube3Manually;
        cube3Manually.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), ReadCubeManuallyActivity.class);
            intent.putExtra("cubeDimensions", 3);
            startActivity(intent);
        });

        Button cube2Manually = binding.cube2Manually;
        cube2Manually.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), ReadCubeManuallyActivity.class);
            intent.putExtra("cubeDimensions", 2);
            startActivity(intent);
        });
        
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
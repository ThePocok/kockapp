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

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        LinearLayout layout = root.findViewById(R.id.linear_layout_home);

        Button cubeByCameraBtn = new Button(getContext());
        cubeByCameraBtn.setText("Read cube from camera");
        cubeByCameraBtn.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), ReadCubeFromCameraActivity.class);
            startActivity(intent);
        });

        layout.addView(cubeByCameraBtn);

        Button cubeManually = new Button(getContext());
        cubeManually.setText("Read cube manually");
        cubeManually.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), ReadCubeManuallyActivity.class);
            intent.putExtra("cubeDimensions", 3);
            startActivity(intent);
        });

        layout.addView(cubeManually);


        Button solution = new Button(getContext());
        solution.setText("Solution");
        solution.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), CubeSolutionActivity.class);
            startActivity(intent);
        });

        layout.addView(solution);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
package com.example.todolistmobile.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.todolistmobile.R;
import com.example.todolistmobile.databinding.FragmentHomeBinding;
import com.example.todolistmobile.ui.CardAdapter;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private HomeViewModel homeViewModel;
    private SwipeRefreshLayout swipeRefreshLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        HomeViewModelFactory factory = new HomeViewModelFactory(requireActivity().getApplication());
        homeViewModel = new ViewModelProvider(this, factory).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView recyclerView = binding.recyclerViewCards;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        homeViewModel.getTaskList().observe(getViewLifecycleOwner(), taskList -> {
            if (taskList != null) {
                recyclerView.setAdapter(new CardAdapter(taskList));
            }

            swipeRefreshLayout.setRefreshing(false);
        });

        swipeRefreshLayout = root.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            homeViewModel.fetchCards();
        });

        return root;
    }

    public void onResume() {
        super.onResume();
        homeViewModel.fetchCards();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}


package com.example.todolistmobile.ui.modal;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.UserManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.todolistmobile.R;
import com.example.todolistmobile.model.AppUserManager;
import com.example.todolistmobile.ui.home.HomeViewModel;
import com.example.todolistmobile.ui.home.HomeViewModelFactory;
import com.google.android.material.snackbar.Snackbar;


import java.util.Calendar;

public class ModalFragment extends DialogFragment {
    private ModalViewModel modalViewModel;
    private HomeViewModel homeViewModel;
    private EditText editDate;
    private Button btnOk, btnCancel;
    private TextView editTaskTitle;
    private TextView editTaskDescription;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_custom, container, false);

        AppUserManager appUserManager = new AppUserManager(requireContext());
        ModalViewModelFactory factory = new ModalViewModelFactory(appUserManager);

        modalViewModel = new ViewModelProvider(this, factory).get(ModalViewModel.class);

        HomeViewModelFactory homeFactory = new HomeViewModelFactory(requireActivity().getApplication());
        homeViewModel = new ViewModelProvider(requireActivity(), homeFactory).get(HomeViewModel.class);

        editDate = view.findViewById(R.id.dialog_input_date);
        editTaskTitle = view.findViewById(R.id.dialog_task_title);
        editTaskDescription = view.findViewById(R.id.dialog_task_description);
        btnOk = view.findViewById(R.id.btn_ok);
        btnCancel = view.findViewById(R.id.btn_cancel);

        modalViewModel.getCloseEvent().observe(getViewLifecycleOwner(), shouldClose -> {
            if (shouldClose) {
                dismiss();
                modalViewModel.resetCloseEvent();
                homeViewModel.fetchCards();
            }
        });

        editDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                    (view1, selectedYear, selectedMonth, selectedDay) -> {
                        String selectedDate = selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay;
                        editDate.setText(selectedDate);
                    }, year, month, day);

            datePickerDialog.show();
        });

        modalViewModel.getIsTaskCreated().observe(getViewLifecycleOwner(), isCreated -> {
            if (isCreated) {
                dismiss();
                homeViewModel.fetchCards();
                modalViewModel.resetCloseEvent();
            }
        });

        modalViewModel.getTaskCreationError().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Snackbar.make(view, error, Snackbar.LENGTH_LONG).show();
            }
        });

        btnOk.setOnClickListener(v -> {
            String taskTitle = editTaskTitle.getText().toString();
            String taskDescription = editTaskDescription.getText().toString();
            String taskDueDate = editDate.getText().toString();
            if (taskTitle.isEmpty() || taskDescription.isEmpty() || taskDueDate.isEmpty()) {
                Snackbar.make(view, "Por favor, preencha todos os campos.", Snackbar.LENGTH_LONG).show();
            } else {
                modalViewModel.createTask(taskTitle, taskDescription, taskDueDate);
            }
        });

        btnCancel.setOnClickListener(v -> {
            modalViewModel.closeModal();
        });

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();

        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setLayout(
                    (int) (getResources().getDisplayMetrics().widthPixels * 0.9), // 90% da largura da tela
                    (int) (getResources().getDisplayMetrics().heightPixels * 0.5) // 50% da altura da tela
            );
        }
    }

}

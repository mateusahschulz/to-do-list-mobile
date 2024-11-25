package com.example.todolistmobile.ui.taskDetail;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.todolistmobile.R;
import com.example.todolistmobile.databinding.FragmentTaskDetailBinding;
import com.example.todolistmobile.model.Task;

import java.util.Calendar;

public class TaskDetailFragment extends Fragment {

    private FragmentTaskDetailBinding binding;
    private TaskDetailViewModel taskDetailViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentTaskDetailBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        taskDetailViewModel = new ViewModelProvider(this).get(TaskDetailViewModel.class);

        Task task = getArguments().getParcelable("task");

        if (task != null) {
            taskDetailViewModel.setTask(task);

            binding.taskTitle.setText(task.getTitle());
            binding.taskDescription.setText(task.getDescription());
            binding.taskDueDate.setText(task.getDueDate());

            String status = task.getStatus();
            binding.statusBadge.setText(status);

            updateStatusBadge(status);
        }

        binding.taskDueDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                    (view1, selectedYear, selectedMonth, selectedDay) -> {
                        String selectedDate = selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay;
                        binding.taskDueDate.setText(selectedDate);
                    }, year, month, day);

            datePickerDialog.show();
        });

        binding.advanceStatusButton.setOnClickListener(v -> {
            String currentStatus = task.getStatus();

            System.out.println(currentStatus);

            if (!currentStatus.equals("done")) {
                String nextStatus = getNextStatus(currentStatus);
                System.out.println(nextStatus);
                task.setStatus(nextStatus);
                taskDetailViewModel.updateTaskStatus(task);
            }
        });

        binding.saveButton.setOnClickListener(v -> {
            String updatedTitle = binding.taskTitle.getText().toString();
            String updatedDescription = binding.taskDescription.getText().toString();
            String updatedDueDate = binding.taskDueDate.getText().toString();

            if (updatedTitle.isEmpty() || updatedDescription.isEmpty() || updatedDueDate.isEmpty()) {
                Toast.makeText(getContext(), "Todos os campos precisam ser preenchidos", Toast.LENGTH_SHORT).show();
            } else {
                Task updatedTask = new Task(
                        task.getId(),
                        updatedTitle,
                        updatedDescription,
                        updatedDueDate,
                        task.getUserId(),
                        task.getStatus()
                );
                taskDetailViewModel.updateTask(updatedTask);
            }
        });

        binding.deleteButton.setOnClickListener(v -> {
            taskDetailViewModel.deleteTask(task.getId());
        });

        taskDetailViewModel.getIsTaskUpdated().observe(getViewLifecycleOwner(), isUpdated -> {
            if (isUpdated != null && isUpdated) {
                System.out.println("Rodando aqui");
                Navigation.findNavController(root).popBackStack();
            }
        });

        taskDetailViewModel.getIsTaskDeleted().observe(getViewLifecycleOwner(), isDeleted -> {
            if (isDeleted != null && isDeleted) {
                Navigation.findNavController(root).popBackStack();
            }
        });

        taskDetailViewModel.getTaskUpdateError().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Toast.makeText(getContext(), "Erro: " + error, Toast.LENGTH_SHORT).show();
            }
        });

        taskDetailViewModel.getIsTaskStatusUpdated().observe(getViewLifecycleOwner(), isStatusUpdated -> {
            if (isStatusUpdated != null && isStatusUpdated) {
                String updatedStatus = task.getStatus();
                updateStatusBadge(updatedStatus);
                if (updatedStatus.equals("done")) {
                    binding.advanceStatusButton.setEnabled(false);
                }
                Navigation.findNavController(root).popBackStack();
            }
        });

        taskDetailViewModel.getTaskUpdateStatusError().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Toast.makeText(getContext(), "Erro ao avançar status: " + error, Toast.LENGTH_SHORT).show();
            }
        });

        taskDetailViewModel.getTaskDeleteError().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Toast.makeText(getContext(), "Erro ao excluir a tarefa: " + error, Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

    @SuppressLint("SetTextI18n")
    private void updateStatusBadge(String status) {
        switch (status) {
            case "todo":
                binding.statusBadge.setBackgroundResource(R.drawable.status_todo);
                binding.statusBadge.setText("À Fazer");
                break;
            case "in_progress":
                binding.statusBadge.setBackgroundResource(R.drawable.status_in_progress);
                binding.statusBadge.setText("Em Progresso");
                break;
            case "done":
                binding.statusBadge.setBackgroundResource(R.drawable.status_done);
                binding.statusBadge.setText("Feito");
                break;
            default:
                binding.statusBadge.setBackgroundResource(R.drawable.status_todo);
                break;
        }
    }

    private String getNextStatus(String currentStatus) {
        switch (currentStatus) {
            case "todo":
                return "in_progress";
            case "in_progress":
                return "done";
            default:
                return currentStatus;
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

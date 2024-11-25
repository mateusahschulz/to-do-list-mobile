package com.example.todolistmobile.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolistmobile.R;
import com.example.todolistmobile.model.Task;

import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

    private final List<Task> cardList;

    public CardAdapter(List<Task> cardList) {
        this.cardList = cardList;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_card_view, parent, false);
        return new CardViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        Task card = cardList.get(position);
        holder.title.setText(card.getTitle());
        holder.description.setText(card.getDescription());


        TextView statusBadge = holder.statusBadge;
        String status = card.getStatus();

        switch (status) {
            case "todo":
                statusBadge.setText("À Fazer");
                statusBadge.setBackgroundResource(R.drawable.status_todo);
                break;
            case "in_progress":
                statusBadge.setText("Em progresso");
                statusBadge.setBackgroundResource(R.drawable.status_in_progress);
                break;
            case "done":
                statusBadge.setText("Feito");
                statusBadge.setBackgroundResource(R.drawable.status_done);
                break;
            default:
                statusBadge.setText("Desconhecido");
                statusBadge.setBackgroundResource(R.drawable.status_todo);
                break;
        }

        holder.itemView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putParcelable("task", card);
            Navigation.findNavController(v).navigate(R.id.action_nav_home_to_taskDetailFragment, bundle);
        });
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    static class CardViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView description;
        TextView statusBadge;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.card_title);
            description = itemView.findViewById(R.id.card_description);
            statusBadge = itemView.findViewById(R.id.status_badge);
        }
    }
}


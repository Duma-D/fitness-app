package com.fitness.app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TargetAdapter extends RecyclerView.Adapter<TargetAdapter.ViewHolder>{

    private List<String> targetList;
    private TargetAdapter.RecyclerViewClickListener listener;

    public TargetAdapter(List<String> targetList, RecyclerViewClickListener listener) {
        this.targetList = targetList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TargetAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_targets, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TargetAdapter.ViewHolder holder, int position) {
        holder.mTarget.setText(targetList.get(position));
    }

    @Override
    public int getItemCount() {
        return targetList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mTarget;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mTarget = itemView.findViewById(R.id.textViewTargetMuscle);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            listener.onClick(view, getAdapterPosition());
        }
    }

    public interface RecyclerViewClickListener{
        void onClick(View v, int position);
    }
}

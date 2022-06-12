package com.fitness.app.Data;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.fitness.app.Activities.MainActivity;
import com.fitness.app.R;

import java.util.List;

import pl.droidsonroids.gif.GifImageView;


public class ExercisesAdapter extends RecyclerView.Adapter<ExercisesAdapter.ViewHolder>{

    private List<Exercise> exerciseList;
    private RecyclerViewClickListener listener;

    public ExercisesAdapter(List<Exercise> exerciseList, RecyclerViewClickListener listener) {
        this.exerciseList = exerciseList;
        this.listener = listener;
    }


    @NonNull
    @Override
    public ExercisesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExercisesAdapter.ViewHolder holder, int position) {
        holder.txtName.setText(exerciseList.get(position).getName().toUpperCase());
        holder.txtBodyPart.setText("Target muscle: " + exerciseList.get(position).getTarget());
        String gifURL = exerciseList.get(position).getGifUrl();
        if(!gifURL.isEmpty()) {
            Glide
                    .with(holder.itemView)
                    .load(gifURL)
                    /*.override(76, 76)*/
                    .placeholder(R.drawable.img_3)
                    .into(holder.gif);
        }
    }


    @Override
    public int getItemCount() {
        return exerciseList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        TextView txtName;
        TextView txtBodyPart;
        GifImageView gif;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.textViewExName);
            txtBodyPart = itemView.findViewById(R.id.textViewBodyPart);
            gif = itemView.findViewById(R.id.gifImageView);
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

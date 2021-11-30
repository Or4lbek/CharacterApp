package com.example.retrofitapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Adaptery extends RecyclerView.Adapter<Adaptery.MyViewHolder> {

    private Context mContext;
    private List<Character> characters;
    private OnItemNoteListener mOnItemNoteListener;

    public Adaptery(List<Character> charactersList, Context mContext, OnItemNoteListener mOnItemNoteListener) {
        this.mContext = mContext;
        this.characters = charactersList;
        this.mOnItemNoteListener = mOnItemNoteListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        view = layoutInflater.inflate(R.layout.character_record_item, parent, false);
        return new MyViewHolder(view, mOnItemNoteListener);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.name.setText(characters.get(position).getName());
        holder.birthday.setText(characters.get(position).getBirthday());
        holder.status.setText(characters.get(position).getStatus());
        holder.category.setText(characters.get(position).getCategory());


        System.out.println(characters.get(position).getImg());
        Picasso.with(mContext)
                .load(characters.get(position).getImg())
                .placeholder(R.drawable.back)
                .fit()
                .into(holder.image);


    }

    @Override
    public int getItemCount() {
        return characters.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name;
        TextView birthday;
        TextView status;
        TextView category;
        ImageView image;
        OnItemNoteListener noteListener;

        public MyViewHolder( View itemView, OnItemNoteListener noteListener) {
            super(itemView);
            name = itemView.findViewById(R.id.textViewTitle);
            birthday = itemView.findViewById(R.id.textViewBirthDate);
            status = itemView.findViewById(R.id.textViewStatus);
            category = itemView.findViewById(R.id.textViewCategory);
            image = itemView.findViewById(R.id.imageViewUser);
            this.noteListener = noteListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            noteListener.onNoteClick(getAdapterPosition());
        }
    }

    public interface OnItemNoteListener{
        void onNoteClick(int position);
    }
}

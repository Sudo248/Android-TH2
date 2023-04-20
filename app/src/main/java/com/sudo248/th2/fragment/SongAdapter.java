package com.sudo248.th2.fragment;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sudo248.th2.R;
import com.sudo248.th2.model.Song;

import java.util.ArrayList;
import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Song song);
    }

    public SongAdapter(ArrayList<Song> songs) {
        this.songs = songs;
    }

    ArrayList<Song> songs;

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void submitList(ArrayList<Song> songs) {
        this.songs.clear();
        this.songs.addAll(songs);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SongViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
        holder.onBind(songs.get(position));
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    class SongViewHolder extends RecyclerView.ViewHolder {

        private TextView txtSongName, txtSingerName, txtAlbum, txtType;
        ImageView imgLike;

        public SongViewHolder(@NonNull View itemView) {
            super(itemView);
            txtSongName = itemView.findViewById(R.id.txtSongName);
            txtSingerName = itemView.findViewById(R.id.txtSingerName);
            txtAlbum = itemView.findViewById(R.id.txtAlbum);
            txtType = itemView.findViewById(R.id.txtType);
            imgLike = itemView.findViewById(R.id.imgLike);
        }

        public void onBind(Song song) {
            txtSongName.setText("Name song: " + song.getSongName());
            txtSingerName.setText("Singer: " + song.getSinger().getSingerName());
            txtAlbum.setText("Album: " + song.getAlbum());
            txtType.setText("Type: " + song.getType());
            if (song.isLike()) {
                imgLike.setImageTintList(ColorStateList.valueOf(Color.RED));
            } else {
                imgLike.setImageTintList(ColorStateList.valueOf(Color.WHITE));
            }

            itemView.setOnClickListener((v) -> onItemClickListener.onItemClick(song));
        }

    }

}

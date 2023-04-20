package com.sudo248.th2.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.sudo248.th2.Consts;
import com.sudo248.th2.Database;
import com.sudo248.th2.R;
import com.sudo248.th2.SongActivity;
import com.sudo248.th2.databinding.FragmentListBinding;
import com.sudo248.th2.model.Song;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {

    private View rootView;
    private Database database;
    private SongAdapter adapter;
    private RecyclerView rcv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_list, container, false);
        database = new Database(requireContext());
        ArrayList<Song> songs = database.getAllSong();
        adapter = new SongAdapter(songs);
        rcv = rootView.findViewById(R.id.rcvSong);
        rcv.setAdapter(adapter);

        adapter.setOnItemClickListener((song) -> {
            Intent intent = new Intent(requireActivity(), SongActivity.class);
            intent.putExtra(Consts.KEY_SONG, song);
            startActivity(intent);
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.submitList(database.getAllSong());
    }
}

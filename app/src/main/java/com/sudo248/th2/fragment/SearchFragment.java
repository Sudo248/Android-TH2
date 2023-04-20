package com.sudo248.th2.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.sudo248.th2.Consts;
import com.sudo248.th2.Database;
import com.sudo248.th2.R;
import com.sudo248.th2.SongActivity;
import com.sudo248.th2.model.Song;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SearchFragment extends Fragment {

    private View rootView;

    Spinner spinnerAlbum;
    RecyclerView rcvSong;
    SongAdapter adapter;

    Button btnStatistic;

    TextView txtResultStatistic;

    Database database;

    private int currentSelectedAlbum = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_search, container, false);
        database = new Database(requireContext());

        rcvSong = rootView.findViewById(R.id.rcvSong);
        adapter = new SongAdapter(new ArrayList<>());
        rcvSong.setAdapter(adapter);
        adapter.setOnItemClickListener((song) ->{
            Intent intent = new Intent(requireActivity(), SongActivity.class);
            intent.putExtra(Consts.KEY_SONG, song);
            startActivity(intent);
        });

        spinnerAlbum = rootView.findViewById(R.id.spinnerAlbum);
        spinnerAlbum.setAdapter(new ArrayAdapter<String>(requireContext(), R.layout.item_spinner, Consts.ALBUMS));
        spinnerAlbum.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (rcvSong.getVisibility() == View.GONE) {
                    rcvSong.setVisibility(View.VISIBLE);
                    txtResultStatistic.setVisibility(View.GONE);
                }
                currentSelectedAlbum = i;
                ArrayList<Song> songs = database.searchSongByAlbum(spinnerAlbum.getAdapter().getItem(i).toString());
                adapter.submitList(songs);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnStatistic = rootView.findViewById(R.id.btnStatistic);
        txtResultStatistic = rootView.findViewById(R.id.txtResultStatistic);

        btnStatistic.setOnClickListener((v) ->{

            Map<String, Integer> result = database.statistic();
            List<Map.Entry<String, Integer>> list = new ArrayList<>(result.entrySet());
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                list.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
//            }
            StringBuilder resultString = new StringBuilder();
            for (Map.Entry<String, Integer> entry : list) {
                resultString.append("Type: ").append(entry.getKey()).append(" có ").append(entry.getValue()).append(" bài hát.\n\n");
            }
            txtResultStatistic.setText(resultString.toString());

            rcvSong.setVisibility(View.GONE);
            txtResultStatistic.setVisibility(View.VISIBLE);
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        spinnerAlbum.setSelection(currentSelectedAlbum);
    }
}

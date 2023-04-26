package com.sudo248.th2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.sudo248.th2.model.Song;

import java.util.Arrays;

public class SongActivity extends AppCompatActivity {

    private Song song = null;
    private View rootView;

    TextView txtTitle;
    ImageView imgBack, imgLike;
    EditText edtNameSong, edtNameSinger;
    Spinner spinnerAlbum, spinnerType;
    Button btnDelete, btnSave;

    private int currentSelectedItemAlbum = 0;
    private int currentSelectedItemType = 0;

    private boolean isLike = false;

    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView = getLayoutInflater().inflate(R.layout.activity_song, null);
        setContentView(rootView);
        bindView();
        setupSpinner();

        database = new Database(this);
        try {
            song = (Song) getIntent().getSerializableExtra(Consts.KEY_SONG);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (song == null) {
            viewCreate();
        } else {
            viewUpdate();
        }

    }

    private void viewUpdate() {
        txtTitle.setText("Update Song");
        edtNameSong.setText(song.getSongName());
        edtNameSinger.setText(song.getSinger());
        btnDelete.setVisibility(View.VISIBLE);
        btnSave.setText("Update");
        isLike = song.isLike();
        setupImageIsLike();

        spinnerAlbum.setSelection(Arrays.asList(Consts.ALBUMS).indexOf(song.getAlbum()));
        spinnerType.setSelection(Arrays.asList(Consts.TYPES).indexOf(song.getType()));

        btnDelete.setOnClickListener((v) -> {
            database.deleteSong(song.getSongId());
            onBackPressed();
        });

        btnSave.setOnClickListener((v) -> {
            song.setSongName(edtNameSong.getText().toString());
            song.setAlbum(Consts.ALBUMS[currentSelectedItemAlbum]);
            song.setType(Consts.TYPES[currentSelectedItemType]);
            song.setSinger(edtNameSinger.getText().toString());
            song.setLike(isLike);
            database.updateSong(song);
            Log.d("Sudoo", "viewUpdate: " + currentSelectedItemAlbum + " " + song.getAlbum());
            onBackPressed();
        });
    }

    private void viewCreate() {
        btnDelete.setVisibility(View.GONE);
        spinnerType.setSelection(0);
        spinnerAlbum.setSelection(0);
        btnSave.setOnClickListener((v) -> {
            song = new Song();
            song.setSongName(edtNameSong.getText().toString());
            song.setAlbum(spinnerAlbum.getAdapter().getItem(currentSelectedItemAlbum).toString());
            song.setType(spinnerType.getAdapter().getItem(currentSelectedItemType).toString());
            song.setSinger(edtNameSinger.getText().toString());
            song.setLike(isLike);

            database.insertSong(song);
            onBackPressed();
        });
    }

    private void setupImageIsLike() {
        if (isLike) {
            imgLike.setImageTintList(ColorStateList.valueOf(Color.RED));
        } else {
            imgLike.setImageTintList(ColorStateList.valueOf(Color.WHITE));
        }
    }

    private void setupSpinner() {

        spinnerType.setAdapter(new ArrayAdapter<String>(this, R.layout.item_spinner, Consts.TYPES));
        spinnerAlbum.setAdapter(new ArrayAdapter<String>(this, R.layout.item_spinner, Consts.ALBUMS));

        spinnerAlbum.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currentSelectedItemAlbum = i;
                Log.d("Sudoo", "onItemSelected: " + i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currentSelectedItemType = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void bindView() {
        txtTitle = findViewById(R.id.txtTitle);
        imgBack = findViewById(R.id.imgBack);
        imgLike = findViewById(R.id.imgLike);
        edtNameSong = findViewById(R.id.edtNameSong);
        edtNameSinger = findViewById(R.id.edtNameSinger);
        spinnerAlbum = findViewById(R.id.spinnerAlbum);
        spinnerType = findViewById(R.id.spinnerType);
        btnDelete = findViewById(R.id.btnDelete);
        btnSave = findViewById(R.id.btnSave);

        imgLike.setOnClickListener((v) -> {
            isLike = !isLike;
            setupImageIsLike();
        });

        imgBack.setOnClickListener((v) -> {
            onBackPressed();
        });
    }
}
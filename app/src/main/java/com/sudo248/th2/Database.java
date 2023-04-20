package com.sudo248.th2;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.sudo248.th2.model.Singer;
import com.sudo248.th2.model.Song;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Database extends SQLiteOpenHelper {

    private final static String DATABASE_NAME = "sudo.db";
    private final static int DATABASE_VERSION = 1;

    public Database(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table singers (" +
                "singerId integer primary key autoincrement," +
                "singerName text," +
                "image integer," +
                "songInfo text)";

        db.execSQL(sql);

        sql = "create table songs (" +
                "songId integer primary key autoincrement," +
                "songName text," +
                "singerId integer," +
                "album text," +
                "type text," +
                "isLike integer," +
                "foreign key(singerId) references singers(singerId))";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insertSinger(Singer singer) {
        String sql = "insert into singers (singerName, image, songInfo) values (?,?,?)";
        String[] args = {singer.getSingerName(), String.valueOf(singer.getImage()), singer.getSongInfo()};
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql, args);
        db.close();
    }

    public Singer searchSingerByName(String singerName) {
        String sql = "select * from singers where singerName like ? limit 1";
        String[] args = {"%"+singerName+"%"};
        Singer singer = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, args);
        while ((cursor != null) && cursor.moveToNext()) {
            singer = new Singer(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getString(3));
        }
        db.close();
        return singer;
    }

    public void insertSong(Song song) {
        String sql = "insert into songs(songName,singerId,album,type,isLike) values (?,?,?,?,?)";
        String[] args = {song.getSongName(), String.valueOf(song.getSinger().getSingerId()), song.getAlbum(), song.getType(), String.valueOf(song.isLike())};
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql, args);
        db.close();
    }

    public int deleteSong(int songId) {
        String where="songId=?";
        String[] agrs = {Integer.toString(songId)};
        SQLiteDatabase db=getWritableDatabase();
        return db.delete("songs",where,agrs);
    }

    public int updateSong(Song song) {
        ContentValues values=new ContentValues();
        values.put("songName", song.getSongName());
        values.put("singerId", song.getSinger().getSingerId());
        values.put("album", song.getAlbum());
        values.put("type", song.getType());
        values.put("isLike", song.isLike());
        String where="songId=?";
        String[] agrs = {Integer.toString(song.getSongId())};
        SQLiteDatabase db=getWritableDatabase();
        return db.update("songs",values,where,agrs);
    }

    public List<Singer> getAllSinger() {
        List<Singer> result = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("singers", null, null, null, null, null, null);
        while ((cursor != null) && cursor.moveToNext()) {
            result.add(new Singer(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getString(3)));
        }
        db.close();
        return result;
    }

    public ArrayList<Song> getAllSong() {
        ArrayList<Song> result = new ArrayList<>();
        String sql = "select songs.songId, songs.songName, songs.album, songs.type, songs.isLike, singers.singerId, singers.singerName, singers.image, singers.songInfo " +
                "from singers inner join songs " +
                "on singers.singerId = songs.singerId";

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        while ((cursor != null) && cursor.moveToNext()) {
            result.add(new Song(
                    cursor.getInt(0),
                    cursor.getString(1),
                    new Singer(
                            cursor.getInt(5),
                            cursor.getString(6),
                            cursor.getInt(7),
                            cursor.getString(8)
                    ),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getInt(4) == 1
            ));
        }
        db.close();
        return result;
    }

    public ArrayList<Song> searchSongByAlbum(String album) {
        ArrayList<Song> result = new ArrayList<>();
        String sql = "select songs.songId, songs.songName, songs.album, songs.type, songs.isLike, singers.singerId, singers.singerName, singers.image, singers.songInfo " +
                "from singers inner join songs " +
                "on singers.singerId = songs.singerId where songs.album=?";

        SQLiteDatabase db = getReadableDatabase();
        String[] args = {album};
        Cursor cursor = db.rawQuery(sql, args);

        while ((cursor != null) && cursor.moveToNext()) {
            result.add(new Song(
                    cursor.getInt(0),
                    cursor.getString(1),
                    new Singer(
                            cursor.getInt(5),
                            cursor.getString(6),
                            cursor.getInt(7),
                            cursor.getString(8)
                    ),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getInt(4) == 1
            ));
        }
        db.close();
        return result;
    }

    public Map<String, Integer> statistic() {
        Map<String, Integer> result = new HashMap<>();

        String sql = "select type, count(*) " +
                "from songs "+
                "group by type " +
                "order by count(*) desc";
        SQLiteDatabase db = getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(sql, null);

//        SQLiteDatabase db = getReadableDatabase();
//        String[] columns = {"type", "count(*)"};
//        String groupBy = "type";
//        String orderBy = "count(*) desc";
//        Cursor cursor = db.query("songs", columns, null, null, groupBy, null, orderBy);

        while ((cursor != null) && cursor.moveToNext()) {
            result.put(cursor.getString(0), cursor.getInt(1));
        }
        return result;
    }

//    public List<>
}

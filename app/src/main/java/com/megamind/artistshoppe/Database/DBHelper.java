package com.megamind.artistshoppe.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "albums";
    public static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "artists";
    public static final String COL_1 = "FIRST_NAME";
    public static final String COL_2 = "LAST_NAME";
    public static final String COL_3 = "ALBUM_NAME";
    public static final String COL_4 = "NUMBER_OF_ALBUMS";
    public static final String COL_5 = "PRICE";

    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + " " +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_1 + " VARCHAR(50), " +
                COL_2 + " VARCHAR(50), " +
                COL_3 + " VARCHAR(50), " +
                COL_4 + " NUMBER, " +
                COL_5 + " DECIMAL(2,4))";
        db.execSQL(sql);
    }

    public long addArtist(String firstName, String lastName, String albumName, int numberOfAlbums, Double price) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_1, firstName);
        contentValues.put(COL_2, lastName);
        contentValues.put(COL_3, albumName);
        contentValues.put(COL_4, numberOfAlbums);
        contentValues.put(COL_5, price);
        long add = db.insert(TABLE_NAME, null, contentValues);
        db.close();

        return add;
    }

    public int updateArtist(String artistID, String firstName, String lastName, String albumName, int numberOfAlbums, Double price) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_1, firstName);
        contentValues.put(COL_2, lastName);
        contentValues.put(COL_3, albumName);
        contentValues.put(COL_4, numberOfAlbums);
        contentValues.put(COL_5, price);
        int update = db.update(TABLE_NAME, contentValues, "ID = ?", new String[] {artistID});
        db.close();

        return update;
    }

    public int removeArtist(String artistID) {
        SQLiteDatabase db = getWritableDatabase();
        int remove = db.delete(TABLE_NAME, "ID = ?", new String[] {artistID});
        db.close();

        return remove;
    }

    public Cursor viewAllArtists() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        return cursor;
    }

    public Cursor viewSingleArtist(String id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE ID = ?", new String[]{id});

        return cursor;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(sql);

        onCreate(db);
    }
}

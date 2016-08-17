package com.lkmhr.app.swatches.com.lkmhr.app.swatches.dbutils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.lkmhr.app.swatches.com.lkmhr.app.swatches.utils.Color;
import com.lkmhr.app.swatches.com.lkmhr.app.swatches.utils.ColorGroup;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "lk_swatches";

    private static final String TABLE_SWATCH_GROUP = "swatches";
    private static final String TABLE_SWATCH_COLOR = "colors";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_DESC = "description";
    private static final String KEY_CODE = "code";
    private static final String KEY_GROUP = "group_id";

    public DatabaseHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_GROUP_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_SWATCH_GROUP + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_DESC + " TEXT"
                + ")";
        String CREATE_COLOR_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_SWATCH_COLOR + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_CODE + " TEXT,"
                + KEY_GROUP + " INTEGER"
                + ")";

        db.execSQL(CREATE_GROUP_TABLE);
        db.execSQL(CREATE_COLOR_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SWATCH_GROUP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SWATCH_COLOR);

        onCreate(db);
    }

    public void addGroup(ColorGroup colorGroup) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, colorGroup.getName());
        values.put(KEY_DESC, colorGroup.getDescription());

        // Inserting Row
        db.insert(TABLE_SWATCH_GROUP, null, values);
        db.close();
    }

    public void addColor(Color color) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, color.getName());
        values.put(KEY_CODE, color.getHexCode());
        values.put(KEY_GROUP, color.getGroupId());

        // Inserting Row
        db.insert(TABLE_SWATCH_COLOR, null, values);
        db.close();
    }

    public List<ColorGroup> getGroups() {
        List<ColorGroup> colorGroups = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_SWATCH_GROUP;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ColorGroup colorGroup = new ColorGroup();
                colorGroup.setId(Integer.parseInt(cursor.getString(0)));
                colorGroup.setName(cursor.getString(1));
                colorGroup.setDescription(cursor.getString(2));

                colorGroups.add(colorGroup);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return colorGroups;
    }

    public List<Color> getColors(int groupId) {
        List<Color> colors = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_SWATCH_COLOR
                + " WHERE " + KEY_GROUP + "=" + groupId;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Color color = new Color();
                color.setId(Integer.parseInt(cursor.getString(0)));
                color.setName(cursor.getString(1));
                color.setHexCode(cursor.getString(2));
                color.setGroupId(Integer.parseInt(cursor.getString(3)));

                colors.add(color);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return colors;
    }

    public int updateGroup(ColorGroup colorGroup) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, colorGroup.getName());
        values.put(KEY_DESC, colorGroup.getDescription());

        // updating row
        return db.update(TABLE_SWATCH_GROUP, values, KEY_ID + " = ?",
                new String[] { String.valueOf(colorGroup.getId()) });
    }

    public int updateColor(Color color) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, color.getName());
        values.put(KEY_CODE, color.getHexCode());

        // updating row
        return db.update(TABLE_SWATCH_COLOR, values, KEY_ID + " = ?",
                new String[] { String.valueOf(color.getId()) });
    }

    public void deleteGroup(int id) {
        deleteAllColors(id);

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_SWATCH_GROUP, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
    }

    public void deleteColor(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SWATCH_COLOR, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
    }

    public void deleteAllColors(int groupId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SWATCH_COLOR, KEY_GROUP + " = ?",
                new String[] { String.valueOf(groupId) });
        db.close();
    }
}

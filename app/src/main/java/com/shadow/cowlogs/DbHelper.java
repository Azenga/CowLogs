package com.shadow.cowlogs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.shadow.cowlogs.models.LogEntry;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "cowlogs.db";

    private static final String TABLE1 = "entries";
    private static final String COL1 = "id";
    private static final String COL2 = "condition";
    private static final String COL3 = "breed";
    private static final String COL4 = "weight";
    private static final String COL5 = "age";
    private static final String COL6 = "entry_date";


    public DbHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE1 + "(" +
                COL1 + " INTEGER PRIMARY KEY," +
                COL2 + " TEXT NOT NULL," +
                COL3 + " TEXT NOT NULL," +
                COL4 + " REAL NOT NULL," +
                COL5 + " INTEGER NOT NULL," +
                COL6 + " STRING NOT NULL)";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE1);
        onCreate(db);
    }

    public boolean addEntry(LogEntry entry) {
        SQLiteDatabase db = getWritableDatabase();


        ContentValues values = new ContentValues();

        values.put(COL1, entry.getId());
        values.put(COL2, entry.getCondition());
        values.put(COL3, entry.getBreed());
        values.put(COL4, entry.getWeight());
        values.put(COL5, entry.getAge());
        values.put(COL6, entry.getDateTime());

        long row = db.insert(TABLE1, null, values);

        return row != -1;
    }

    public List<LogEntry> getEntryList() {

        SQLiteDatabase db = getReadableDatabase();

        List<LogEntry> entryList = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE1 + " ORDER BY " + COL1 + " DESC";

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(COL1));
                int age = cursor.getInt(cursor.getColumnIndex(COL5));
                double weight = cursor.getDouble(cursor.getColumnIndex(COL4));
                String breed = cursor.getString(cursor.getColumnIndex(COL3));
                String condition = cursor.getString(cursor.getColumnIndex(COL2));
                String date = cursor.getString(cursor.getColumnIndex(COL6));

                LogEntry entry = new LogEntry(condition, date, breed, id, weight, age);

                entryList.add(entry);
            }
        }

        return entryList;
    }

    public boolean deleteAllEntries(){

        SQLiteDatabase db = getWritableDatabase();

        String query = "DELETE FROM " + TABLE1;

        db.rawQuery(query, null);

        return true;
    }
}

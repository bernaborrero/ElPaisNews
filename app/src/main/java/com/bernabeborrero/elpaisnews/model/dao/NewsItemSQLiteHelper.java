package com.bernabeborrero.elpaisnews.model.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by berna on 20/02/15.
 */
public class NewsItemSQLiteHelper extends SQLiteOpenHelper {

    private final String SQL_CREATE_NEWSITEMS = "CREATE TABLE NewsItems(" +
            "   id INTEGER PRIMARY KEY, " +
            "   title TEXT, " +
            "   description TEXT, " +
            "   content TEXT, " +
            "   link TEXT, " +
            "   section INTEGER, " +
            "   image BLOB)";

    private final String SQL_DROP_NEWSITEMS = "DROP TABLE IF EXISTS NewsItems";

    public NewsItemSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_NEWSITEMS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DROP_NEWSITEMS);
        db.execSQL(SQL_CREATE_NEWSITEMS);
    }
}

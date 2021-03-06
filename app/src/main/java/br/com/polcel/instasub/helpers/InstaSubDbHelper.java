package br.com.polcel.instasub.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import br.com.polcel.instasub.contracts.InstaSubContract;

/**
 * Created by polcel on 23/03/17.
 */

public class InstaSubDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "InstaSub.db";

    public InstaSubDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(InstaSubContract.InstaSub.SQL_CREATE_ENTRIES);
      //  db.execSQL(InstaSubContract.InstaSub.SQL_CREATE_TEMP_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL(InstaSubContract.InstaSub.SQL_DELETE_ENTRIES);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}

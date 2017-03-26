package br.com.polcel.instasub.contracts;

import android.provider.BaseColumns;

/**
 * Created by polcel on 23/03/17.
 */

public final class InstaSubContract {

    public static final String SELECT_ALL_SUBS = "SELECT * FROM " + InstaSub.TABLE_NAME;

    private InstaSubContract() {

    }

    public static class InstaSub implements BaseColumns {

        public static final String TABLE_NAME = "SUBS";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_CREATED = "created";
        public static final String COLUMN_NAME_DELETED = "deleted";

        private static final String TEXT_TYPE = " TEXT";
        private static final String DATE_TYPE = " INTEGER";

        private static final String COMMA_SEP = ",";
        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + InstaSub.TABLE_NAME + " (" +
                        InstaSub._ID + " INTEGER PRIMARY KEY," +
                        InstaSub.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                        InstaSub.COLUMN_NAME_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                        InstaSub.COLUMN_NAME_CREATED + DATE_TYPE + " )";

        public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + InstaSub.TABLE_NAME;
    }

}

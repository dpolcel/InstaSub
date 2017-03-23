package br.com.polcel.instasub;

import android.provider.BaseColumns;

/**
 * Created by polcel on 23/03/17.
 */

public final class InstaSubContract {
    private InstaSubContract() {

    }

    public static class InstaSub implements BaseColumns {

        public static final String TABLE_NAME = "SUBS";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_CREATED = "created";
        public static final String COLUMN_NAME_DELETED = "deleted";


    }

}

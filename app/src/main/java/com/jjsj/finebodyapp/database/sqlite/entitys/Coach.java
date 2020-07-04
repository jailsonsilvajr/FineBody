package com.jjsj.finebodyapp.database.sqlite.entitys;

import android.provider.BaseColumns;

public class Coach implements BaseColumns {

    private long id_sqlite;
    private String id_firebase;

    public long getId_sqlite() {
        return id_sqlite;
    }

    public void setId_sqlite(long id_sqlite) {
        this.id_sqlite = id_sqlite;
    }

    public String getId_firebase() {
        return id_firebase;
    }

    public void setId_firebase(String id_firebase) {
        this.id_firebase = id_firebase;
    }

    public void setIdCoachFirebase(String id_firebase) {
        this.id_firebase = id_firebase;
    }

    public static final String TABLE_NAME = "coach";

    public static final String NAME_COLUMN_ID_SQLITE = "id_sqlite";
    public static final String NAME_COLUMN_ID_FIREBASE = "id_firebase";

    public static final String SQL_CREATE_TABLE_COACH =
            "CREATE TABLE " + Coach.TABLE_NAME + " (" +
                    Coach.NAME_COLUMN_ID_SQLITE + " INTEGER PRIMARY KEY," +
                    Coach.NAME_COLUMN_ID_FIREBASE + " TEXT)";

    public static final String SQL_DELETE_TABLE_COACH =
            "DROP TABLE IF EXISTS " + Coach.TABLE_NAME;
}

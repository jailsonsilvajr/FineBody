package com.jjsj.finebodyapp.database.sqlite.entitys;

import android.provider.BaseColumns;

public class Coach implements BaseColumns {

    private long id;
    private String idCoach;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIdCoach() {
        return idCoach;
    }

    public void setIdCoach(String idCoach) {
        this.idCoach = idCoach;
    }

    public static final String TABLE_NAME = "coach";
    public static final String NAME_COLUMN_ID_FIREBASE = "idCoach";

    public static final String SQL_CREATE_TABLE_COACH =
            "CREATE TABLE " + Coach.TABLE_NAME + " (" +
                    Coach._ID + " INTEGER PRIMARY KEY," +
                    Coach.NAME_COLUMN_ID_FIREBASE + " TEXT)";

    public static final String SQL_DELETE_TABLE_COACH =
            "DROP TABLE IF EXISTS " + Coach.TABLE_NAME;
}

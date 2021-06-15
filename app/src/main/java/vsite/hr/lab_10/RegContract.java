package vsite.hr.lab_10;

import android.provider.BaseColumns;

public class RegContract {

    private  RegContract(){}
    public final static String DATABASE_NAME = "Reg.db";
    public static abstract class Reg implements BaseColumns {
        public static String TABLE_NAME   = "Reg";
        public static String COLUMN_POSTA   = "Posta";
        public static String COLUMN_IME   = "Ime";
        public static String COLUMN_PASSWORD  = "Lozinka";

        public static String CREATE_TABLE_SCRIPT =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY, "  +
                        COLUMN_POSTA + " TEXT NOT NULL, " +
                        COLUMN_IME + " TEXT NOT NULL, " +
                        COLUMN_PASSWORD + " TEXT NOT NULL" +
                        ")";

    }
}


package vsite.hr.lab_10;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import vsite.hr.lab_10.RegContract.Reg;

public class RegHelper extends SQLiteOpenHelper {
    final static int DATABASE_VERSION = 1;

    public RegHelper(Context context){
        super(context, RegContract.DATABASE_NAME, null, DATABASE_VERSION );
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Reg.CREATE_TABLE_SCRIPT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String x = Reg.TABLE_NAME;
        db.execSQL("DROP TABLE IF EXISTS " + x);
        onCreate(db);
    }
}

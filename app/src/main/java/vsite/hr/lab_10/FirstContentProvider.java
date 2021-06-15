package vsite.hr.lab_10;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import vsite.hr.lab_10.RegContract.Reg;

public class FirstContentProvider extends ContentProvider {
    public  static final String AUTHORITY = "vsite.hr.lab_9";
    public  static final String PATH_REG= "reg";
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static final int REG_CODE = 11;
    static {
        uriMatcher.addURI(AUTHORITY, PATH_REG, REG_CODE);
    }
    public FirstContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        RegHelper helper = new RegHelper(getContext());
        try {
            SQLiteDatabase baza = helper.getWritableDatabase();

            long id = baza.insert(Reg.TABLE_NAME, null, values);

            return ContentUris.withAppendedId(uri, id);

        } catch (SQLException ex){
            ex.printStackTrace();
        }
        return  Uri.EMPTY;
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        switch (uriMatcher.match(uri)) {
            case REG_CODE:
                RegHelper helper = new RegHelper(getContext());

                try {
                    SQLiteDatabase baza = helper.getReadableDatabase();
                    Cursor kursor = baza.query(
                            Reg.TABLE_NAME,
                            projection,
                            null,
                            null,
                            null,
                            null,
                            null);
                    return kursor;
                } catch (SQLException ex){
                    ex.printStackTrace();
                }
                break;
            case UriMatcher.NO_MATCH:
                throw new IllegalArgumentException("Krivi URI za Content provider!");
        }
        return null;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}

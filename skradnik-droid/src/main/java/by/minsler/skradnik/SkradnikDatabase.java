package by.minsler.skradnik;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by minsler on 8/17/14.
 */

public class SkradnikDatabase extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "skradnik.db";
    private static final int DATABASE_VERSION = 1;

    public SkradnikDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}
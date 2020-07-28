package id.co.myproject.talasstore.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "db_pesan";
    public static final int DATABASE_VERSION = 1;
    public static final String SQL_CREATE_TABLE_PESAN = String.format("CREATE TABLE %s"+
            "(%s TEXT NOT NULL, "+
            "%s TEXT NOT NULL, "+
            "%s TEXT NOT NULL, "+
            "%s TEXT NOT NULL, "+
            "%s TEXT NOT NULL, "+
            "%s TEXT NOT NULL)",
            DatabaseContract.TABLE_PESAN,
            DatabaseContract.PesananColumns.ID_USER,
            DatabaseContract.PesananColumns.ID_MAKANAN,
            DatabaseContract.PesananColumns.NAMA_MAKANAN,
            DatabaseContract.PesananColumns.HARGA_MAKANAN,
            DatabaseContract.PesananColumns.GAMBAR_MAKANAN,
            DatabaseContract.PesananColumns.QUANTITY
    );
    public DatabaseHelper(@Nullable Context context) {
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_PESAN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+DatabaseContract.TABLE_PESAN);
        onCreate(sqLiteDatabase);
    }
}

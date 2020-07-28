package id.co.myproject.talasstore.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import id.co.myproject.talasstore.model.Makanan;

import static id.co.myproject.talasstore.database.DatabaseContract.PesananColumns.GAMBAR_MAKANAN;
import static id.co.myproject.talasstore.database.DatabaseContract.PesananColumns.HARGA_MAKANAN;
import static id.co.myproject.talasstore.database.DatabaseContract.PesananColumns.ID_MAKANAN;
import static id.co.myproject.talasstore.database.DatabaseContract.PesananColumns.ID_USER;
import static id.co.myproject.talasstore.database.DatabaseContract.PesananColumns.NAMA_MAKANAN;
import static id.co.myproject.talasstore.database.DatabaseContract.PesananColumns.QUANTITY;
import static id.co.myproject.talasstore.database.DatabaseContract.TABLE_PESAN;

public class OrderHelper {
    public static DatabaseHelper databaseHelper;
    private static OrderHelper INSTANCE;

    private static SQLiteDatabase database;
    Context context;

    public OrderHelper(Context context) {
        this.context = context;
        databaseHelper = new DatabaseHelper(context);
    }

    public static OrderHelper getINSTANCE(Context context){
        if (INSTANCE == null){
            synchronized (SQLiteOpenHelper.class){
                if (INSTANCE == null){
                    INSTANCE = new OrderHelper(context);
                }
            }
        }

        return INSTANCE;
    }

    public void open(){
        database = databaseHelper.getWritableDatabase();
    }

    public void close(){
        databaseHelper.close();
        if (database.isOpen()){
            database.close();
        }
    }

    public long addToCart(String idUser, Makanan makanan, String qty){
        ContentValues args = new ContentValues();
        args.put(ID_USER, idUser);
        args.put(ID_MAKANAN, makanan.getKodeMakanan());
        args.put(NAMA_MAKANAN, makanan.getNamaMakanan());
        args.put(HARGA_MAKANAN, makanan.getHargaSatuan());
        args.put(GAMBAR_MAKANAN, makanan.getGambar());
        args.put(QUANTITY, qty);
        return database.insert(TABLE_PESAN, null, args);
    }


    public int getCountCart(String idUser){
        int count = 0;
        String query = String.format("SELECT COUNT(*) FROM %s WHERE %s = '%s'", TABLE_PESAN, ID_USER, idUser);
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do{
                count = cursor.getInt(0);
            }while (cursor.moveToNext());
        }else {
            return 0;
        }

        return count;
    }

    public List<Makanan> getAllCart(String idUser){
        List<Makanan> makananList = new ArrayList<>();
        Cursor cursor = database.query(TABLE_PESAN, null,
                ID_USER+"='"+idUser+"'",
                null,
                null,
                null,
                null,
                null);
        cursor.moveToFirst();
        Makanan makanan;
        if (cursor.moveToFirst()){
            do{
                makanan = new Makanan();
                makanan.setKodeMakanan(cursor.getString(cursor.getColumnIndex(ID_MAKANAN)));
                makanan.setNamaMakanan(cursor.getString(cursor.getColumnIndex(NAMA_MAKANAN)));
                makanan.setHargaSatuan(cursor.getString(cursor.getColumnIndex(HARGA_MAKANAN)));
                makanan.setGambar(cursor.getString(cursor.getColumnIndex(GAMBAR_MAKANAN)));
                makanan.setQuantity(cursor.getString(cursor.getColumnIndex(QUANTITY)));
                makananList.add(makanan);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return makananList;
    }

    public void updateCart(Makanan makanan, String idUser) {
        String query = String.format("UPDATE %s SET %s = '%s' WHERE %s = '%s' AND %s = '%s'", TABLE_PESAN, QUANTITY, makanan.getQuantity(), ID_USER, idUser, ID_MAKANAN, makanan.getKodeMakanan());
        database.execSQL(query);
    }

    public void removeToCart(String idMakanan, String idUser) {
        String query = String.format("DELETE FROM %s WHERE %s = '%s' and %s = '%s'", TABLE_PESAN, ID_MAKANAN, idMakanan, ID_USER, idUser);
        database.execSQL(query);
    }

    public long cleanCart(String idUser){
        return database.delete(TABLE_PESAN, ID_USER+" = '"+idUser+"'", null);
    }

}

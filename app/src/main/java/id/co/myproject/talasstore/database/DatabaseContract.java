package id.co.myproject.talasstore.database;

import android.provider.BaseColumns;

public class DatabaseContract {
    static String TABLE_PESAN = "tb_pesan";
    static final class PesananColumns implements BaseColumns {
        static String ID_USER = "id_user";
        static String ID_MAKANAN = "id_makanan";
        static String NAMA_MAKANAN = "nama_makanan";
        static String HARGA_MAKANAN = "harga_makanan";
        static String GAMBAR_MAKANAN = "gambar_makanan";
        static String QUANTITY = "qty";
    }
}

package id.co.myproject.talasstore.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Kategori {
    @SerializedName("kode_jenis_makanan")
    @Expose
    private String kodeJenisMakanan;

    @SerializedName("nama_jenis_makanan")
    @Expose
    private String namaJenisMakanan;

    @SerializedName("gambar_jenis_makanan")
    @Expose
    private String gambarJenisMakanan;

    public Kategori(String kodeJenisMakanan, String namaJenisMakanan, String gambarJenisMakanan) {
        this.kodeJenisMakanan = kodeJenisMakanan;
        this.namaJenisMakanan = namaJenisMakanan;
        this.gambarJenisMakanan = gambarJenisMakanan;
    }

    public String getKodeJenisMakanan() {
        return kodeJenisMakanan;
    }

    public String getNamaJenisMakanan() {
        return namaJenisMakanan;
    }

    public String getGambarJenisMakanan() {
        return gambarJenisMakanan;
    }
}

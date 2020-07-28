package id.co.myproject.talasstore.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Value {
    @SerializedName("value")
    @Expose
    private int value;

    @SerializedName("kode_member")
    @Expose
    private String kodeMember;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("id_transaksi")
    @Expose
    private int idTransaksi;

    @SerializedName("jumlah_pesanan")
    @Expose
    private int jumlahPesanan;

    public Value(int value, String kodeMember, String message, int idTransaksi, int jumlahPesanan) {
        this.value = value;
        this.kodeMember = kodeMember;
        this.message = message;
        this.idTransaksi = idTransaksi;
        this.jumlahPesanan = jumlahPesanan;
    }

    public int getValue() {
        return value;
    }

    public String getKodeMember() {
        return kodeMember;
    }

    public String getMessage() {
        return message;
    }

    public int getIdTransaksi() {
        return idTransaksi;
    }

    public int getJumlahPesanan() {
        return jumlahPesanan;
    }
}

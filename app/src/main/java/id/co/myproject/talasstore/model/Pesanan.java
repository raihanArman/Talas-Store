package id.co.myproject.talasstore.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Pesanan {
    @SerializedName("id_transaksi")
    @Expose
    private int idTransaksi;

    @SerializedName("kode_member")
    @Expose
    private String kodeMember;

    @SerializedName("total_harga")
    @Expose
    private String totalHarga;

    @SerializedName("uang_bayar")
    @Expose
    private String uangBayar;

    @SerializedName("uang_kembali")
    @Expose
    private String uangKembali;

    @SerializedName("tanggal")
    @Expose
    private Date tanggal;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("jumlah_pesanan")
    @Expose
    private String jumlah_pesanan;

    public Pesanan(int idTransaksi, String kodeMember, String totalHarga, String uangBayar, String uangKembali, Date tanggal, String status, String jumlah_pesanan) {
        this.idTransaksi = idTransaksi;
        this.kodeMember = kodeMember;
        this.totalHarga = totalHarga;
        this.uangBayar = uangBayar;
        this.uangKembali = uangKembali;
        this.tanggal = tanggal;
        this.status = status;
        this.jumlah_pesanan = jumlah_pesanan;
    }

    public int getIdTransaksi() {
        return idTransaksi;
    }

    public String getKodeMember() {
        return kodeMember;
    }

    public String getTotalHarga() {
        return totalHarga;
    }

    public String getUangBayar() {
        return uangBayar;
    }

    public String getUangKembali() {
        return uangKembali;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public String getStatus() {
        return status;
    }

    public String getJumlah_pesanan() {
        return jumlah_pesanan;
    }
}

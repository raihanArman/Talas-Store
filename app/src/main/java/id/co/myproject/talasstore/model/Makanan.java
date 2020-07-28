package id.co.myproject.talasstore.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Makanan implements Parcelable {
    @SerializedName("kode_makanan")
    @Expose
    private String kodeMakanan;

    @SerializedName("nama_makanan")
    @Expose
    private String namaMakanan;

    @SerializedName("deskripsi")
    @Expose
    private String deksripsi;

    @SerializedName("kode_jenis_makanan")
    @Expose
    private String kodeJenisMakanan;

    @SerializedName("stok_makanan")
    @Expose
    private String stokMakanan;

    @SerializedName("harga_satuan")
    @Expose
    private String hargaSatuan;

    @SerializedName("bahan_makanan")
    @Expose
    private String bahanMakanan;

    @SerializedName("langkah_makanan")
    @Expose
    private String langkahMakanan;

    @SerializedName("gambar")
    @Expose
    private String gambar;

    private String quantity;


    public Makanan() {
    }

    public Makanan(String kodeMakanan, String namaMakanan, String deksripsi, String kodeJenisMakanan, String stokMakanan, String hargaSatuan, String bahanMakanan, String langkahMakanan, String gambar, String quantity) {
        this.kodeMakanan = kodeMakanan;
        this.namaMakanan = namaMakanan;
        this.deksripsi = deksripsi;
        this.kodeJenisMakanan = kodeJenisMakanan;
        this.stokMakanan = stokMakanan;
        this.hargaSatuan = hargaSatuan;
        this.bahanMakanan = bahanMakanan;
        this.langkahMakanan = langkahMakanan;
        this.gambar = gambar;
        this.quantity = quantity;
    }

    public String getKodeMakanan() {
        return kodeMakanan;
    }

    public void setKodeMakanan(String kodeMakanan) {
        this.kodeMakanan = kodeMakanan;
    }

    public String getNamaMakanan() {
        return namaMakanan;
    }

    public void setNamaMakanan(String namaMakanan) {
        this.namaMakanan = namaMakanan;
    }

    public String getDeksripsi() {
        return deksripsi;
    }

    public void setDeksripsi(String deksripsi) {
        this.deksripsi = deksripsi;
    }

    public String getKodeJenisMakanan() {
        return kodeJenisMakanan;
    }

    public void setKodeJenisMakanan(String kodeJenisMakanan) {
        this.kodeJenisMakanan = kodeJenisMakanan;
    }

    public String getStokMakanan() {
        return stokMakanan;
    }

    public void setStokMakanan(String stokMakanan) {
        this.stokMakanan = stokMakanan;
    }

    public String getHargaSatuan() {
        return hargaSatuan;
    }

    public void setHargaSatuan(String hargaSatuan) {
        this.hargaSatuan = hargaSatuan;
    }

    public String getBahanMakanan() {
        return bahanMakanan;
    }

    public void setBahanMakanan(String bahanMakanan) {
        this.bahanMakanan = bahanMakanan;
    }

    public String getLangkahMakanan() {
        return langkahMakanan;
    }

    public void setLangkahMakanan(String langkahMakanan) {
        this.langkahMakanan = langkahMakanan;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.kodeMakanan);
        dest.writeString(this.namaMakanan);
        dest.writeString(this.deksripsi);
        dest.writeString(this.kodeJenisMakanan);
        dest.writeString(this.stokMakanan);
        dest.writeString(this.hargaSatuan);
        dest.writeString(this.bahanMakanan);
        dest.writeString(this.langkahMakanan);
        dest.writeString(this.gambar);
        dest.writeString(this.quantity);
    }

    protected Makanan(Parcel in) {
        this.kodeMakanan = in.readString();
        this.namaMakanan = in.readString();
        this.deksripsi = in.readString();
        this.kodeJenisMakanan = in.readString();
        this.stokMakanan = in.readString();
        this.hargaSatuan = in.readString();
        this.bahanMakanan = in.readString();
        this.langkahMakanan = in.readString();
        this.gambar = in.readString();
        this.quantity = in.readString();
    }

    public static final Parcelable.Creator<Makanan> CREATOR = new Parcelable.Creator<Makanan>() {
        @Override
        public Makanan createFromParcel(Parcel source) {
            return new Makanan(source);
        }

        @Override
        public Makanan[] newArray(int size) {
            return new Makanan[size];
        }
    };
}

package id.co.myproject.talasstore.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListPesanan {
    @SerializedName("id_pesanan")
    @Expose
    private String idPesanan;

    @SerializedName("id_transaksi")
    @Expose
    private String idTransaksi;

    @SerializedName("kode_barang")
    @Expose
    private String kodeBarang;

    @SerializedName("qty")
    @Expose
    private String qty;

    @SerializedName("sub_total")
    @Expose
    private String subTotal;

    @SerializedName("nama_makanan")
    @Expose
    private String namaMakanan;

    @SerializedName("satuan")
    @Expose
    private String satuan;

    public ListPesanan(String idPesanan, String idTransaksi, String kodeBarang, String qty, String subTotal, String namaMakanan, String satuan) {
        this.idPesanan = idPesanan;
        this.idTransaksi = idTransaksi;
        this.kodeBarang = kodeBarang;
        this.qty = qty;
        this.subTotal = subTotal;
        this.namaMakanan = namaMakanan;
        this.satuan = satuan;
    }

    public String getIdPesanan() {
        return idPesanan;
    }

    public String getIdTransaksi() {
        return idTransaksi;
    }

    public String getKodeBarang() {
        return kodeBarang;
    }

    public String getQty() {
        return qty;
    }

    public String getSubTotal() {
        return subTotal;
    }

    public String getNamaMakanan() {
        return namaMakanan;
    }

    public String getSatuan() {
        return satuan;
    }
}

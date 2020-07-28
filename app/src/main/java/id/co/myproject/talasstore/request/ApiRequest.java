package id.co.myproject.talasstore.request;

import java.util.List;

import id.co.myproject.talasstore.model.Kategori;
import id.co.myproject.talasstore.model.ListPesanan;
import id.co.myproject.talasstore.model.Makanan;
import id.co.myproject.talasstore.model.Pesanan;
import id.co.myproject.talasstore.model.User;
import id.co.myproject.talasstore.model.Value;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiRequest {

    @GET("tampil_makanan.php")
    Call<List<Makanan>> getMakananRequest();

    @GET("tampil_makanan.php")
    Call<List<Makanan>> getMakananByKategoriRequest(
            @Query("kode_jenis_makanan") String kodeJenisKategori
    );

    @GET("tampil_makanan.php")
    Call<Makanan> getMakananItemRequest(
            @Query("kode_makanan") String kodeMakanan
    );

    @GET("tampil_kategori.php")
    Call<List<Kategori>> getKategoriRequest();

    @GET("tampil_member.php")
    Call<User> getMember(
            @Query("kode_member") String kodeMember
    );

    @FormUrlEncoded
    @POST("input_transaksi.php")
    Call<Value> inputTransaksiRequest(
            @Field("kode_member") String kodeMember,
            @Field("total_harga") int totalHarga
    );

    @FormUrlEncoded
    @POST("input_pesanan.php")
    Call<Value> inputPesananRequest(
            @Field("id_transaksi") int idTransaksi,
            @Field("kode_makanan") String kodeMakanan,
            @Field("qty") String qty,
            @Field("sub_total") int subTotal
    );

    @GET("tampil_order.php")
    Call<List<Pesanan>> getPesananRequest(
            @Query("kode_member") String kodeMember
    );

    @GET("tampil_riwayat_pembayaran.php")
    Call<List<Pesanan>> getPembayaranRequest(
            @Query("kode_member") String kodeMember
    );

    @GET("tampil_jumlah_pesanan.php")
    Call<Value> getJumlahPesananRequest(
            @Query("kode_member") String kodeMember
    );

    @GET("tampil_pesanan.php")
    Call<List<ListPesanan>> getListPesananRequest(
            @Query("id_transaksi") int idTransaksi
    );


    @FormUrlEncoded
    @POST("edit_profil.php")
    Call<Value> ediProfilRequest(
            @Field("kode_member") String kodemember,
            @Field("username") String username,
            @Field("nama") String nama,
            @Field("alamat") String alamat,
            @Field("no_telp") String noTelp,
            @Field("gambar_member") String gambarmember
    );

    @FormUrlEncoded
    @POST("login_member.php")
    Call<Value> loginMemberRequest(
            @Field("username") String username,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("tambah_member.php")
    Call<Value> inputMemberRequest(
            @Field("kode_member") String kodeMember,
            @Field("username") String username,
            @Field("password") String password,
            @Field("nama") String namaMember,
            @Field("alamat") String alamatMember,
            @Field("no_telp") String noTelpMember,
            @Field("avatar") String avatar
    );

    @FormUrlEncoded
    @POST("cek_member.php")
    Call<Value> cekMemberRequest(
            @Field("username") String username
    );

    @FormUrlEncoded
    @POST("lupa_password_member.php")
    Call<Value> lupaPasswordMemberRequest(
            @Field("kode_member") String kodeMember,
            @Field("password") String password
    );

}

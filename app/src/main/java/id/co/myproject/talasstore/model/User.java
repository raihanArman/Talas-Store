package id.co.myproject.talasstore.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("kode_member")
    @Expose
    private String kodeMember;

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("nama_member")
    @Expose
    private String namaMember;

    @SerializedName("alamat_member")
    @Expose
    private String alamatMember;

    @SerializedName("no_telp_member")
    @Expose
    private String noTelpMember;

    @SerializedName("gambar_member")
    @Expose
    private String gambarMember;

    public User(String kodeMember, String username, String namaMember, String alamatMember, String noTelpMember, String gambarMember) {
        this.kodeMember = kodeMember;
        this.username = username;
        this.namaMember = namaMember;
        this.alamatMember = alamatMember;
        this.noTelpMember = noTelpMember;
        this.gambarMember = gambarMember;
    }

    public String getKodeMember() {
        return kodeMember;
    }

    public String getUsername() {
        return username;
    }

    public String getNamaMember() {
        return namaMember;
    }

    public String getAlamatMember() {
        return alamatMember;
    }

    public String getNoTelpMember() {
        return noTelpMember;
    }

    public String getGambarMember() {
        return gambarMember;
    }
}

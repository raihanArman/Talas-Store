package id.co.myproject.talasstore.view;

import androidx.appcompat.app.AppCompatActivity;
import id.co.myproject.talasstore.BuildConfig;
import id.co.myproject.talasstore.MainActivity;
import id.co.myproject.talasstore.R;
import id.co.myproject.talasstore.model.Makanan;
import id.co.myproject.talasstore.request.ApiRequest;
import id.co.myproject.talasstore.request.RetrofitRequest;
import id.co.myproject.talasstore.util.Helper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class DetailMakananActivity extends AppCompatActivity {

    String kodeMakanan;
    TextView tvNamaMakanan, tvHargaMakanan, tvDeskripsiMakanan, tvBahanMakanan, tvLangkahMakanan, tv_stok, tv_jenis_makanan;
    ImageView ivMakanan, ivBack;
    ApiRequest apiRequest;
    LinearLayout lv_pesan;
    String kodemember;
    boolean loginStatus;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_makanan);
        sharedPreferences = getSharedPreferences(Helper.KEY_LOGIN_SHARED_PREF, Context.MODE_PRIVATE);
        apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);

        kodemember = sharedPreferences.getString(Helper.KEY_KODE_MEMBER, "");
        loginStatus = sharedPreferences.getBoolean(Helper.KEY_LOGIN_STATUS, false);

        kodeMakanan = getIntent().getStringExtra("kode_makanan");
        Makanan makanan = getIntent().getParcelableExtra("data_makanan");

        tvNamaMakanan = findViewById(R.id.tv_nama_makanan);
        tvHargaMakanan = findViewById(R.id.tv_harga_makanan);
        tv_jenis_makanan = findViewById(R.id.tv_jenis_makanan);
        tvBahanMakanan = findViewById(R.id.tv_bahan);
        tvLangkahMakanan = findViewById(R.id.tv_langkah);
        lv_pesan = findViewById(R.id.lv_pesan);
        ivBack = findViewById(R.id.iv_back);
        tvDeskripsiMakanan = findViewById(R.id.tv_deskripsi_makanan);
        tv_jenis_makanan = findViewById(R.id.tv_jenis_makanan);
        tv_stok = findViewById(R.id.tv_stok);
        ivMakanan = findViewById(R.id.iv_makanan);

        if (loginStatus){
            lv_pesan.setVisibility(View.VISIBLE);
        }else {
            lv_pesan.setVisibility(View.GONE);
        }

        lv_pesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialogFragment bottomSheetDialogFragment = new PesananBottomSheetDialogFragment(makanan, kodemember);
                bottomSheetDialogFragment.show(((DetailMakananActivity)view.getContext()).getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailMakananActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        loadDataDetailMakanan();

    }

    private void loadDataDetailMakanan() {
        Call<Makanan> getDetailMakanan = apiRequest.getMakananItemRequest(kodeMakanan);
        getDetailMakanan.enqueue(new Callback<Makanan>() {
            @Override
            public void onResponse(Call<Makanan> call, Response<Makanan> response) {
                if (response.isSuccessful()){
                    Makanan makanan = response.body();
                    Glide.with(DetailMakananActivity.this).load(BuildConfig.BASE_URL_GAMBAR+"makanan/"+makanan.getGambar()).into(ivMakanan);
                    tvNamaMakanan.setText(makanan.getNamaMakanan());
                    tvDeskripsiMakanan.setText(makanan.getDeksripsi());
                    tv_stok.setText(makanan.getStokMakanan());
                    tvHargaMakanan.setText(makanan.getHargaSatuan());
                    tvBahanMakanan.setText(makanan.getBahanMakanan());
                    tvLangkahMakanan.setText(makanan.getLangkahMakanan());
                }
            }

            @Override
            public void onFailure(Call<Makanan> call, Throwable t) {
                Toast.makeText(DetailMakananActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}

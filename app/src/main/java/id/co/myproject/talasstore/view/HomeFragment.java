package id.co.myproject.talasstore.view;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.andremion.counterfab.CounterFab;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import id.co.myproject.talasstore.BuildConfig;
import id.co.myproject.talasstore.MainActivity;
import id.co.myproject.talasstore.R;
import id.co.myproject.talasstore.adapter.DataMakananAdapter;
import id.co.myproject.talasstore.adapter.KategoriAdapter;
import id.co.myproject.talasstore.database.OrderHelper;
import id.co.myproject.talasstore.model.Kategori;
import id.co.myproject.talasstore.model.Makanan;
import id.co.myproject.talasstore.model.User;
import id.co.myproject.talasstore.request.ApiRequest;
import id.co.myproject.talasstore.request.RetrofitRequest;
import id.co.myproject.talasstore.util.Helper;
import id.co.myproject.talasstore.view.login.LoginActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    RecyclerView rv_data_makanan, rv_kategori;
    ImageView ivMember;
    Button btnLogin;
    TextView tvNamaMember, tvJenisMember;
    ApiRequest apiRequest;
    DataMakananAdapter dataMakananAdapter;
    KategoriAdapter kategoriAdapter;
    public static CounterFab fb_pesan;
    OrderHelper orderHelper;
    String kodemember;
    boolean loginStatus;
    SharedPreferences sharedPreferences;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPreferences = getActivity().getSharedPreferences(Helper.KEY_LOGIN_SHARED_PREF, Context.MODE_PRIVATE);
        rv_data_makanan = view.findViewById(R.id.rv_data_makanan);
        rv_kategori = view.findViewById(R.id.rv_kategori);
        tvNamaMember = view.findViewById(R.id.tv_nama_member);
        tvJenisMember = view.findViewById(R.id.tv_jenis_user);
        ivMember = view.findViewById(R.id.iv_member);
        fb_pesan = view.findViewById(R.id.fb_pesan);
        btnLogin = view.findViewById(R.id.btn_login);

        kodemember = sharedPreferences.getString(Helper.KEY_KODE_MEMBER, "");
        loginStatus = sharedPreferences.getBoolean(Helper.KEY_LOGIN_STATUS, false);

        orderHelper = OrderHelper.getINSTANCE(getActivity());
        orderHelper.open();
        apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);

        rv_data_makanan.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        rv_kategori.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        dataMakananAdapter = new DataMakananAdapter(getActivity());
        kategoriAdapter = new KategoriAdapter(getActivity());
        rv_data_makanan.setAdapter(dataMakananAdapter);
        rv_kategori.setAdapter(kategoriAdapter);

        if (loginStatus){
            fb_pesan.setVisibility(View.VISIBLE);
            ivMember.setVisibility(View.VISIBLE);
            tvNamaMember.setVisibility(View.VISIBLE);
            tvJenisMember.setVisibility(View.VISIBLE);
            btnLogin.setVisibility(View.GONE);
        }else {
            fb_pesan.setVisibility(View.GONE);
            ivMember.setVisibility(View.GONE);
            tvNamaMember.setVisibility(View.GONE);
            tvJenisMember.setVisibility(View.GONE);
            btnLogin.setVisibility(View.VISIBLE);
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        loadDataMakanan();
        loadDataMember();
        loadDataKategori();

        fb_pesan.setColorFilter(Color.WHITE);
        fb_pesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)view.getContext()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_home, new PesananFragment()).addToBackStack(null)
                        .commit();
            }
        });

    }

    private void loadDataMember() {
        Call<User> userCall = apiRequest.getMember(kodemember);
        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                    User user = response.body();
                    tvNamaMember.setText(user.getNamaMember());
                    Glide.with(getActivity()).load(BuildConfig.BASE_URL_GAMBAR+"member/"+user.getGambarMember()).into(ivMember);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadDataKategori() {
        Call<List<Kategori>> getKategori = apiRequest.getKategoriRequest();
        getKategori.enqueue(new Callback<List<Kategori>>() {
            @Override
            public void onResponse(Call<List<Kategori>> call, Response<List<Kategori>> response) {
                if (response.isSuccessful()){
                    List<Kategori> kategoriList = response.body();
                    kategoriAdapter.setKategoriList(kategoriList);
                }
            }

            @Override
            public void onFailure(Call<List<Kategori>> call, Throwable t) {
                Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadDataMakanan() {
        Call<List<Makanan>> getDataMakanan = apiRequest.getMakananRequest();
        getDataMakanan.enqueue(new Callback<List<Makanan>>() {
            @Override
            public void onResponse(Call<List<Makanan>> call, Response<List<Makanan>> response) {
                if (response.isSuccessful()){
                    List<Makanan> makananList = response.body();
                    dataMakananAdapter.setMakananList(makananList);
                }
            }

            @Override
            public void onFailure(Call<List<Makanan>> call, Throwable t) {
                Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        fb_pesan.setCount(orderHelper.getCountCart(kodemember));
        loadDataMakanan();
    }

    @Override
    public void onStop() {
        super.onStop();
        orderHelper.close();
    }
}

package id.co.myproject.talasstore.view.home;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import id.co.myproject.talasstore.R;
import id.co.myproject.talasstore.adapter.DataMakananAdapter;
import id.co.myproject.talasstore.model.Makanan;
import id.co.myproject.talasstore.request.ApiRequest;
import id.co.myproject.talasstore.request.RetrofitRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class KategoriMakananFragment extends Fragment {
    RecyclerView rv_data_makanan, rv_kategori;
    ImageView ivBack;
    String kodeJenisKategori;
    TextView tvNamaKategori;
    RelativeLayout lv_empty;
    ApiRequest apiRequest;
    public static DataMakananAdapter dataMakananAdapter;
    public FloatingActionButton fb_tambah;


    public KategoriMakananFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_kategori_makanan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rv_data_makanan = view.findViewById(R.id.rv_kategori_makanan);
        tvNamaKategori = view.findViewById(R.id.tv_nama_kategori);
        ivBack = view.findViewById(R.id.iv_back);
        lv_empty = view.findViewById(R.id.lv_empty);

        kodeJenisKategori = getArguments().getString("id_kategori");
        apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);

        rv_data_makanan.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        dataMakananAdapter = new DataMakananAdapter(getActivity());
        rv_data_makanan.setAdapter(dataMakananAdapter);

        tvNamaKategori.setText(getArguments().getString("nama_kategori"));

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStackImmediate();
            }
        });

        loadDataMakanan();

    }

    private void loadDataMakanan() {
        Call<List<Makanan>> getMakanan = apiRequest.getMakananByKategoriRequest(kodeJenisKategori);
        getMakanan.enqueue(new Callback<List<Makanan>>() {
            @Override
            public void onResponse(Call<List<Makanan>> call, Response<List<Makanan>> response) {
                if (response.isSuccessful()){
                    List<Makanan> makananList = response.body();
                    if (makananList.size() <= 0){
                        lv_empty.setVisibility(View.VISIBLE);
                    }else {
                        lv_empty.setVisibility(View.GONE);
                    }
                    dataMakananAdapter.setMakananList(makananList);
                }
            }

            @Override
            public void onFailure(Call<List<Makanan>> call, Throwable t) {
                Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

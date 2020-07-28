package id.co.myproject.talasstore.view;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import id.co.myproject.talasstore.R;
import id.co.myproject.talasstore.adapter.OrderAdapter;
import id.co.myproject.talasstore.model.Pesanan;
import id.co.myproject.talasstore.request.ApiRequest;
import id.co.myproject.talasstore.request.RetrofitRequest;
import id.co.myproject.talasstore.util.Helper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment {
    ApiRequest apiRequest;
    RecyclerView rv_order_list;
    OrderAdapter orderAdapter;
    String kodemember;
    boolean loginStatus;
    SharedPreferences sharedPreferences;

    public OrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPreferences = getActivity().getSharedPreferences(Helper.KEY_LOGIN_SHARED_PREF, Context.MODE_PRIVATE);
        apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
        rv_order_list = view.findViewById(R.id.rv_order_list);
        kodemember = sharedPreferences.getString(Helper.KEY_KODE_MEMBER, "");
        loginStatus = sharedPreferences.getBoolean(Helper.KEY_LOGIN_STATUS, false);
        orderAdapter = new OrderAdapter(getActivity(), apiRequest);
        rv_order_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_order_list.setAdapter(orderAdapter);

        loadDataTransaksi();
    }


    private void loadDataTransaksi() {
        Call<List<Pesanan>> requestCall = apiRequest.getPesananRequest(kodemember);
        requestCall.enqueue(new Callback<List<Pesanan>>() {
            @Override
            public void onResponse(Call<List<Pesanan>> call, Response<List<Pesanan>> response) {
                if (response.isSuccessful()){
                    List<Pesanan> requestList = response.body();
                    orderAdapter.setRequestList(requestList);
                }
            }

            @Override
            public void onFailure(Call<List<Pesanan>> call, Throwable t) {
                Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

package id.co.myproject.talasstore.view;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import id.co.myproject.talasstore.R;
import id.co.myproject.talasstore.adapter.CartAdapter;
import id.co.myproject.talasstore.database.OrderHelper;
import id.co.myproject.talasstore.model.Makanan;
import id.co.myproject.talasstore.model.Value;
import id.co.myproject.talasstore.request.ApiRequest;
import id.co.myproject.talasstore.request.RetrofitRequest;
import id.co.myproject.talasstore.util.Helper;
import id.co.myproject.talasstore.util.RecyclerItemTouchHelper;
import id.co.myproject.talasstore.util.RecyclerItemTouchHelperListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static id.co.myproject.talasstore.util.Helper.rupiahFormat;

/**
 * A simple {@link Fragment} subclass.
 */
public class PesananFragment extends Fragment implements RecyclerItemTouchHelperListener {

    RecyclerView rv_cart;
    RelativeLayout rl_root;
    CartAdapter cartAdapter;
    OrderHelper orderHelper;
    LinearLayout lv_empty;
    CardView cv_cart;
    ImageView iv_back;
    public static TextView tv_total;
    Button btn_pesan;
    int id_kasir, id_cafe, totalBayar, uangKembalian;
    String idUser;
    ApiRequest apiRequest;
    String kodemember;
    boolean loginStatus;
    SharedPreferences sharedPreferences;

    public PesananFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pesanan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        orderHelper = OrderHelper.getINSTANCE(getActivity());
        orderHelper.open();

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);

        sharedPreferences = getActivity().getSharedPreferences(Helper.KEY_LOGIN_SHARED_PREF, Context.MODE_PRIVATE);
        kodemember = sharedPreferences.getString(Helper.KEY_KODE_MEMBER, "");
        loginStatus = sharedPreferences.getBoolean(Helper.KEY_LOGIN_STATUS, false);

        rv_cart = view.findViewById(R.id.rv_cart);
        tv_total = view.findViewById(R.id.tv_total);
        rl_root = view.findViewById(R.id.rootLayout);
        btn_pesan = view.findViewById(R.id.btn_pesan);
        lv_empty = view.findViewById(R.id.lv_empty);
        cv_cart = view.findViewById(R.id.cd_cart);
        iv_back = view.findViewById(R.id.iv_back);

        cartAdapter = new CartAdapter(getActivity(), orderHelper, kodemember);

        rv_cart.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_cart.setAdapter(cartAdapter);

        ItemTouchHelper.SimpleCallback itemSimpleCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemSimpleCallback).attachToRecyclerView(rv_cart);

        loadDataCart();

        btn_pesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputTransaksi();
            }
        });


        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStackImmediate();
            }
        });


    }


    private void inputTransaksi() {
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Proses ...");
        progressDialog.show();
        Call<Value> inputTransaksi = apiRequest.inputTransaksiRequest(
                kodemember,
                totalBayar
        );
        inputTransaksi.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                if (response.isSuccessful()){
                    Toast.makeText(getActivity(), ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    if (response.body().getValue() == 1){
                        int id_transaksi = response.body().getIdTransaksi();
                        List<Makanan> makananList = orderHelper.getAllCart(kodemember);
                        for (Makanan makanan : makananList){
                            int subTotal = Integer.parseInt(makanan.getHargaSatuan())* Integer.parseInt(makanan.getQuantity());
                            Call<Value> inputPesanan = apiRequest.inputPesananRequest(
                                    id_transaksi,
                                    makanan.getKodeMakanan(),
                                    makanan.getQuantity(),
                                    subTotal
                            );
                            inputPesanan.enqueue(new Callback<Value>() {
                                @Override
                                public void onResponse(Call<Value> call, Response<Value> response) {
                                    if (response.isSuccessful()){
                                        if (response.body().getValue() == 1){
                                            long delete = orderHelper.cleanCart(kodemember);
                                            if (delete > 0){
                                                progressDialog.dismiss();
                                                HomeFragment.fb_pesan.setCount(orderHelper.getCountCart(kodemember));
                                                getActivity().getSupportFragmentManager().popBackStackImmediate();
                                            }
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<Value> call, Throwable t) {
                                    progressDialog.dismiss();
                                    Toast.makeText(getActivity(), "Pesanan : "+t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                    }else {
                        progressDialog.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                Toast.makeText(getActivity(), "Transaksi : "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



    }

    private void loadDataCart() {
        List<Makanan> menuList = orderHelper.getAllCart(kodemember);
        cartAdapter.setMenuList(menuList);
        if (menuList.size() <= 0){
            lv_empty.setVisibility(View.VISIBLE);
            cv_cart.setVisibility(View.INVISIBLE);
            rv_cart.setVisibility(View.INVISIBLE);
        }else{
            lv_empty.setVisibility(View.GONE);
            cv_cart.setVisibility(View.VISIBLE);
            rv_cart.setVisibility(View.VISIBLE);
        }

        int total = 0;
        for (Makanan menu : menuList){
            total+=(Integer.parseInt(menu.getHargaSatuan()))*(Integer.parseInt(menu.getQuantity()));
        }

        totalBayar = total;

        tv_total.setText(rupiahFormat(total));

    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof CartAdapter.ViewHolder){
            String name = ((CartAdapter)rv_cart.getAdapter()).getItem(viewHolder.getAdapterPosition()).getKodeMakanan();
            final Makanan deleteItem = ((CartAdapter)rv_cart.getAdapter()).getItem(viewHolder.getAdapterPosition());
            final int deleteIndex = viewHolder.getAdapterPosition();
            cartAdapter.removeItem(deleteIndex);
            orderHelper.removeToCart(deleteItem.getKodeMakanan(), kodemember);

            int total = 0;
            List<Makanan> menuList = orderHelper.getAllCart(kodemember);
            for (Makanan item : menuList){
                total+=(Integer.parseInt(item.getHargaSatuan()))*(Integer.parseInt(item.getQuantity()));
            }

            tv_total.setText(rupiahFormat(total));

            Snackbar snackbar = Snackbar.make(rl_root, name+" menghapus dari keranjang", Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cartAdapter.restoreItem(deleteItem, deleteIndex);
                    orderHelper.addToCart(kodemember, deleteItem, deleteItem.getQuantity());

                    int total = 0;
                    List<Makanan> menuList = orderHelper.getAllCart(kodemember);
                    for (Makanan item : menuList){
                        total+=(Integer.parseInt(item.getHargaSatuan()))*(Integer.parseInt(item.getQuantity()));
                    }

                    tv_total.setText(rupiahFormat(total));

                }
            });

            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();

        }
    }
}

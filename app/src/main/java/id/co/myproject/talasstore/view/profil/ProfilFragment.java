package id.co.myproject.talasstore.view.profil;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.bumptech.glide.Glide;

import java.util.List;

import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import id.co.myproject.talasstore.BuildConfig;
import id.co.myproject.talasstore.MainActivity;
import id.co.myproject.talasstore.R;
import id.co.myproject.talasstore.adapter.RiwayatPembayaranAdapter;
import id.co.myproject.talasstore.model.Pesanan;
import id.co.myproject.talasstore.model.User;
import id.co.myproject.talasstore.request.ApiRequest;
import id.co.myproject.talasstore.request.RetrofitRequest;
import id.co.myproject.talasstore.util.Helper;
import id.co.myproject.talasstore.view.HomeFragment;
import id.co.myproject.talasstore.view.login.LoginActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static id.co.myproject.talasstore.util.Helper.KEY_KODE_MEMBER;
import static id.co.myproject.talasstore.util.Helper.KEY_LOGIN_STATUS;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfilFragment extends Fragment {

    ImageView ivUser, ivSetting;
    TextView tvUser, tvEmail;
    Button btnLogOut;
    RecyclerView rv_pembayaran;
    RiwayatPembayaranAdapter riwayatPembayaranAdapter;
    SharedPreferences sharedPreferences;
    String kodemember;
    boolean loginStatus;
    SharedPreferences.Editor editor;
    int idUser;
    ApiRequest apiRequest;
    public static boolean statusUpdate = false;

    public ProfilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profil, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
        sharedPreferences = getActivity().getSharedPreferences(Helper.KEY_LOGIN_SHARED_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        kodemember = sharedPreferences.getString(Helper.KEY_KODE_MEMBER, "");
        loginStatus = sharedPreferences.getBoolean(Helper.KEY_LOGIN_STATUS, false);


        idUser = sharedPreferences.getInt("id_user", 0);
        ivUser = view.findViewById(R.id.iv_user);
        ivSetting = view.findViewById(R.id.iv_setting);
        tvUser = view.findViewById(R.id.tv_user);
        tvEmail = view.findViewById(R.id.tv_email);
        btnLogOut = view.findViewById(R.id.btn_log_out);
        rv_pembayaran = view.findViewById(R.id.rv_pembayaran);

        loadDataUser();

        riwayatPembayaranAdapter = new RiwayatPembayaranAdapter(getActivity());
        rv_pembayaran.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_pembayaran.setAdapter(riwayatPembayaranAdapter);
        loadDataPembayaran();

        ivSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditProfilFragment editProfilFragment = new EditProfilFragment();
                Bundle bundle = new Bundle();
                bundle.putString("kode_member", kodemember);
                editProfilFragment.setArguments(bundle);
                ((MainActivity)view.getContext()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_home, editProfilFragment)
                        .addToBackStack("")
                        .commit();
            }
        });

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });
    }

    private void signOut() {
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Proses ...");
        progressDialog.show();
        editor.putBoolean(KEY_LOGIN_STATUS, false);
        editor.putString(KEY_KODE_MEMBER, "");
        editor.commit();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_home, new HomeFragment());
        fragmentTransaction.commit();
        MainActivity.bottomNavigationView.setVisibility(View.GONE);
        progressDialog.dismiss();
    }

    private void loadDataPembayaran() {
        Call<List<Pesanan>> getRiwayat = apiRequest.getPembayaranRequest(kodemember);
        getRiwayat.enqueue(new Callback<List<Pesanan>>() {
            @Override
            public void onResponse(Call<List<Pesanan>> call, Response<List<Pesanan>> response) {
                if (response.isSuccessful()){
                    List<Pesanan> pesanans = response.body();
                    riwayatPembayaranAdapter.setPesananList(pesanans);
                }
            }

            @Override
            public void onFailure(Call<List<Pesanan>> call, Throwable t) {
                Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadDataUser() {
        Call<User> userCall = apiRequest.getMember(kodemember);
        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                    User user = response.body();
                    tvUser.setText(user.getNamaMember());
                    tvEmail.setText(user.getUsername());
                    Glide.with(getActivity()).load(BuildConfig.BASE_URL_GAMBAR+"member/"+user.getGambarMember()).into(ivUser);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        if (statusUpdate) {
            loadDataUser();
        }
    }
}

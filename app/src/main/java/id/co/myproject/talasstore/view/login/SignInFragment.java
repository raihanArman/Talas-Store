package id.co.myproject.talasstore.view.login;


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
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentTransaction;
import id.co.myproject.talasstore.MainActivity;
import id.co.myproject.talasstore.R;
import id.co.myproject.talasstore.model.Value;
import id.co.myproject.talasstore.request.ApiRequest;
import id.co.myproject.talasstore.request.RetrofitRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static id.co.myproject.talasstore.util.Helper.KEY_KODE_MEMBER;
import static id.co.myproject.talasstore.util.Helper.KEY_LOGIN_SHARED_PREF;
import static id.co.myproject.talasstore.util.Helper.KEY_LOGIN_STATUS;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignInFragment extends Fragment {
    EditText etEmail, etPassword;
    Button btnSignIn;
    TextView tvRegistrasi, tvLupaPassword, tv_email;
    FrameLayout parentFrameLayout;
    int id_supplier, login_level;
    String nama,email,url, avatar, namaUser, emailUser, avatarUser;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";
    ApiRequest apiRequest;
    private boolean userExists = false;
    ProgressDialog progressDialog;


    public SignInFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);

        etEmail = view.findViewById(R.id.et_email);
        etPassword = view.findViewById(R.id.et_password);
        btnSignIn = view.findViewById(R.id.btn_sign_in);
        tvRegistrasi = view.findViewById(R.id.tv_registrasi);
        tvLupaPassword = view.findViewById(R.id.tv_lupa_password);
        parentFrameLayout = getActivity().findViewById(R.id.frame_login);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Memproses ...");
        sharedPreferences = getActivity().getSharedPreferences(KEY_LOGIN_SHARED_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginProcess();
            }
        });

        tvRegistrasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new SignUpFragment());
            }
        });

        tvLupaPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new ForgotPasswordFragment());
            }
        });
    }

    private void loginProcess() {
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        if (etPassword.length() > 8) {
            progressDialog.show();
            btnSignIn.setEnabled(true);
            prosesLogin(email, password);
        } else {
            Toast.makeText(getActivity(), "Password kurang boss", Toast.LENGTH_SHORT).show();
        }
    }

    private void prosesLogin(String email, String password) {
        Call<Value> loginMember = apiRequest.loginMemberRequest(email, password);
        loginMember.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                if (response.isSuccessful()){
                    Toast.makeText(getActivity(), ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    if (response.body().getValue() == 1){
                        String kodeMember = response.body().getKodeMember();
                        editor.putString(KEY_KODE_MEMBER, kodeMember);
                        editor.putBoolean(KEY_LOGIN_STATUS, true);
                        editor.commit();
                        updateUI(true);
                    }else {
                        progressDialog.dismiss();
                        btnSignIn.setEnabled(true);
                    }
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {

            }
        });
    }


    private void updateUI(final boolean isSignedIn) {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Proses ...");
        progressDialog.show();
        final boolean loginStatus = sharedPreferences.getBoolean(KEY_LOGIN_STATUS, false);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isSignedIn) {
                    if (loginStatus) {
                        String kodeMember = sharedPreferences.getString(KEY_KODE_MEMBER, "");
                        progressDialog.dismiss();
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        editor.putString(KEY_KODE_MEMBER, kodeMember);
                        editor.apply();
                        getActivity().startActivity(intent);
                        getActivity().finish();
                    }
                } else {
                    progressDialog.dismiss();
                }
            }
        });
    }

    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(parentFrameLayout.getId(), fragment);
        fragmentTransaction.commit();
    }
}

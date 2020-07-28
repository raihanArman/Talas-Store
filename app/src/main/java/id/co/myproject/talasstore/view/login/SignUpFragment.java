package id.co.myproject.talasstore.view.login;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.UUID;

import androidx.fragment.app.FragmentTransaction;
import id.co.myproject.talasstore.MainActivity;
import id.co.myproject.talasstore.R;
import id.co.myproject.talasstore.model.Value;
import id.co.myproject.talasstore.request.ApiRequest;
import id.co.myproject.talasstore.request.RetrofitRequest;
import id.co.myproject.talasstore.util.Helper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static id.co.myproject.talasstore.util.Helper.KEY_KODE_MEMBER;
import static id.co.myproject.talasstore.util.Helper.KEY_LOGIN_STATUS;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment {

    EditText etNama, etUsername, etPassword, etKonfirm, etAlamat, et_noTelp;
    Button btnSignUp;
    TextView tv_login;
    private FrameLayout parentFrameLayout;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";
    private ProgressDialog progressDialog;
    //    public static final String TAG = SignUpFragment.class.getSimpleName();
    //    ApiRequest apiRequest;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ApiRequest apiRequest;


    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etNama = view.findViewById(R.id.et_nama);
        etAlamat = view.findViewById(R.id.et_alamat);
        et_noTelp = view.findViewById(R.id.et_noTelp);
        etPassword = view.findViewById(R.id.et_password);
        etKonfirm = view.findViewById(R.id.et_confirm_password);
        etUsername = view.findViewById(R.id.et_username);
        btnSignUp = view.findViewById(R.id.btn_sign_up);
        tv_login = view.findViewById(R.id.tv_login);

        apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);

        parentFrameLayout = getActivity().findViewById(R.id.frame_login);

        sharedPreferences = getActivity().getSharedPreferences(Helper.KEY_LOGIN_SHARED_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Proses ...");


        etNama.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkInput();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkInput();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        etKonfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkInput();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                daftar();
            }
        });

        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new SignInFragment());
            }
        });
    }

    private void daftar() {
        final String username = etUsername.getText().toString();
        final String alamat = etAlamat.getText().toString();
        final String nama = etNama.getText().toString();
        final String password = etPassword.getText().toString();
        final String noTelp= et_noTelp.getText().toString();
        String kodeMember = UUID.randomUUID().toString();
        String kdMember = kodeMember.substring(0,8);

        if (etPassword.getText().toString().equals(etKonfirm.getText().toString())){
            progressDialog.show();
            Call<Value> inputMember = apiRequest.inputMemberRequest(
                    kdMember,
                    username,
                    password,
                    nama,
                    alamat,
                    noTelp, ""
            );
            inputMember.enqueue(new Callback<Value>() {
                @Override
                public void onResponse(Call<Value> call, Response<Value> response) {
                    String kodeMember = response.body().getKodeMember();
                    editor.putString(KEY_KODE_MEMBER, kodeMember);
                    editor.putBoolean(KEY_LOGIN_STATUS, true);
                    editor.commit();
                    updateUI(true);
                }

                @Override
                public void onFailure(Call<Value> call, Throwable t) {
                    Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
                }
            });
        }
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

    private void checkInput() {
        if (!TextUtils.isEmpty(etNama.getText())) {
            if (!TextUtils.isEmpty(etAlamat.getText())) {
                if (!TextUtils.isEmpty(etPassword.getText()) && etPassword.length() >= 8) {
                    if (!TextUtils.isEmpty(etKonfirm.getText())) {
                        btnSignUp.setEnabled(true);
                    } else {
                        btnSignUp.setEnabled(false);
                    }
                } else {
                    btnSignUp.setEnabled(false);
                }
            } else {
                btnSignUp.setEnabled(false);
            }
        } else {
            btnSignUp.setEnabled(false);
        }
    }



    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(parentFrameLayout.getId(), fragment);
        fragmentTransaction.commit();
    }
}

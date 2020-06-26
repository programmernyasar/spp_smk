package com.kulodev.smkppi.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.kulodev.smkppi.Database.SharedPrefManager;
import com.kulodev.smkppi.Model.DataPengumuman;
import com.kulodev.smkppi.Model.DataResponse;
import com.kulodev.smkppi.Model.DataSiswa;
import com.kulodev.smkppi.Model.DataTagihan;
import com.kulodev.smkppi.Model.DataUserWali;
import com.kulodev.smkppi.R;
import com.kulodev.smkppi.Rest.ApiClient;
import com.kulodev.smkppi.Rest.ApiInterface;
import com.scottyab.showhidepasswordedittext.ShowHidePasswordEditText;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kulodev.smkppi.Database.DataConstant.API_KEY;

public class LoginWaliActivity extends AppCompatActivity {

    private TextInputEditText edt_email_wali;
    private ShowHidePasswordEditText edt_password_wali;
    private Button btn_login;
    private TextView tv_forgot_password;
    private TextView tv_login_wali;
    private TextView tv_register;
    private List<DataUserWali> dataUserWali = new ArrayList<>();
    private List<DataSiswa> dataSiswa = new ArrayList<>();
    private List<DataTagihan> dataTagihan = new ArrayList<>();
    private List<DataPengumuman> dataPengumuman = new ArrayList<>();
    private ProgressDialog progressDialog;

    private String session;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login_wali);

        sharedPrefManager = new SharedPrefManager(this);
        session="wali";

        initComponent();
        onClick();


    }

    private void initComponent(){
        edt_email_wali=findViewById(R.id.edt_email_wali);
        edt_password_wali=findViewById(R.id.edt_password_wali);
        btn_login=findViewById(R.id.btn_login_wali);
        tv_login_wali=findViewById(R.id.tv_login_siswa);
        tv_register=findViewById(R.id.register_wali);
        tv_forgot_password=findViewById(R.id.forgot_password_wali);
    }

    private void onClick(){
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email= String.valueOf(edt_email_wali.getText());
                String password= String.valueOf(edt_password_wali.getText());
                if(isNullOrEmpty(email)&&isNullOrEmpty(password)){
                    Toast.makeText(getApplicationContext(), "Email Atau Password Kosong",Toast.LENGTH_LONG).show();
                }else{
                    progressDialog = new ProgressDialog(LoginWaliActivity.this);
                    progressDialog.setMessage("Loading..."); // Setting Message
                    progressDialog.setTitle("Mohon Tunggu"); // Setting Title
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                    progressDialog.setCancelable(false);
                    progressDialog.show(); // Display Progress Dialog

                    getLogin(email,password);
                }
            }
        });

        tv_login_wali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login=new Intent(LoginWaliActivity.this, LoginSiswaActivity.class);
                startActivity(login);
                finish();

            }
        });

        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogRegister();

            }
        });

        tv_forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogForgotPassword();

            }
        });
    }

    private void dialogForgotPassword() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_lupa_password);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;


        ((AppCompatButton) dialog.findViewById(R.id.bt_confirm)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edt_email=dialog.findViewById(R.id.edt_email_forgot);
                String email= String.valueOf(edt_email.getText());
                String password= String.valueOf(edt_password_wali.getText());

                if(isNullOrEmpty(email)){
                    Toast.makeText(getApplicationContext(), "Email Kosong",Toast.LENGTH_LONG).show();
                }else {
                    getForgot(email);
                }

                dialog.dismiss();
            }
        });

        ((AppCompatButton) dialog.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    private void dialogRegister() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_add_wali);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;


        ((AppCompatButton) dialog.findViewById(R.id.bt_confirm_add_wali)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edt_email=dialog.findViewById(R.id.edt_email_add_wali);
                ShowHidePasswordEditText edt_password=dialog.findViewById(R.id.edt_password_add_wali);
                String email= String.valueOf(edt_email.getText());
                String password= String.valueOf(edt_password.getText());

                if(isNullOrEmpty(email)&&isNullOrEmpty(password)){
                    Toast.makeText(getApplicationContext(), "Email Atau Password Kosong",Toast.LENGTH_LONG).show();
                }else{
                    getRegister(email,password);
                }

                dialog.dismiss();
            }
        });

        ((AppCompatButton) dialog.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    public void getLogin(String email, String password){

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<DataResponse> call = apiService.getLoginWali(API_KEY,email, password);
        call.enqueue(new Callback<DataResponse>() {
            @Override
            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                if(response.isSuccessful()){
                    dataUserWali = response.body().getLoginWali();
                    dataSiswa = response.body().getDataSiswa();
                    dataTagihan=response.body().getTagihanTerbaru();
                    dataPengumuman=response.body().getPengumumanTerbaru();
                    String status = response.body().getStatus();
                    String message= response.body().getMessage();
                    if(status.equals("success")){
                        Log.e("Siswa :","Sukses API Login");
                        //Save Session User
                        String nis=dataUserWali.get(0).getNisSiswa();
                        String nama=dataUserWali.get(0).getNamaWali();
                        String email=dataUserWali.get(0).getMailUserWali();
                        String kelas=dataUserWali.get(0).getKelas();
                        String tahunAjaran=dataUserWali.get(0).getTahunAjaran();
                        sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, true);
                        sharedPrefManager.saveSPString(SharedPrefManager.SP_NIS,nis);
                        sharedPrefManager.saveSPString(SharedPrefManager.SP_NAMA,nama);
                        sharedPrefManager.saveSPString(SharedPrefManager.SP_EMAIL,email);
                        sharedPrefManager.saveSPString(SharedPrefManager.SP_SESSION, session);
                        sharedPrefManager.saveSPString(SharedPrefManager.SP_PASSWORD, password);
                        sharedPrefManager.saveSPString(SharedPrefManager.SP_KELAS,kelas);
                        sharedPrefManager.saveSPString(SharedPrefManager.SP_TAHUN_AJARAN,tahunAjaran);

                        //Save Session Pengumuman
                        String id=dataPengumuman.get(0).getIdPengumuman();
                        String judul=dataPengumuman.get(0).getJudul();
                        String isi=dataPengumuman.get(0).getIsi();
                        String tujuan=dataPengumuman.get(0).getTujuan();
                        String bulan=dataPengumuman.get(0).getBulan();
                        String dateUpdate=dataPengumuman.get(0).getDateUpdate();

                        sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_ADA_PENGUMUMAN,true);
                        sharedPrefManager.saveSPString(SharedPrefManager.SP_ID_PENGUMUMAN,id);
                        sharedPrefManager.saveSPString(SharedPrefManager.SP_JUDUL_PENGUMUMAN, judul);
                        sharedPrefManager.saveSPString(SharedPrefManager.SP_ISI_PENGUMUMAN, isi);
                        sharedPrefManager.saveSPString(SharedPrefManager.SP_TUJUAN_PENGUMUMAN,tujuan);
                        sharedPrefManager.saveSPString(SharedPrefManager.SP_BULAN_PENGUMUMAN,bulan);
                        sharedPrefManager.saveSPString(SharedPrefManager.SP_DATE_PENGUMUMAN,dateUpdate);

                        //Save Tagihan Terbaru
                        String idTagihan=dataTagihan.get(0).getIdTagihan();
                        String bulanTagihan=dataTagihan.get(0).getBulan();
                        String catatanTagihan=dataTagihan.get(0).getCatatan();
                        String jumlahTagihan=dataTagihan.get(0).getJumlah();
                        String tahunAjaranTagihan=dataTagihan.get(0).getTahunAjaran();
                        String statusTagihan=dataTagihan.get(0).getStatus();
                        String dateUpdateTagihan=dataTagihan.get(0).getDateUpdate();

                        sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_ADA_TAGIHAN,true);
                        sharedPrefManager.saveSPString(SharedPrefManager.SP_ID_TAGIHAN,idTagihan);
                        sharedPrefManager.saveSPString(SharedPrefManager.SP_BULAN_TAGIHAN, bulanTagihan);
                        sharedPrefManager.saveSPString(SharedPrefManager.SP_CATATAN_TAGIHAN, catatanTagihan);
                        sharedPrefManager.saveSPString(SharedPrefManager.SP_JUMLAH_TAGIHAN,jumlahTagihan);
                        sharedPrefManager.saveSPString(SharedPrefManager.SP_STATUS_TAGIHAN,statusTagihan);
                        sharedPrefManager.saveSPString(SharedPrefManager.SP_TAHUN_AJARAN_TAGIHAN,tahunAjaranTagihan);
                        sharedPrefManager.saveSPString(SharedPrefManager.SP_DATE_TAGIHAN,dateUpdateTagihan);

                        Intent home=new Intent(LoginWaliActivity.this, HomeActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(home);
                        finish();

                        progressDialog.dismiss();

                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(LoginWaliActivity.this, message,Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<DataResponse> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("Wali :", "Failed API Login");
                Toast.makeText(getApplicationContext(), "Mohon Periksa Internet Anda !",Toast.LENGTH_LONG).show();
            }

        });

    }

    public void getRegister(String email, String password){
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<DataResponse> call = apiService.getRegisterWali(API_KEY,email, password);
        call.enqueue(new Callback<DataResponse>() {
            @Override
            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                if(response.isSuccessful()){
                    Log.e("Wali :","Sukses API Register");
                    String status = response.body().getStatus();
                    String message= response.body().getMessage();

                    if(status.equals("success")){
                        Toast.makeText(getApplicationContext(), message,Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getApplicationContext(), message,Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<DataResponse> call, Throwable t) {
                Log.e("Wali :", "Failed API Login");
                Toast.makeText(getApplicationContext(), "Mohon Periksa Internet Anda !",Toast.LENGTH_LONG).show();
            }

        });
    }

    public void getForgot(String email){
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<DataResponse> call = apiService.getForgotWali(API_KEY,email);
        call.enqueue(new Callback<DataResponse>() {
            @Override
            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                if(response.isSuccessful()){
                    Log.e("Wali :","Sukses API Forgot");
                    String status = response.body().getStatus();
                    String message= response.body().getMessage();

                    if(status.equals("success")){
                        Toast.makeText(getApplicationContext(), message,Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getApplicationContext(), message,Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<DataResponse> call, Throwable t) {
                Log.e("Wali :", "Failed API Forgot");
                Toast.makeText(getApplicationContext(), "Mohon Periksa Internet Anda !",Toast.LENGTH_LONG).show();
            }

        });
    }

    public static boolean isNullOrEmpty(String str) {
        if(str != null && !str.isEmpty())
            return false;
        return true;
    }
}

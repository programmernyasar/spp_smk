package com.kulodev.smkppi.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.kulodev.smkppi.Database.SharedPrefManager;
import com.kulodev.smkppi.Model.DataResponse;
import com.kulodev.smkppi.R;
import com.kulodev.smkppi.Rest.ApiClient;
import com.kulodev.smkppi.Rest.ApiInterface;
import com.scottyab.showhidepasswordedittext.ShowHidePasswordEditText;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kulodev.smkppi.Database.DataConstant.API_KEY;

public class HomeActivity extends AppCompatActivity {
    private Button btn_detail_profile;
    private ImageView img_ganti_password;
    private ImageView img_tagihan;
    private ImageView img_pengumuman;
    private ImageView img_logout;
    private TextView id_user;
    private TextView nama_user;
    private TextView catatan_tagihan;
    private TextView pembayaran_tagihan;
    private TextView jumlah_tagihan;
    private TextView tanggal_tagihan;
    private Button bayar_tagihan;
    private TextView judul_pengumuman;
    private TextView tujuan_pengumuman;
    private TextView tanggal_pengumuman;
    private MaterialCardView layoutTagihan;
    private MaterialCardView layoutPengumuman;
    SharedPrefManager sharedPrefManager;

    private String judulPengumuman;
    private String tujuanPengumuman;
    private String tanggalPengumuman;
    private String isiPengumuman;
    private String sessionPengumuman;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_home);

        initComponent();
        onClick();
        setDataUser();
        setDataTagihan();
        setDataPengumuman();
        getCurrentFirebaseToken();




    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    private void initComponent(){

        sharedPrefManager = new SharedPrefManager(this);
        sessionPengumuman="home";

        btn_detail_profile=findViewById(R.id.btn_detail_profile);
        img_ganti_password=findViewById(R.id.img_ganti_password);
        img_tagihan=findViewById(R.id.img_tagihan);
        img_pengumuman=findViewById(R.id.img_pengumuman);
        img_logout=findViewById(R.id.img_logout);
        id_user=findViewById(R.id.id_user_home);
        nama_user=findViewById(R.id.nama_user_home);
        catatan_tagihan=findViewById(R.id.tv_catatan_tagihan_home);
        pembayaran_tagihan=findViewById(R.id.tv_pembayaran_tagihan_home);
        jumlah_tagihan=findViewById(R.id.tv_jumlah_tagihan_home);
        tanggal_tagihan=findViewById(R.id.tv_tanggal_tagihan_home);
        bayar_tagihan=findViewById(R.id.btn_bayar_tagihan_home);
        judul_pengumuman=findViewById(R.id.tv_judul_pengumuman_home);
        tujuan_pengumuman=findViewById(R.id.tv_tujuan_pengumuman_home);
        tanggal_pengumuman=findViewById(R.id.tv_tanggal_pengumuman_home);
        layoutTagihan=findViewById(R.id.layout_tagihan_home);
        layoutPengumuman=findViewById(R.id.layout_pengumuman_home);

    }

    private void onClick(){
        btn_detail_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detailProfile=new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(detailProfile);
            }
        });

        img_ganti_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogGantiPassword();
            }
        });

        img_tagihan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tagihan=new Intent(HomeActivity.this, TagihanActivity.class);
                startActivity(tagihan);
            }
        });

        img_pengumuman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pengumuman=new Intent(HomeActivity.this, PengumumanActivity.class);
                startActivity(pengumuman);
            }
        });

        img_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, false);
                startActivity(new Intent(HomeActivity.this, LoginSiswaActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
        });

        bayar_tagihan.setOnClickListener(new View.OnClickListener() {
            String sessionPembayaran= "home";
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("id_tagihan", sharedPrefManager.getSPIdTagihan());
                bundle.putString("jumlah_tagihan", sharedPrefManager.getSPJumlahTagihan());
                bundle.putString("catatan_tagihan", sharedPrefManager.getSPCatatanTagihan());
                bundle.putString("session_pembayaran",sessionPembayaran);
                Intent pembayaran=new Intent(HomeActivity.this, PembayaranActivity.class);
                pembayaran.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                pembayaran.putExtras(bundle);
                HomeActivity.this.startActivity(pembayaran);
            }
        });

        layoutPengumuman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("session_pengumuman", sessionPengumuman);
                bundle.putString("judul_pengumuman", judulPengumuman);
                bundle.putString("tujuan_pengumuman", tujuanPengumuman);
                bundle.putString("tanggal_pengumuman", tanggalPengumuman);
                bundle.putString("isi_pengumuman", isiPengumuman);
                Intent detailPengumuman=new Intent(HomeActivity.this, DetailPengumumanActivity.class);
                detailPengumuman.putExtras(bundle);
                startActivity(detailPengumuman);
            }
        });

    }

    private void setDataUser(){
        String session=sharedPrefManager.getSPSession();

        if(session.equals("siswa")){
            id_user.setText(sharedPrefManager.getSPNis());
            nama_user.setText(sharedPrefManager.getSPNama());
        }else if(session.equals("wali")){
            id_user.setText(sharedPrefManager.getSPEmail());
            nama_user.setText(sharedPrefManager.getSPNama());
        }


    }

    private void setDataTagihan(){
        boolean adaTagihan=sharedPrefManager.getSPAdaTagihan();
        String statusTagihan=sharedPrefManager.getSPStatusTagihan();
        String jumlahTagihan=sharedPrefManager.getSPJumlahTagihan();

        if(adaTagihan){
            catatan_tagihan.setText(sharedPrefManager.getSPCatatanTagihan());
            tanggal_tagihan.setText(sharedPrefManager.getDateTagihan());
            jumlah_tagihan.setText(kurensiIndonesia(jumlahTagihan));

            if(statusTagihan.equals("Lunas")){

                bayar_tagihan.setVisibility(View.GONE);
                pembayaran_tagihan.setText(sharedPrefManager.getSPStatusTagihan()+" ("+sharedPrefManager.getSPPembayaranTagihan()+")");
                pembayaran_tagihan.setBackgroundColor(Color.parseColor("#2196F3"));

            }else if(statusTagihan.equals("Menunggu Konfirmasi")){

                bayar_tagihan.setVisibility(View.GONE);
                pembayaran_tagihan.setText(sharedPrefManager.getSPStatusTagihan());
                pembayaran_tagihan.setBackgroundColor(Color.parseColor("#2196F3"));

            }else if(statusTagihan.equals("Dispensasi")){

                pembayaran_tagihan.setText(sharedPrefManager.getSPStatusTagihan());
                pembayaran_tagihan.setBackgroundColor(Color.parseColor("#FF9800"));

            }else if(statusTagihan.equals("Pembayaran Gagal")){
                pembayaran_tagihan.setText(sharedPrefManager.getSPStatusTagihan());
                pembayaran_tagihan.setBackgroundColor(Color.parseColor("#F44336"));

            }else{
                pembayaran_tagihan.setText(sharedPrefManager.getSPStatusTagihan());
                pembayaran_tagihan.setBackgroundColor(Color.parseColor("#F44336"));
            }

        }else {
            layoutTagihan.setVisibility(View.GONE);
        }

    }

    private void setDataPengumuman(){
        boolean adaPengumuman=sharedPrefManager.getSPAdaPengumuman();

        if(adaPengumuman){
            judulPengumuman=sharedPrefManager.getSPJudulPengumuman();
            tujuanPengumuman=sharedPrefManager.getSPTujuanPengumuman();
            tanggalPengumuman=sharedPrefManager.getSPDatePengumuman();
            isiPengumuman =sharedPrefManager.getSPIsiPengumuman();

            judul_pengumuman.setText(judulPengumuman);
            if(sharedPrefManager.getSPNis().equals(tujuanPengumuman)){
                tujuan_pengumuman.setText("Anda");
            }else{
                tujuan_pengumuman.setText(tujuanPengumuman);
            }
            tanggal_pengumuman.setText(tanggalPengumuman);

        }else {
            layoutPengumuman.setVisibility(View.GONE);
        }

    }

    private void dialogGantiPassword() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_ganti_password);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;


        ((AppCompatButton) dialog.findViewById(R.id.bt_confirm)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowHidePasswordEditText edt_old_password=dialog.findViewById(R.id.edt_oldpassword_change);
                ShowHidePasswordEditText edt_new_password=dialog.findViewById(R.id.edt_newpassword_change);
                String nis= sharedPrefManager.getSPNis();
                String email= sharedPrefManager.getSPEmail();
                String session=sharedPrefManager.getSPSession();
                String oldPassword= String.valueOf(edt_old_password.getText());
                String newPassword= String.valueOf(edt_new_password.getText());

                if(session.equals("siswa")){
                    getChangePasswordSiswa(nis,oldPassword,newPassword);
                }else{
                    getChangePasswordWali(email,oldPassword,newPassword);
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

    public void getChangePasswordSiswa(String nis,String oldpassword, String newpassword){
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<DataResponse> call = apiService.getChangePasswordSiswa(nis,API_KEY, oldpassword, newpassword);
        call.enqueue(new Callback<DataResponse>() {
            @Override
            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                if(response.isSuccessful()){
                    Log.e("Siswa :","Sukses API Change Password");
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
                Log.e("Siswa :", "Failed API Login");
                Toast.makeText(getApplicationContext(), "Mohon Periksa Internet Anda !",Toast.LENGTH_LONG).show();
            }

        });
    }

    public void getChangePasswordWali(String email,String oldpassword, String newpassword){
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<DataResponse> call = apiService.getChangePasswordWali(email,API_KEY, oldpassword, newpassword);
        call.enqueue(new Callback<DataResponse>() {
            @Override
            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                if(response.isSuccessful()){
                    Log.e("Wali :","Sukses API Change Password");
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
                Log.e("Wali :", "Failed API Change Password");
                Toast.makeText(getApplicationContext(), "Mohon Periksa Internet Anda !",Toast.LENGTH_LONG).show();
            }

        });
    }

    private String kurensiIndonesia(String nominal){
        double jumlah=Double.valueOf(nominal);
        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("- Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');

        kursIndonesia.setDecimalFormatSymbols(formatRp);
        String hasil=kursIndonesia.format(jumlah);
        String[] hasilAkhir=hasil.split(",");

        return  hasilAkhir[0];
    }

    // Notification

    private void getCurrentFirebaseToken(){
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("TAG", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        Log.e("currentToken", token);

                        // Log and toast
                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.d("TAG", msg);
                        Toast.makeText(HomeActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }



        /* Dialog Show All Menu
    private void dialogShowAllMenu() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_semua_menu);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        ((FloatingActionButton) dialog.findViewById(R.id.btn_logout)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, false);
                startActivity(new Intent(HomeActivity.this, LoginSiswaActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
//                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

     */

}

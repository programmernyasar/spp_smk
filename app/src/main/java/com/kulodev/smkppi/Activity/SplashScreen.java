package com.kulodev.smkppi.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.kulodev.smkppi.Database.SharedPrefManager;
import com.kulodev.smkppi.Model.DataPengumuman;
import com.kulodev.smkppi.Model.DataResponse;
import com.kulodev.smkppi.Model.DataSiswa;
import com.kulodev.smkppi.Model.DataTagihan;
import com.kulodev.smkppi.Model.DataUserSiswa;
import com.kulodev.smkppi.Model.DataUserWali;
import com.kulodev.smkppi.R;
import com.kulodev.smkppi.Rest.ApiClient;
import com.kulodev.smkppi.Rest.ApiInterface;

import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kulodev.smkppi.Database.DataConstant.API_KEY;

public class SplashScreen extends AppCompatActivity {
    private int waktu_loading = 1000;
    private SharedPrefManager sharedPrefManager;
    private LinearLayout lyt_progress;
    private List<DataUserSiswa> dataUserSiswa = new ArrayList<>();
    private List<DataUserWali> dataUserWali = new ArrayList<>();
    private List<DataSiswa> dataSiswa = new ArrayList<>();
    private List<DataTagihan> dataTagihan = new ArrayList<>();
    private List<DataPengumuman> dataPengumuman = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        sharedPrefManager = new SharedPrefManager(this);
        lyt_progress = findViewById(R.id.lyt_progress);
        loadingAndDisplayContent();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (sharedPrefManager.getSPSudahLogin()) {
                    String session=sharedPrefManager.getSPSession();
                    String nis=sharedPrefManager.getSPNis();
                    String password=sharedPrefManager.getSPPassword();
                    String email=sharedPrefManager.getSPEmail();
                    if(session.equals("siswa")){

                        getLoginSiswa(nis,password);

                    }else if(session.equals("wali")){

                        getLoginWali(email,password);

                    }


                } else {
                    Intent home = new Intent(SplashScreen.this, LoginSiswaActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(home);
                    finish();
                }
            }
        }, waktu_loading);
    }


    private void loadingAndDisplayContent() {
        lyt_progress.setVisibility(View.VISIBLE);
        lyt_progress.setAlpha(1.0f);

    }

    public void getLoginSiswa(String nis, String password){

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<DataResponse> call = apiService.getLoginSiswa(API_KEY,nis, password);
        call.enqueue(new Callback<DataResponse>() {
            @Override
            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                if(response.isSuccessful()){
                    dataUserSiswa = response.body().getLoginSiswa();
                    dataSiswa = response.body().getDataSiswa();
                    dataTagihan=response.body().getTagihanTerbaru();
                    dataPengumuman=response.body().getPengumumanTerbaru();
                    String status = response.body().getStatus();
                    String message= response.body().getMessage();

                    if(status.equals("success")){
                        Log.e("Siswa :","Sukses API Splashscreen");
                        //Save Session User
                        String nama=dataUserSiswa.get(0).getNamaSiswa();
                        String email=dataUserSiswa.get(0).getMailUserSiswa();
                        String kelas=dataUserSiswa.get(0).getKelas();
                        String tahunAjaran=dataUserSiswa.get(0).getTahunAjaran();
                        sharedPrefManager.saveSPString(SharedPrefManager.SP_NIS,nis);
                        sharedPrefManager.saveSPString(SharedPrefManager.SP_NAMA,nama);
                        sharedPrefManager.saveSPString(SharedPrefManager.SP_EMAIL,email);
                        sharedPrefManager.saveSPString(SharedPrefManager.SP_KELAS,kelas);
                        sharedPrefManager.saveSPString(SharedPrefManager.SP_TAHUN_AJARAN,tahunAjaran);

                        if(!dataPengumuman.isEmpty()){
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
                        }else {
                            sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_ADA_PENGUMUMAN,false);
                        }


                        if(!dataTagihan.isEmpty()){
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
                        }else {
                            sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_ADA_TAGIHAN,false);
                        }


                        Intent home=new Intent(SplashScreen.this, HomeActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(home);
                        finish();



                    }else{

                        Intent home=new Intent(SplashScreen.this, LoginSiswaActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(home);
                        finish();
                        Toast.makeText(SplashScreen.this, message,Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<DataResponse> call, Throwable t) {

                Log.e("Siswa :", "Failed API Splashscreen");
                Toast.makeText(getApplicationContext(), "Mohon Periksa Internet Anda !",Toast.LENGTH_LONG).show();
            }

        });

    }

    public void getLoginWali(String email, String password){

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
                        Log.e("Wali :","Sukses API Login");
                        //Save Session User
                        String nis=dataUserWali.get(0).getNisSiswa();
                        String nama=dataUserWali.get(0).getNamaWali();
                        String email=dataUserWali.get(0).getMailUserWali();
                        String kelas=dataUserWali.get(0).getKelas();
                        String tahunAjaran=dataUserWali.get(0).getTahunAjaran();
                        sharedPrefManager.saveSPString(SharedPrefManager.SP_NIS,nis);
                        sharedPrefManager.saveSPString(SharedPrefManager.SP_NAMA,nama);
                        sharedPrefManager.saveSPString(SharedPrefManager.SP_EMAIL,email);
                        sharedPrefManager.saveSPString(SharedPrefManager.SP_KELAS,kelas);
                        sharedPrefManager.saveSPString(SharedPrefManager.SP_TAHUN_AJARAN,tahunAjaran);

                        if(!dataPengumuman.isEmpty()){
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
                        }else {
                            sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_ADA_PENGUMUMAN,false);
                        }


                        if(!dataTagihan.isEmpty()){
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
                        }else {
                            sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_ADA_TAGIHAN,false);
                        }


                        Intent home=new Intent(SplashScreen.this, HomeActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(home);
                        finish();



                    }else{

                        Intent login=new Intent(SplashScreen.this, LoginWaliActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(login);
                        finish();
                        Toast.makeText(SplashScreen.this, message,Toast.LENGTH_LONG).show();
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


}
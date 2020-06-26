package com.kulodev.smkppi.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.kulodev.smkppi.Adapter.PengumumanListAdapter;
import com.kulodev.smkppi.Database.SharedPrefManager;
import com.kulodev.smkppi.Model.DataPengumuman;
import com.kulodev.smkppi.Model.DataResponse;
import com.kulodev.smkppi.R;
import com.kulodev.smkppi.Rest.ApiClient;
import com.kulodev.smkppi.Rest.ApiInterface;
import com.kulodev.smkppi.widget.ViewAnimation;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kulodev.smkppi.Database.DataConstant.API_KEY;

public class PengumumanActivity extends AppCompatActivity {

    private ImageView icon_back;
    private RecyclerView RlistPengumuman;
    private LinearLayout lyt_progress;
    private PengumumanListAdapter adapter;
    private SharedPrefManager sharedPrefManager;
    private List<DataPengumuman> dataPengumuman = new ArrayList<>();
    private String nis;
    private String kelas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_pengumuman);

        initComponent();
        onClick();
        loadingAndDisplayContent();
        showRecycleView();
        getDataPengumuman();

    }

    @Override
    public void onBackPressed() {
        Intent home=new Intent(PengumumanActivity.this, HomeActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(home);
        finish();
    }

    private void initComponent(){
        sharedPrefManager = new SharedPrefManager(this);
        nis=sharedPrefManager.getSPNis();
        kelas=sharedPrefManager.getSPKelas();

        icon_back=findViewById(R.id.icon_back);
        lyt_progress=findViewById(R.id.lyt_progress_pengumuman);
        RlistPengumuman=findViewById(R.id.list_pengumuman);
    }

    private void onClick(){

        icon_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home=new Intent(PengumumanActivity.this, HomeActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(home);
                finish();
            }
        });

    }

    private void showRecycleView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(PengumumanActivity.this);
        RlistPengumuman.setLayoutManager(layoutManager);
        adapter= new PengumumanListAdapter();
        adapter.notifyDataSetChanged();
        RlistPengumuman.setAdapter(adapter);

    }

    private void getDataPengumuman(){
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<DataResponse> call = apiService.getPengumuman(nis,kelas,API_KEY);
        call.enqueue(new Callback<DataResponse>() {
            @Override
            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                if(response.isSuccessful()){
                    Log.e("Siswa :","Sukses API Pengumuman");
                    dataPengumuman = response.body().getPengumuman();
                    String status = response.body().getStatus();
                    String message= response.body().getMessage();

                    if(status.equals("success")){

                        adapter.setPengumumanData(getApplicationContext(),dataPengumuman);
                        ViewAnimation.fadeOut(lyt_progress);

                    }else{
                        Toast.makeText(PengumumanActivity.this, "Server Bermasalah",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<DataResponse> call, Throwable t) {
                Log.e("Siswa :", "Failed API Pengumuman");
                Toast.makeText(getApplicationContext(), "Mohon Periksa Internet Anda !",Toast.LENGTH_LONG).show();
            }

        });

    }

    private void loadingAndDisplayContent() {
        lyt_progress.setVisibility(View.VISIBLE);
        lyt_progress.setAlpha(1.0f);

    }


}

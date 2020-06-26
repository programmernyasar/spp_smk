package com.kulodev.smkppi.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.kulodev.smkppi.Adapter.TagihanListAdapter;
import com.kulodev.smkppi.Database.SharedPrefManager;
import com.kulodev.smkppi.Model.DataResponse;
import com.kulodev.smkppi.Model.DataTagihan;
import com.kulodev.smkppi.Model.DataTahunAjaran;
import com.kulodev.smkppi.R;
import com.kulodev.smkppi.Rest.ApiClient;
import com.kulodev.smkppi.Rest.ApiInterface;
import com.kulodev.smkppi.widget.ViewAnimation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kulodev.smkppi.Database.DataConstant.API_KEY;

public class TagihanActivity extends AppCompatActivity {

    private ImageView icon_back;
    private SmartMaterialSpinner spinner_tahun_ajaran;

    private RecyclerView RlistTagihan;
    private LinearLayout lyt_progress;
    private TagihanListAdapter adapter;


    private SharedPrefManager sharedPrefManager;
    private List<DataTahunAjaran> dataTahunAjaran = new ArrayList<>();
    private List<DataTagihan> dataTagihan = new ArrayList<>();
    private List<String> listTahunAjaran = new ArrayList<String>();
    private String nis;
    private String tahunAjaran;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_tagihan);

        initComponent();
        getDataSpinner();
        onClick();

        showRecycleView();
        getDataTagihan(tahunAjaran);
    }

    @Override
    public void onBackPressed() {
        Intent home=new Intent(TagihanActivity.this, HomeActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(home);
        finish();
    }

    private void initComponent(){
        sharedPrefManager = new SharedPrefManager(this);
        nis=sharedPrefManager.getSPNis();
        tahunAjaran=sharedPrefManager.getSPTahunAjaran();

        icon_back=findViewById(R.id.icon_back);
        lyt_progress=findViewById(R.id.lyt_progress_tagihan);
        spinner_tahun_ajaran=findViewById(R.id.spinnerTahunAjaran);
        RlistTagihan=findViewById(R.id.list_tagihan);

    }

    private void getDataSpinner(){
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<DataResponse> call = apiService.getTahunAjaran(nis,API_KEY);
        call.enqueue(new Callback<DataResponse>() {
            @Override
            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                if(response.isSuccessful()){
                    Log.e("Siswa :","Sukses API Tahun Ajaran");
                    dataTahunAjaran = response.body().getDataTahunAjaran();
                    String status = response.body().getStatus();
                    String message= response.body().getMessage();

                    if(status.equals("success")){

                        for (int i = 0; i < dataTahunAjaran.size(); i++) {
                            String tahunAjaran= dataTahunAjaran.get(i).getTahunAjaran();
                            Log.e("Tahun Ajaran",tahunAjaran);

                            listTahunAjaran.add(tahunAjaran);
                        }

                        spinner_tahun_ajaran.setItem(listTahunAjaran);
                        spinner_tahun_ajaran.setSelection(0);

                    }else{
                        Toast.makeText(TagihanActivity.this, "Server Bermasalah",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<DataResponse> call, Throwable t) {
                Log.e("Siswa :", "Failed API List Tahun Ajaran");
                Toast.makeText(getApplicationContext(), "Mohon Periksa Internet Anda !",Toast.LENGTH_LONG).show();
            }

        });
    }

    private void onClick(){
        spinner_tahun_ajaran.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id_spinner) {

                String tahunAjaranChoice = listTahunAjaran.get(position);
                getDataTagihan(tahunAjaranChoice);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        icon_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home=new Intent(TagihanActivity.this, HomeActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(home);
                finish();
            }
        });
    }

    private void showRecycleView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(TagihanActivity.this);
        RlistTagihan.setLayoutManager(layoutManager);
        adapter= new TagihanListAdapter();
        adapter.notifyDataSetChanged();
        RlistTagihan.setAdapter(adapter);

    }

    private void getDataTagihan(String tahunAjaran){
        loadingAndDisplayContent();

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<DataResponse> call = apiService.getTagihan(nis,tahunAjaran,API_KEY);
        call.enqueue(new Callback<DataResponse>() {
            @Override
            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                if(response.isSuccessful()){
                    Log.e("Siswa :","Sukses API Tagihan");
                    dataTagihan = response.body().getTagihan();
                    String status = response.body().getStatus();
                    String message= response.body().getMessage();

                    if(status.equals("success")){

                        adapter.setTagihanData(getApplicationContext(),dataTagihan);
                        ViewAnimation.fadeOut(lyt_progress);


                    }else{
                        Toast.makeText(TagihanActivity.this, "Server Bermasalah",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<DataResponse> call, Throwable t) {
                Log.e("Siswa :", "Failed API Tagihan");
                Toast.makeText(getApplicationContext(), "Mohon Periksa Internet Anda !",Toast.LENGTH_LONG).show();
            }

        });

    }

    private void loadingAndDisplayContent() {
        lyt_progress.setVisibility(View.VISIBLE);
        lyt_progress.setAlpha(1.0f);

    }


}

package com.kulodev.smkppi.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;

import com.kulodev.smkppi.R;
import com.kulodev.smkppi.utils.Tools;

public class DetailPengumumanActivity extends AppCompatActivity {
    private ImageButton icon_back;
    private TextView tv_judul_pengumuman;
    private TextView tv_tujuan_pengumuman;
    private TextView tv_tanggal_pengumuman;
    private TextView tv_isi_pengumuman;

    private String sessionPengumuman;
    private String judulPengumuman;
    private String tujuanPengumuman;
    private String tanggalPengumuman;
    private String isiPengumuman;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pengumuman);
        initComponent();
        initToolbar();
        getBundle();
        setData();
    }

    @Override
    public void onBackPressed() {
        if(sessionPengumuman.equals("home")){
            Intent home=new Intent(DetailPengumumanActivity.this, HomeActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(home);
            finish();
        }else{
            finish();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent home=new Intent(DetailPengumumanActivity.this, HomeActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(home);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initComponent(){
        tv_judul_pengumuman=findViewById(R.id.tv_judul_pengumuman_detail);
        tv_tujuan_pengumuman=findViewById(R.id.tv_tujuan_pengumuman_detail);
        tv_tanggal_pengumuman=findViewById(R.id.tv_tanggal_pengumuman_detail);
        tv_isi_pengumuman=findViewById(R.id.tv_isi_pengumuman_detail);
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this, R.color.colorPrimary);
    }

    private void getBundle(){
        if(getIntent().getExtras()!=null){
            Bundle bundle = getIntent().getExtras();
            sessionPengumuman=bundle.getString("session_pengumuman");
            judulPengumuman=bundle.getString("judul_pengumuman");
            tujuanPengumuman=bundle.getString("tujuan_pengumuman");
            tanggalPengumuman=bundle.getString("tanggal_pengumuman");
            isiPengumuman=bundle.getString("isi_pengumuman");

        }
    }

    private void setData(){
        tv_judul_pengumuman.setText(judulPengumuman);
        tv_tujuan_pengumuman.setText(tujuanPengumuman);
        tv_tanggal_pengumuman.setText(tanggalPengumuman);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tv_isi_pengumuman.setText(Html.fromHtml(isiPengumuman, Html.FROM_HTML_MODE_COMPACT));
        } else {
            tv_isi_pengumuman.setText(Html.fromHtml(isiPengumuman));
        }
    }



}

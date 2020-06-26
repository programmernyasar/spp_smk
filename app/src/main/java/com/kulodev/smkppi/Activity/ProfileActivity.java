package com.kulodev.smkppi.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.balysv.materialripple.MaterialRippleLayout;
import com.kulodev.smkppi.Database.SharedPrefManager;
import com.kulodev.smkppi.Model.DataResponse;
import com.kulodev.smkppi.Model.DataSiswa;
import com.kulodev.smkppi.R;
import com.kulodev.smkppi.Rest.ApiClient;
import com.kulodev.smkppi.Rest.ApiInterface;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kulodev.smkppi.Database.DataConstant.API_KEY;
import static com.kulodev.smkppi.Database.DataConstant.BASE_IMAGE_URL_PROFILE;

public class ProfileActivity extends AppCompatActivity {
    private ImageView btn_back;
    private CircleImageView img_profile;
    private TextView nama_header;
    private TextView id_header;
    private TextView nis;
    private TextView nama;
    private TextView jenis_kelamin;
    private TextView kelas;
    private TextView tahun_ajaran;
    private TextView kelahiran;
    private TextView email;
    private TextView no_hp;
    private TextView alamat;
    private TextView bapak;
    private TextView ibu;
    private TextView hp_wali;
    private TextView email_wali;
    private TextView alamat_wali;
    private MaterialRippleLayout btn_edit;
    private SharedPrefManager sharedPrefManager;
    private String session;
    private List<DataSiswa> dataSiswa = new ArrayList<>();
    private ProgressDialog progressDialog;

    private String imageProfile;
    private String kelasProfile;
    private String jurusanProfile;
    private String tanggal_lahir;
    private String tempat_lahir;
    private String kode_jenis_kelamin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_profile);

        initComponent();
        onClick();
        getDataSiswa();

    }

    @Override
    public void onBackPressed() {
        Intent home=new Intent(ProfileActivity.this, HomeActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(home);
        finish();
    }

    private void initComponent(){

        sharedPrefManager = new SharedPrefManager(this);
        session=sharedPrefManager.getSPSession();

        img_profile=findViewById(R.id.img_profile);
        nama_header=findViewById(R.id.tv_nama_profile_header);
        id_header =findViewById(R.id.tv_id_profile_header);
        nis=findViewById(R.id.tv_nis_profile);
        nama=findViewById(R.id.tv_nama_profile);
        jenis_kelamin=findViewById(R.id.tv_jenis_kelamin);
        kelas=findViewById(R.id.tv_kelas_profile);
        tahun_ajaran=findViewById(R.id.tv_tahun_ajaran_profile);
        kelahiran=findViewById(R.id.tv_kelahiran_profile);
        email=findViewById(R.id.tv_email_profile);
        no_hp=findViewById(R.id.tv_hp_profile);
        alamat=findViewById(R.id.tv_alamat_profile);
        bapak=findViewById(R.id.tv_bapak_profile);
        ibu=findViewById(R.id.tv_ibu_profile);
        hp_wali=findViewById(R.id.tv_hp_wali_profile);
        email_wali=findViewById(R.id.tv_email_wali_profile);
        alamat_wali=findViewById(R.id.tv_alamat_wali_profile);
        btn_back=findViewById(R.id.btn_back);
        btn_edit=findViewById(R.id.btn_edt_profile);

        if(session.equals("wali")){
            btn_edit.setVisibility(View.GONE);
        }

    }

    private void onClick(){

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home=new Intent(ProfileActivity.this, HomeActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(home);
                finish();
            }
        });

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("img_profile", imageProfile);
                bundle.putString("nis_siswa", nis.getText().toString());
                bundle.putString("nama_siswa", nama.getText().toString());
                bundle.putString("jenis_kelamin", jenis_kelamin.getText().toString());
                bundle.putString("kode_jenis_kelamin", kode_jenis_kelamin);
                bundle.putString("tempat_lahir", tempat_lahir);
                bundle.putString("tanggal_lahir", tanggal_lahir);
                bundle.putString("email", email.getText().toString());
                bundle.putString("no_hp", no_hp.getText().toString());
                bundle.putString("alamat", alamat.getText().toString());
                bundle.putString("bapak", bapak.getText().toString());
                bundle.putString("ibu", ibu.getText().toString());
                bundle.putString("no_hp_wali", hp_wali.getText().toString());
                bundle.putString("email_wali", email_wali.getText().toString());
                bundle.putString("alamat_wali", alamat_wali.getText().toString());
                Intent editProfile=new Intent(ProfileActivity.this, ProfileEditActivity.class);
                editProfile.putExtras(bundle);
                startActivity(editProfile);
            }
        });

    }

    public void getDataSiswa(){

        progressDialog = new ProgressDialog(ProfileActivity.this);
        progressDialog.setMessage("Loading..."); // Setting Message
        progressDialog.setCancelable(false);
        progressDialog.show(); // Display Progress Dialog

        String nisData= sharedPrefManager.getSPNis();
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<DataResponse> call = apiService.getDataSiswa(nisData,API_KEY);
        call.enqueue(new Callback<DataResponse>() {
            @Override
            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                if(response.isSuccessful()){
                    Log.e("Siswa :","Sukses API Profile");
                    dataSiswa = response.body().getDataSiswa();
                    String status = response.body().getStatus();
                    String message= response.body().getMessage();
                    if(status.equals("success")){

                        setData();

                    }else{
                        Toast.makeText(ProfileActivity.this, "Server Bermasalah",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<DataResponse> call, Throwable t) {
                Log.e("Siswa :", "Failed API Siswa");
                Toast.makeText(getApplicationContext(), "Mohon Periksa Internet Anda !",Toast.LENGTH_LONG).show();
            }

        });
    }

    private void setData(){
        // Variabel Umum
        imageProfile=dataSiswa.get(0).getFotoSiswa();
        kelasProfile=dataSiswa.get(0).getKelas();
        jurusanProfile=dataSiswa.get(0).getJurusan();
        tanggal_lahir=dataSiswa.get(0).getTanggalLahir();
        tempat_lahir=dataSiswa.get(0).getTempatLahir();

        // Variabel Lokal
        String namaHeader=sharedPrefManager.getSPNama();
        String emailHeader=sharedPrefManager.getSPEmail();
        String nisSiswa=dataSiswa.get(0).getNis();
        String namaSiswa=dataSiswa.get(0).getNama();

        kode_jenis_kelamin=dataSiswa.get(0).getJenisKelamin();
        String jenisKelaminSiswa="none";
        if(kode_jenis_kelamin.equals("L")){
           jenisKelaminSiswa="Laki-Laki";
        }else if(kode_jenis_kelamin.equals("P")){
            jenisKelaminSiswa="Perempuan";
        }

        String kelasSiswa=dataSiswa.get(0).getKelas()+" ("+dataSiswa.get(0).getJurusan()+")";
        String tahunAjaran=dataSiswa.get(0).getTahunAjaran();
        String kelahiranSiswa=dataSiswa.get(0).getTempatLahir()+", "+dataSiswa.get(0).getTanggalLahir();
        String noHpSiswa=dataSiswa.get(0).getNoHp();
        String emailSiswa=dataSiswa.get(0).getEmail();
        String alamatSiswa=dataSiswa.get(0).getAlamat();

        String namaBapakSiswa=dataSiswa.get(0).getBapakSiswa();
        String namaIbuSiswa=dataSiswa.get(0).getIbuSiswa();
        String noHpWalisiswa=dataSiswa.get(0).getNoHpWali();
        String emailWaliSiswa=dataSiswa.get(0).getEmailWali();
        String alamatWaliSiswa=dataSiswa.get(0).getAlamatWali();

        Picasso.get()
                .load(BASE_IMAGE_URL_PROFILE+imageProfile)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .placeholder(R.drawable.default_profile)
                .error(R.drawable.default_profile)
                .fit()
                .noFade()
                .into(img_profile);

        nama_header.setText(namaHeader);
        if(sharedPrefManager.getSPSession().equals("siswa")){
            id_header.setText(nisSiswa);
        }else {
            id_header.setText(emailHeader);
        }

        nis.setText(nisSiswa);
        nama.setText(namaSiswa);
        jenis_kelamin.setText(jenisKelaminSiswa);
        kelas.setText(kelasSiswa);
        tahun_ajaran.setText(tahunAjaran);
        kelahiran.setText(kelahiranSiswa);

        no_hp.setText(noHpSiswa);
        email.setText(emailSiswa);
        alamat.setText(alamatSiswa);

        bapak.setText(namaBapakSiswa);
        ibu.setText(namaIbuSiswa);
        hp_wali.setText(noHpWalisiswa);
        email_wali.setText(emailWaliSiswa);
        alamat_wali.setText(alamatWaliSiswa);

        progressDialog.dismiss();
    }

}

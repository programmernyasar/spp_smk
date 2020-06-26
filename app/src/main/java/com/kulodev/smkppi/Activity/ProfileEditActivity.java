package com.kulodev.smkppi.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.loader.content.CursorLoader;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.balysv.materialripple.MaterialRippleLayout;
import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kulodev.smkppi.Database.SharedPrefManager;
import com.kulodev.smkppi.Model.DataResponse;
import com.kulodev.smkppi.R;
import com.kulodev.smkppi.Rest.ApiClient;
import com.kulodev.smkppi.Rest.ApiInterface;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kulodev.smkppi.Database.DataConstant.API_KEY;
import static com.kulodev.smkppi.Database.DataConstant.BASE_IMAGE_URL_PROFILE;

public class ProfileEditActivity extends AppCompatActivity {

    private SharedPrefManager sharedPrefManager;

    private ImageView btn_back;
    private CircleImageView img_profile;
    private FloatingActionButton btn_upload_foto;
    private MaterialRippleLayout btn_save_profile;
    private DatePickerDialog datePickerDialog;
    private SmartMaterialSpinner jenis_kelamin_siswa;
    private EditText nama_siswa;
    private EditText tempat_lahir_siswa;
    private EditText tanggal_lahir_siswa;
    private EditText email_siswa;
    private EditText no_hp_siswa;
    private EditText alamat_siswa;
    private EditText bapak_siswa;
    private EditText ibu_siswa;
    private EditText no_hp_wali_siswa;
    private EditText alamat_wali_siswa;


    private String imgProfile;
    private String nisSiswa;
    private String namaSiswa;
    private String jenisKelamin;
    private String kodeJenisKelamin;
    private String tempatLahirSiswa;
    private String tanggalLahirSiswa;
    private String emailSiswa;
    private String noHpSiswa;
    private String alamatSiswa;
    private String bapakSiswa;
    private String ibuSiswa;
    private String noHpWali;
    private String alamatWali;
    private List<String> jenisKelaminSpinner;

    private Uri selectedImage;
    private boolean changeImage=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_profile_edit);

        getBundle();
        initComponent();
        spinnerJenisKelamin();
        setData();
        onClick();
        requestAppPermissions();

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void getBundle(){
        if(getIntent().getExtras()!=null){
            Bundle bundle = getIntent().getExtras();
            imgProfile=bundle.getString("img_profile");
            nisSiswa=bundle.getString("nis_siswa");
            namaSiswa=bundle.getString("nama_siswa");
            jenisKelamin=bundle.getString("jenis_kelamin");
            kodeJenisKelamin=bundle.getString("kode_jenis_kelamin");
            tempatLahirSiswa=bundle.getString("tempat_lahir");
            tanggalLahirSiswa=bundle.getString("tanggal_lahir");
            emailSiswa=bundle.getString("email");
            noHpSiswa=bundle.getString("no_hp");
            alamatSiswa=bundle.getString("alamat");
            bapakSiswa=bundle.getString("bapak");
            ibuSiswa=bundle.getString("ibu");
            noHpWali=bundle.getString("no_hp_wali");
            alamatWali=bundle.getString("alamat_wali");

        }
    }

    private void initComponent(){
        sharedPrefManager = new SharedPrefManager(this);
        btn_back=findViewById(R.id.btn_back);
        btn_save_profile=findViewById(R.id.btn_save_profile);
        img_profile=findViewById(R.id.img_profile);
        btn_upload_foto=findViewById(R.id.btn_img_upload_foto);
        nama_siswa=findViewById(R.id.edt_nama_siswa);
        jenis_kelamin_siswa=findViewById(R.id.spinner_jenis_kelamin);
        tempat_lahir_siswa=findViewById(R.id.edt_tempat_lahir);
        tanggal_lahir_siswa=findViewById(R.id.edt_tanggal_lahir);
        email_siswa=findViewById(R.id.edt_email_siswa);
        no_hp_siswa=findViewById(R.id.edt_hp_siswa);
        alamat_siswa=findViewById(R.id.edt_alamat_siswa);
        bapak_siswa=findViewById(R.id.edt_bapak_siswa);
        ibu_siswa=findViewById(R.id.edt_ibu_siswa);
        no_hp_wali_siswa=findViewById(R.id.edt_hp_wali);
        alamat_wali_siswa=findViewById(R.id.edt_alamat_wali);


    }

    private void spinnerJenisKelamin(){

        jenisKelaminSpinner=new ArrayList<>();
        jenisKelaminSpinner.add("Laki-Laki");
        jenisKelaminSpinner.add("Perempuan");

        jenis_kelamin_siswa.setItem(jenisKelaminSpinner);
        if(jenisKelamin.equals("Laki-Laki")){
            jenis_kelamin_siswa.setSelection(0);
        }else if(jenisKelamin.equals("Perempuan")){
            jenis_kelamin_siswa.setSelection(1);
        }



    }

    private void setData(){

        Picasso.get()
                .load(BASE_IMAGE_URL_PROFILE+imgProfile)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .placeholder(R.drawable.default_profile)
                .error(R.drawable.default_profile)
                .fit()
                .noFade()
                .into(img_profile);

        nama_siswa.setText(namaSiswa);
        tempat_lahir_siswa.setText(tempatLahirSiswa);
        tanggal_lahir_siswa.setText(tanggalLahirSiswa);
        email_siswa.setText(emailSiswa);
        no_hp_siswa.setText(noHpSiswa);
        alamat_siswa.setText(alamatSiswa);
        bapak_siswa.setText(bapakSiswa);
        ibu_siswa.setText(ibuSiswa);
        no_hp_wali_siswa.setText(noHpWali);
        alamat_wali_siswa.setText(alamatWali);

    }

    private void onClick(){

        jenis_kelamin_siswa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id_spinner) {

                String jenisKelaminChoice = jenisKelaminSpinner.get(position);
                if(jenisKelaminChoice.equals("Laki-Laki")){
                    kodeJenisKelamin="L";
                }else if(jenisKelaminChoice.equals("Perempuan")){
                    kodeJenisKelamin="P";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        tanggal_lahir_siswa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                datePickerDialog = new DatePickerDialog(ProfileEditActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                int tanggalDate= dayOfMonth;
                                int bulanDate= (monthOfYear + 1);
                                String bulan= String.valueOf(bulanDate);
                                String tanggal= String.valueOf(tanggalDate);
                                if(bulanDate<10){
                                    bulan="0"+bulanDate;
                                }
                                if(tanggalDate<10){
                                    tanggal="0"+tanggalDate;
                                }
                                tanggalLahirSiswa=tanggal + "-" + bulan + "-" + year;
                                tanggal_lahir_siswa.setText(tanggalLahirSiswa);
                                Log.e("bisa",tanggalLahirSiswa);

                            }
                        }, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }

        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_upload_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i,100);
            }
        });

        btn_save_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                progressDialog = new ProgressDialog(ProfileEditActivity.this);
//                progressDialog.setMessage("Loading..."); // Setting Message
//                progressDialog.setCancelable(false);
//                progressDialog.show(); // Display Progress Dialog

                prepareSaveData();
                if(changeImage){
                    getSaveDataImage(selectedImage);
                }else{
                    getSaveData();
                }


            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            selectedImage = data.getData();
            img_profile.setImageURI(selectedImage);
            changeImage=true;

        }
    }

    private void prepareSaveData(){
        namaSiswa=nama_siswa.getText().toString();
        tempatLahirSiswa=tempat_lahir_siswa.getText().toString();
        emailSiswa=email_siswa.getText().toString();
        noHpSiswa=no_hp_siswa.getText().toString();
        alamatSiswa=alamat_siswa.getText().toString();
        bapakSiswa=bapak_siswa.getText().toString();
        ibuSiswa=ibu_siswa.getText().toString();
        noHpWali=no_hp_wali_siswa.getText().toString();
        alamatWali=alamat_wali_siswa.getText().toString();
    }

    public void getSaveDataImage(Uri fileUri) {

        File file = new File(getRealPathFromURI(fileUri));
        RequestBody requestFile = RequestBody.create(MediaType.parse(getContentResolver().getType(fileUri)), file);
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<DataResponse> call = apiService.updateSiswaImage(nisSiswa,requestFile,namaSiswa,kodeJenisKelamin,
                tanggalLahirSiswa, tempatLahirSiswa,alamatSiswa,noHpSiswa,emailSiswa,
                bapakSiswa,ibuSiswa,noHpWali,alamatWali,API_KEY);

        call.enqueue(new Callback<DataResponse>() {
            @Override
            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                if(response.isSuccessful()){
                    Log.e("Siswa :","Sukses API Save Profile");
                    String status = response.body().getStatus();
                    String message= response.body().getMessage();
                    if(status.equals("success")){

                        sharedPrefManager.saveSPString(SharedPrefManager.SP_NAMA,namaSiswa);
                        sharedPrefManager.saveSPString(SharedPrefManager.SP_EMAIL,emailSiswa);
//                        progressDialog.dismiss();
                        Toast.makeText(ProfileEditActivity.this, message,Toast.LENGTH_LONG).show();
                        Intent home=new Intent(ProfileEditActivity.this, ProfileActivity.class);
                        startActivity(home);
                        finish();



                    }else{
//                        progressDialog.dismiss();
                        Toast.makeText(ProfileEditActivity.this, "Server Bermasalah",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<DataResponse> call, Throwable t) {
                Log.e("Siswa :", "Failed API Save Profile");
                Log.e("Error API:", t.getMessage());
//                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Mohon Periksa Internet Anda !",Toast.LENGTH_LONG).show();
            }

        });
    }

    public void getSaveData() {

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<DataResponse> call = apiService.updateSiswa(nisSiswa,namaSiswa,kodeJenisKelamin,
                tanggalLahirSiswa, tempatLahirSiswa,alamatSiswa,noHpSiswa,emailSiswa,
                bapakSiswa,ibuSiswa,noHpWali,alamatWali,API_KEY);

        call.enqueue(new Callback<DataResponse>() {
            @Override
            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                if(response.isSuccessful()){
                    Log.e("Siswa :","Sukses API Save Profile");
                    String status = response.body().getStatus();
                    String message= response.body().getMessage();
                    if(status.equals("success")){

                        sharedPrefManager.saveSPString(SharedPrefManager.SP_NAMA,namaSiswa);
                        sharedPrefManager.saveSPString(SharedPrefManager.SP_EMAIL,emailSiswa);
                        Toast.makeText(ProfileEditActivity.this, message,Toast.LENGTH_LONG).show();
                        Intent home=new Intent(ProfileEditActivity.this, ProfileActivity.class);
                        startActivity(home);
                        finish();

//                        progressDialog.dismiss();



                    }else{
//                        progressDialog.dismiss();
                        Toast.makeText(ProfileEditActivity.this, "Server Bermasalah",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<DataResponse> call, Throwable t) {
                Log.e("Siswa :", "Failed API Save Profile");
                Log.e("Error API:", t.getMessage());
//                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Mohon Periksa Internet Anda !",Toast.LENGTH_LONG).show();
            }

        });
    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(this, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

    private void requestAppPermissions() {
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return;
        }

        if (hasReadPermissions() && hasWritePermissions()) {
            return;
        }

        ActivityCompat.requestPermissions(this,
                new String[] {
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, 100); // your request code
    }

    private boolean hasReadPermissions() {
        return (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    private boolean hasWritePermissions() {
        return (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }


}

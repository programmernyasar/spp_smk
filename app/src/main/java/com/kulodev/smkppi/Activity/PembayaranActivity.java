package com.kulodev.smkppi.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.loader.content.CursorLoader;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.balysv.materialripple.MaterialRippleLayout;
import com.kulodev.smkppi.Database.SharedPrefManager;
import com.kulodev.smkppi.Model.DataResponse;
import com.kulodev.smkppi.R;
import com.kulodev.smkppi.Rest.ApiClient;
import com.kulodev.smkppi.Rest.ApiInterface;
import com.mvc.imagepicker.ImagePicker;


import java.io.File;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kulodev.smkppi.Database.DataConstant.API_KEY;

public class PembayaranActivity extends AppCompatActivity {

    private String idTagihan;
    private String jumlahTagihan;
    private String catatanTagihan;
    private String sessionPembayaran;
    private ImageView icon_back;
    private ImageView img_pembayaran;
    private MaterialRippleLayout btn_kirim_pembayaran;
    private TextView tv_jumlah_pembayaran;
    private TextView tv_catatan_pembayaran;

    private Uri selectedImage;
    private boolean changeImage=false;

    private SharedPrefManager sharedPrefManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_pembayaran);
        
        getBundle();
        initComponent();
        setData();
        onClick();
        requestAppPermissions();
    }

    private void getBundle(){
        if(getIntent().getExtras()!=null){
            Bundle bundle = getIntent().getExtras();
            idTagihan=bundle.getString("id_tagihan");
            jumlahTagihan=bundle.getString("jumlah_tagihan");
            catatanTagihan=bundle.getString("catatan_tagihan");
            sessionPembayaran=bundle.getString("session_pembayaran");

        }

    }

    private void initComponent(){
        sharedPrefManager = new SharedPrefManager(this);
        ImagePicker.setMinQuality(600, 600);
        icon_back=findViewById(R.id.icon_back_pembayaran);
        img_pembayaran=findViewById(R.id.img_pembayaran);
        tv_jumlah_pembayaran=findViewById(R.id.tv_jumlah_pembayaran);
        tv_catatan_pembayaran=findViewById(R.id.tv_catatan_pembayaran);
        btn_kirim_pembayaran=findViewById(R.id.btn_kirim_pembayaran);

    }

    private void setData(){
        tv_jumlah_pembayaran.setText(kurensiIndonesia(jumlahTagihan));
        tv_catatan_pembayaran.setText(catatanTagihan);
    }

    private void onClick(){

        icon_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sessionPembayaran.equals("list")) {
                    finish();
                }else{
                    Intent home=new Intent(PembayaranActivity.this, HomeActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(home);
                    finish();
                }

            }
        });

        img_pembayaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ImagePicker.pickImage(PembayaranActivity.this, "Selected Your Image");

            }
        });

        btn_kirim_pembayaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(changeImage) {
                    sendPayment(selectedImage);
                }else {
                    Toast.makeText(PembayaranActivity.this, "Mohon Upload Bukti Pembayaran",Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            selectedImage = data.getData();
            img_pembayaran.setImageURI(selectedImage);
            changeImage=true;
        }

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

    public void sendPayment(Uri fileUri) {
        String nis=sharedPrefManager.getSPNis();
        String status="Menunggu Konfirmasi";

        File file = new File(getRealPathFromURI(fileUri));
        RequestBody requestFile = RequestBody.create(MediaType.parse(getContentResolver().getType(fileUri)), file);
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<DataResponse> call = apiService.bayarTagihan(nis,idTagihan,requestFile,API_KEY);

        call.enqueue(new Callback<DataResponse>() {
            @Override
            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                if(response.isSuccessful()){
                    Log.e("Siswa :","Sukses API Pembayaran");
                    String status = response.body().getStatus();
                    String message= response.body().getMessage();
                    if(status.equals("success")){

//                        progressDialog.dismiss();
                        if(idTagihan.equals(sharedPrefManager.getSPIdTagihan())){
                            sharedPrefManager.saveSPString(SharedPrefManager.SP_STATUS_TAGIHAN,status);
                        }

                        Toast.makeText(PembayaranActivity.this, message,Toast.LENGTH_LONG).show();
                        if(sessionPembayaran.equals("list")) {
                            Intent tagihan = new Intent(PembayaranActivity.this, TagihanActivity.class);
                            startActivity(tagihan);
                            finish();
                        }else{
                            Intent home=new Intent(PembayaranActivity.this, HomeActivity.class)
                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(home);
                            finish();
                        }

                    }else{
//                        progressDialog.dismiss();
                        Toast.makeText(PembayaranActivity.this, "Server Bermasalah",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<DataResponse> call, Throwable t) {
                Log.e("Siswa :", "Failed API Pembayaran");
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

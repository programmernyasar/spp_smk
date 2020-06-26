package com.kulodev.smkppi.Rest;


import com.kulodev.smkppi.Model.DataResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @POST("user/login/siswa")
    Call<DataResponse> getLoginSiswa(@Query("keyphone") String apiKey, @Query("nis") String nis, @Query("password") String password);

    @POST("user/login/wali")
    Call<DataResponse> getLoginWali(@Query("keyphone") String apiKey, @Query("email") String email, @Query("password") String password);

    @PUT("user/register/siswa")
    Call<DataResponse> getRegisterSiswa(@Query("keyphone") String apiKey, @Query("nis") String nis, @Query("password") String password);

    @PUT("user/register/wali")
    Call<DataResponse> getRegisterWali(@Query("keyphone") String apiKey, @Query("email") String email, @Query("password") String password);

    @POST("user/forgot/siswa")
    Call<DataResponse> getForgotSiswa(@Query("keyphone") String apiKey, @Query("email") String email);

    @POST("user/forgot/wali")
    Call<DataResponse> getForgotWali(@Query("keyphone") String apiKey, @Query("email") String email);

    @POST("user/gantipassword/siswa/{nis}")
    Call<DataResponse> getChangePasswordSiswa(@Path("nis") String nis, @Query("keyphone") String apiKey, @Query("oldpassword") String oldPassword, @Query("newpassword") String newPassword);

    @POST("user/gantipassword/wali/{email}")
    Call<DataResponse> getChangePasswordWali(@Path("email") String email, @Query("keyphone") String apiKey, @Query("oldpassword") String oldPassword, @Query("newpassword") String newPassword);

    @GET("user/datasiswa/{nis}")
    Call<DataResponse> getDataSiswa(@Path("nis") String nis, @Query("keyphone") String apiKey);

    @GET("user/tahunajaran/siswa")
    Call<DataResponse> getTahunAjaran(@Query("nis") String nis, @Query("keyphone") String apiKey);

    @GET("user/datatagihan/{nis}")
    Call<DataResponse> getTagihan(@Path("nis") String nis, @Query("tahun_ajaran") String tahunAjaran, @Query("keyphone") String apiKey);

    @GET("user/datatagihanterbaru/{nis}")
    Call<DataResponse> getTagihanTerbaru(@Path("nis") String nis, @Query("tahun_ajaran") String tahun_ajaran, @Query("keyphone") String apiKey);

    @GET("user/datapengumuman/siswa")
    Call<DataResponse> getPengumuman(@Query("nis") String nis, @Query("kelas") String kelas, @Query("keyphone") String apiKey);

    @GET("user/datapengumumanterbaru/{nis}")
    Call<DataResponse> getPengumumanTerbaru(@Path("nis") String nis, @Query("kelas") String kelas, @Query("tahun_ajaran") String tahun_ajaran, @Query("keyphone") String apiKey);

    @Multipart
    @POST("user/updatesiswa/{nis}")
    Call<DataResponse> updateSiswaImage(@Path("nis") String nis, @Part("image\"; filename=\"myfile.jpg\" ") RequestBody file, @Query("nm_siswa") String nama_siswa,
                                   @Query("jk_siswa") String jk_kelamin, @Query("tgl_lahir") String tgl_lahir, @Query("tmpt_lahir") String tmpt_lahir,
                                   @Query("alamat") String alamat, @Query("no_hp") String no_hp, @Query("mail_siswa") String mail_siswa,
                                   @Query("nm_bapak") String nm_bapak, @Query("nm_ibu") String nm_ibu, @Query("no_hp_wali") String no_hp_wali,
                                   @Query("alamat_wali") String alamat_wali,@Query("keyphone") String apiKey);

    @POST("user/updatesiswa/{nis}")
    Call<DataResponse> updateSiswa(@Path("nis") String nis, @Query("nm_siswa") String nama_siswa,
                                   @Query("jk_siswa") String jk_kelamin, @Query("tgl_lahir") String tgl_lahir, @Query("tmpt_lahir") String tmpt_lahir,
                                   @Query("alamat") String alamat, @Query("no_hp") String no_hp, @Query("mail_siswa") String mail_siswa,
                                   @Query("nm_bapak") String nm_bapak, @Query("nm_ibu") String nm_ibu, @Query("no_hp_wali") String no_hp_wali,
                                   @Query("alamat_wali") String alamat_wali,@Query("keyphone") String apiKey);


    @Multipart
    @POST("user/datatagihan/bayar/{nis}")
    Call <DataResponse> bayarTagihan(@Path("nis") String nis, @Query("id_tagihan") String id_tagihan, @Part("image\"; filename=\"myfile.jpg\" ") RequestBody file, @Query("keyphone") String apiKey);

}

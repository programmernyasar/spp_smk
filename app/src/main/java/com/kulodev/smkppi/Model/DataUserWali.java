package com.kulodev.smkppi.Model;

import com.google.gson.annotations.SerializedName;


/**
 * Created by Muhammad Tio Laksono
 */
public class DataUserWali {

    // User Wali
    @SerializedName("id_usr_wali")
    private String idUser;
    @SerializedName("nis_siswa")
    private String nisSiswa;
    @SerializedName("nm_wali")
    private String namaWali;
    @SerializedName("mail_usr_wali")
    private String mailUserWali;
    @SerializedName("kelas")
    private String kelas;
    @SerializedName("tahun_ajaran")
    private String tahunAjaran;
    @SerializedName("token")
    private String tokenWali;


    public DataUserWali(String idUser, String nisSiswa, String namaWali, String mailUserSiswa, String kelas,
                        String tahunAjaran, String tokenSiswa) {
        this.idUser = idUser;
        this.nisSiswa = nisSiswa;
        this.namaWali = namaWali;
        this.mailUserWali = mailUserWali;
        this.kelas = kelas;
        this.tahunAjaran = tahunAjaran;
        this.tokenWali = tokenWali;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getNisSiswa() {
        return nisSiswa;
    }

    public void setNisSiswa(String nisSiswa) {
        this.nisSiswa = nisSiswa;
    }

    public String getNamaWali() {
        return namaWali;
    }

    public void setNamaWali(String namaWali) {
        this.namaWali = namaWali;
    }

    public String getMailUserWali() {
        return mailUserWali;
    }

    public void setMailUserWali(String mailUserWali) {
        this.mailUserWali = mailUserWali;
    }


    public String getTokenWali() {
        return tokenWali;
    }

    public void setTokenWali(String tokenWali) {
        this.tokenWali = tokenWali;
    }


    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public String getTahunAjaran() {
        return tahunAjaran;
    }

    public void setTahunAjaran(String tahunAjaran) {
        this.tahunAjaran = tahunAjaran;
    }


}

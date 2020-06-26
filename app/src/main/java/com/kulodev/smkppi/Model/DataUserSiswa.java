package com.kulodev.smkppi.Model;

import com.google.gson.annotations.SerializedName;


/**
 * Created by Muhammad Tio Laksono
 */
public class DataUserSiswa {

    // User Siswa
    @SerializedName("id_usr_siswa")
    private String idUser;
    @SerializedName("nis_siswa")
    private String nisSiswa;
    @SerializedName("nm_siswa")
    private String namaSiswa;
    @SerializedName("mail_usr_siswa")
    private String mailUserSiswa;
    @SerializedName("kelas")
    private String kelas;
    @SerializedName("tahun_ajaran")
    private String tahunAjaran;
    @SerializedName("token")
    private String tokenSiswa;

    public DataUserSiswa(String idUser, String nisSiswa, String namaSiswa, String mailUserSiswa, String kelas,
                         String tahunAjaran, String tokenSiswa) {
        this.idUser = idUser;
        this.nisSiswa = nisSiswa;
        this.namaSiswa = namaSiswa;
        this.mailUserSiswa = mailUserSiswa;
        this.kelas = kelas;
        this.tahunAjaran = tahunAjaran;
        this.tokenSiswa = tokenSiswa;
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

    public String getNamaSiswa() {
        return namaSiswa;
    }

    public void setNamaSiswa(String namaSiswa) {
        this.namaSiswa = namaSiswa;
    }

    public String getMailUserSiswa() {
        return mailUserSiswa;
    }

    public void setMailUserSiswa(String mailUserSiswa) {
        this.mailUserSiswa = mailUserSiswa;
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

    public String getTokenSiswa() {
        return tokenSiswa;
    }

    public void setTokenSiswa(String tokenSiswa) {
        this.tokenSiswa = tokenSiswa;
    }

}

package com.kulodev.smkppi.Model;

import com.google.gson.annotations.SerializedName;


/**
 * Created by Muhammad Tio Laksono
 */
public class DataSiswa {

    // User Siswa
    @SerializedName("id_siswa")
    private String idSiswa;
    @SerializedName("nis_siswa")
    private String nis;
    @SerializedName("nm_siswa")
    private String nama;
    @SerializedName("jk_siswa")
    private String jenisKelamin;
    @SerializedName("tgl_lahir")
    private String tanggalLahir;
    @SerializedName("tmpt_lahir")
    private String tempatLahir;
    @SerializedName("alamat")
    private String alamat;
    @SerializedName("no_hp")
    private String noHp;
    @SerializedName("mail_siswa")
    private String email;
    @SerializedName("kelas")
    private String kelas;
    @SerializedName("jurusan")
    private String jurusan;
    @SerializedName("tahun_ajaran")
    private String tahunAjaran;
    @SerializedName("nm_ibu")
    private String ibuSiswa;
    @SerializedName("nm_bapak")
    private String bapakSiswa;
    @SerializedName("no_hp_wali")
    private String noHpWali;
    @SerializedName("mail_wali")
    private String emailWali;
    @SerializedName("alamat_wali")
    private String alamatWali;
    @SerializedName("foto_siswa")
    private String fotoSiswa;


    public DataSiswa(String idSiswa, String nis, String nama, String jenisKelamin,
                     String tanggalLahir, String tempatLahir, String alamat, String noHp,
                     String email, String kelas, String jurusan, String tahunAjaran,
                     String ibuSiswa, String bapakSiswa, String noHpWali, String emailWali,
                     String alamatWali, String fotoSiswa) {

                    this.idSiswa = idSiswa;
                    this.nis = nis;
                    this.nama = nama;
                    this.jenisKelamin = jenisKelamin;
                    this.tanggalLahir = tanggalLahir;
                    this.tempatLahir = tempatLahir;
                    this.alamat = alamat;
                    this.noHp = noHp;
                    this.email = email;
                    this.kelas = kelas;
                    this.jurusan = jurusan;
                    this.tahunAjaran = tahunAjaran;
                    this.ibuSiswa = ibuSiswa;
                    this.bapakSiswa = bapakSiswa;
                    this.noHpWali = noHpWali;
                    this.emailWali = emailWali;
                    this.alamatWali = alamatWali;
                    this.fotoSiswa = fotoSiswa;
    }

    public String getIdSiswa() {
        return idSiswa;
    }

    public void setIdSiswa(String idSiswa) {
        this.idSiswa = idSiswa;
    }

    public String getNis() {
        return nis;
    }

    public void setNis(String nis) {
        this.nis = nis;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(String tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public String getTempatLahir() {
        return tempatLahir;
    }

    public void setTempatLahir(String tempatLahir) {
        this.tempatLahir = tempatLahir;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public String getJurusan() {
        return jurusan;
    }

    public void setJurusan(String jurusan) {
        this.jurusan = jurusan;
    }

    public String getTahunAjaran() {
        return tahunAjaran;
    }

    public void setTahunAjaran(String tahunAjaran) {
        this.tahunAjaran = tahunAjaran;
    }

    public String getIbuSiswa() {
        return ibuSiswa;
    }

    public void setIbuSiswa(String ibuSiswa) {
        this.ibuSiswa = ibuSiswa;
    }

    public String getBapakSiswa() {
        return bapakSiswa;
    }

    public void setBapakSiswa(String bapakSiswa) {
        this.bapakSiswa = bapakSiswa;
    }

    public String getNoHpWali() {
        return noHpWali;
    }

    public void setNoHpWali(String noHpWali) {
        this.noHpWali = noHpWali;
    }

    public String getEmailWali() {
        return emailWali;
    }

    public void setEmailWali(String emailWali) {
        this.emailWali = emailWali;
    }

    public String getAlamatWali() {
        return alamatWali;
    }

    public void setAlamatWali(String alamatWali) {
        this.alamatWali = alamatWali;
    }

    public String getFotoSiswa() {
        return fotoSiswa;
    }

    public void setFotoSiswa(String fotoSiswa) {
        this.fotoSiswa = fotoSiswa;
    }
}

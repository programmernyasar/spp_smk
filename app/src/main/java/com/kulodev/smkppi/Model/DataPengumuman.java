package com.kulodev.smkppi.Model;

import com.google.gson.annotations.SerializedName;


/**
 * Created by Muhammad Tio Laksono
 */
public class DataPengumuman {

    @SerializedName("id_pengumuman")
    private String idPengumuman;
    @SerializedName("judul")
    private String judul;
    @SerializedName("isi")
    private String isi;
    @SerializedName("tujuan")
    private String tujuan;
    @SerializedName("bulan")
    private String bulan;
    @SerializedName("date_update")
    private String dateUpdate;

    public DataPengumuman(String idPengumuman, String judul, String isi, String tujuan, String bulan, String dateUpdate) {
        this.idPengumuman = idPengumuman;
        this.judul = judul;
        this.isi = isi;
        this.tujuan = tujuan;
        this.bulan = bulan;
        this.dateUpdate = dateUpdate;
    }

    public String getIdPengumuman() {
        return idPengumuman;
    }

    public void setIdPengumuman(String idPengumuman) {
        this.idPengumuman = idPengumuman;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getIsi() {
        return isi;
    }

    public void setIsi(String isi) {
        this.isi = isi;
    }

    public String getTujuan() {
        return tujuan;
    }

    public void setTujuan(String tujuan) {
        this.tujuan = tujuan;
    }

    public String getBulan() {
        return bulan;
    }

    public void setBulan(String bulan) {
        this.bulan = bulan;
    }

    public String getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(String dateUpdate) {
        this.dateUpdate = dateUpdate;
    }
}

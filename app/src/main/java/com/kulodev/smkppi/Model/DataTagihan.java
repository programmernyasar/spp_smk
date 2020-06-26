package com.kulodev.smkppi.Model;

import com.google.gson.annotations.SerializedName;


/**
 * Created by Muhammad Tio Laksono
 */
public class DataTagihan {

    // User Siswa
    @SerializedName("id_tagihan")
    private String idTagihan;
    @SerializedName("bulan")
    private String bulan;
    @SerializedName("catatan")
    private String catatan;
    @SerializedName("jumlah")
    private String jumlah;
    @SerializedName("tahun_ajaran")
    private String tahunAjaran;
    @SerializedName("status")
    private String status;
    @SerializedName("jenis_pembayaran")
    private String jenisPembayaran;
    @SerializedName("date_update")
    private String dateUpdate;

    public DataTagihan(String idTagihan, String bulan, String catatan, String jumlah, String tahunAjaran, String status,
                       String jenisPembayaran, String dateUpdate) {
        this.idTagihan = idTagihan;
        this.bulan = bulan;
        this.catatan = catatan;
        this.jumlah = jumlah;
        this.tahunAjaran = tahunAjaran;
        this.status = status;
        this.jenisPembayaran = jenisPembayaran;
        this.dateUpdate = dateUpdate;
    }

    public String getIdTagihan() {
        return idTagihan;
    }

    public void setIdTagihan(String idTagihan) {
        this.idTagihan = idTagihan;
    }

    public String getBulan() {
        return bulan;
    }

    public void setBulan(String bulan) {
        this.bulan = bulan;
    }

    public String getCatatan() {
        return catatan;
    }

    public void setCatatan(String catatan) {
        this.catatan = catatan;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getTahunAjaran() {
        return tahunAjaran;
    }

    public void setTahunAjaran(String tahunAjaran) {
        this.tahunAjaran = tahunAjaran;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getJenisPembayaran() {
        return jenisPembayaran;
    }

    public void setJenisPembayaran(String jenisPembayaran) {
        this.jenisPembayaran = jenisPembayaran;
    }

    public String getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(String dateUpdate) {
        this.dateUpdate = dateUpdate;
    }
}

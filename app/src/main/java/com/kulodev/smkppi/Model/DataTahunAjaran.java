package com.kulodev.smkppi.Model;

import com.google.gson.annotations.SerializedName;


/**
 * Created by Muhammad Tio Laksono
 */
public class DataTahunAjaran {

    @SerializedName("id")
    private String idTahunAjaran;
    @SerializedName("tahun_ajaran")
    private String tahunAjaran;


    public DataTahunAjaran(String idTahunAjaran, String tahunAjaran) {
        this.idTahunAjaran = idTahunAjaran;
        this.tahunAjaran = tahunAjaran;
    }

    public String getIdTahunAjaran() {
        return idTahunAjaran;
    }

    public void setIdTahunAjaran(String idTahunAjaran) {
        this.idTahunAjaran = idTahunAjaran;
    }

    public String getTahunAjaran() {
        return tahunAjaran;
    }

    public void setTahunAjaran(String tahunAjaran) {
        this.tahunAjaran = tahunAjaran;
    }
}

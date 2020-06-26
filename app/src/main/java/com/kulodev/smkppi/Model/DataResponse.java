package com.kulodev.smkppi.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataResponse {

    @SerializedName("user_siswa")
    private List<DataUserSiswa> userSiswa;
    @SerializedName("user_wali")
    private List<DataUserWali> userWali;
    @SerializedName("siswa")
    private List<DataSiswa> dataSiswa;
    @SerializedName("tagihan")
    private List<DataTagihan> dataTagihan;
    @SerializedName("pengumuman")
    private List<DataPengumuman> dataPengumuman;
    @SerializedName("tahunajaran")
    private List<DataTahunAjaran> dataTahunAjaran;
    @SerializedName("status")
    private String status;
    @SerializedName("message")
    private String message;


    public List<DataUserSiswa> getLoginSiswa() {
        return userSiswa;
    }

    public void resultLoginSiswa(List<DataUserSiswa> results) {
        this.userSiswa = results;
    }

    public List<DataUserWali> getLoginWali() {
        return userWali;
    }

    public void resultLoginWali(List<DataUserWali> results) {
        this.userWali = results;
    }

    public List<DataSiswa> getDataSiswa() {
        return dataSiswa;
    }

    public void resultDataSiswa(List<DataSiswa> results) {
        this.dataSiswa = results;
    }

    public List<DataTagihan> getTagihan() {
        return dataTagihan;
    }

    public void resultTagihan(List<DataTagihan> results) {
        this.dataTagihan = results;
    }

    public List<DataPengumuman> getPengumuman() {
        return dataPengumuman;
    }

    public void resultPengumuman(List<DataPengumuman> results) {
        this.dataPengumuman = results;
    }

    public List<DataTagihan> getTagihanTerbaru() {
        return dataTagihan;
    }

    public void resultTagihanTerbaru(List<DataTagihan> results) {
        this.dataTagihan = results;
    }

    public List<DataPengumuman> getPengumumanTerbaru() {
        return dataPengumuman;
    }

    public void resultPengumumanTerbaru(List<DataPengumuman> results) {
        this.dataPengumuman = results;
    }

    public List<DataTahunAjaran> getDataTahunAjaran() {
        return dataTahunAjaran;
    }

    public void setDataTahunAjaran(List<DataTahunAjaran> dataTahunAjaran) {
        this.dataTahunAjaran = dataTahunAjaran;
    }

    public String getStatus(){
        return status;
    }

    public void setStatus(String status){
        this.status=status;
    }

    public String getMessage(){
        return message;
    }

    public void setMessage(String message){
        this.message=message;
    }



}

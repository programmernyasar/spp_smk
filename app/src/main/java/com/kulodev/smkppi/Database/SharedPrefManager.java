package com.kulodev.smkppi.Database;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {

    public static final String SP_SMKPPI_APP = "spSmkppiApp";

    // Data User
    public static final String SP_NIS = "spNis";
    public static final String SP_NAMA = "spNama";
    public static final String SP_EMAIL = "spEmail";
    public static final String SP_TOKEN = "spToken";
    public static final String SP_SESSION = "spSession";
    public static final String SP_PASSWORD = "spPassword";

    // Kebutuhan Siswa
    public static final String SP_KELAS = "spKelas";
    public static final String SP_TAHUN_AJARAN = "spTahunAjaran";

    // Tagihan Terbaru
    public static final String SP_ADA_TAGIHAN = "spAdaTagihan";
    public static final String SP_ID_TAGIHAN = "spIdTagihan";
    public static final String SP_BULAN_TAGIHAN = "spBulanTagihan";
    public static final String SP_CATATAN_TAGIHAN = "spCatatanTagihan";
    public static final String SP_JUMLAH_TAGIHAN = "spJumlahTagihan";
    public static final String SP_TAHUN_AJARAN_TAGIHAN = "spTaTagihan";
    public static final String SP_STATUS_TAGIHAN = "spStatusTagihan";
    public static final String SP_PEMBAYARAN_TAGIHAN = "spPembayaranTagihan";
    public static final String SP_DATE_TAGIHAN = "spDateTagihan";

    // Pengumuman Terbaru
    public static final String SP_ADA_PENGUMUMAN= "spAdaPengumuman";
    public static final String SP_ID_PENGUMUMAN = "spIdPengumuman";
    public static final String SP_JUDUL_PENGUMUMAN = "spJudulPengumuman";
    public static final String SP_ISI_PENGUMUMAN = "spIsiPengumuman";
    public static final String SP_TUJUAN_PENGUMUMAN = "spTujuanPengumuman";
    public static final String SP_BULAN_PENGUMUMAN = "spBulanPengumuman";
    public static final String SP_DATE_PENGUMUMAN = "spDatePengumuman";

    // Cek Login
    public static final String SP_SUDAH_LOGIN = "spSudahLogin";

    SharedPreferences sp;
    SharedPreferences.Editor spEditor;

    public SharedPrefManager(Context context){
        sp = context.getSharedPreferences(SP_SMKPPI_APP, Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }

    public void saveSPString(String keySP, String value){
        spEditor.putString(keySP, value);
        spEditor.commit();
    }

    public void saveSPInt(String keySP, int value){
        spEditor.putInt(keySP, value);
        spEditor.commit();
    }

    public void saveSPBoolean(String keySP, boolean value){
        spEditor.putBoolean(keySP, value);
        spEditor.commit();
    }

    // Data User
    public String getSPNis(){
        return sp.getString(SP_NIS, "");
    }

    public String getSPNama(){
        return sp.getString(SP_NAMA, "");
    }

    public String getSPEmail(){
        return sp.getString(SP_EMAIL, "");
    }

    public String getSPToken(){
        return sp.getString(SP_TOKEN, "");
    }

    public String getSPSession(){
        return sp.getString(SP_SESSION, "");
    }

    public String getSPPassword(){
        return sp.getString(SP_PASSWORD, "");
    }

    public String getSPKelas(){
        return sp.getString(SP_KELAS, "");
    }

    public String getSPTahunAjaran(){
        return sp.getString(SP_TAHUN_AJARAN, "");
    }

    // Tagihan
    public Boolean getSPAdaTagihan(){
        return sp.getBoolean(SP_ADA_TAGIHAN, false);
    }

    public String getSPIdTagihan(){
        return sp.getString(SP_ID_TAGIHAN, "");
    }

    public String getSPBulanTagihan(){
        return sp.getString(SP_BULAN_TAGIHAN, "");
    }

    public String getSPCatatanTagihan(){
        return sp.getString(SP_CATATAN_TAGIHAN, "");
    }

    public String getSPJumlahTagihan(){
        return sp.getString(SP_JUMLAH_TAGIHAN, "");
    }

    public String getSPTahunAjaranTagihan(){
        return sp.getString(SP_TAHUN_AJARAN_TAGIHAN, "");
    }

    public String getSPStatusTagihan(){
        return sp.getString(SP_STATUS_TAGIHAN, "");
    }

    public String getSPPembayaranTagihan(){
        return sp.getString(SP_PEMBAYARAN_TAGIHAN, "Belum Lunas");
    }

    public String getDateTagihan(){
        return sp.getString(SP_DATE_TAGIHAN, "");
    }

    // Pengumuman
    public Boolean getSPAdaPengumuman(){
        return sp.getBoolean(SP_ADA_PENGUMUMAN, false);
    }

    public String getSPIdPengumuman(){
        return sp.getString(SP_ID_PENGUMUMAN, "");
    }

    public String getSPJudulPengumuman(){
        return sp.getString(SP_JUDUL_PENGUMUMAN, "");
    }

    public String getSPIsiPengumuman(){
        return sp.getString(SP_ISI_PENGUMUMAN, "");
    }

    public String getSPTujuanPengumuman(){
        return sp.getString(SP_TUJUAN_PENGUMUMAN, "");
    }

    public String getSPBulanPengumuman(){
        return sp.getString(SP_BULAN_PENGUMUMAN, "");
    }

    public String getSPDatePengumuman(){
        return sp.getString(SP_DATE_PENGUMUMAN, "");
    }

    public Boolean getSPSudahLogin(){
        return sp.getBoolean(SP_SUDAH_LOGIN, false);
    }

}

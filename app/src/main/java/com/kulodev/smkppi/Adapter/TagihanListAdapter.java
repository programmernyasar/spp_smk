package com.kulodev.smkppi.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kulodev.smkppi.Activity.PembayaranActivity;
import com.kulodev.smkppi.Model.DataTagihan;
import com.kulodev.smkppi.R;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;


public class TagihanListAdapter extends RecyclerView.Adapter<TagihanListAdapter.CategoryViewHolder> {

    private List<DataTagihan> listTagihan = new ArrayList<>();
    private Context context;

    public void setTagihanData(Context context,List<DataTagihan> items) {
        this.context=context;
        listTagihan.clear();
        listTagihan.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public TagihanListAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemRow = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_tagihan, viewGroup, false);
        return new CategoryViewHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(@NonNull final TagihanListAdapter.CategoryViewHolder categoryViewHolder, int position) {
        String idTagihan=listTagihan.get(position).getIdTagihan();
        String bulanTagihan= listTagihan.get(position).getBulan();
        String catatanTagihan= listTagihan.get(position).getCatatan();
        String pembayaranTagihan= listTagihan.get(position).getJenisPembayaran();
        String jumlahTagihan= listTagihan.get(position).getJumlah();
        String tanggalTagihan= listTagihan.get(position).getDateUpdate();
        String statusTagihan=listTagihan.get(position).getStatus();
        String sessionPembayaran="list";

        categoryViewHolder.bulan_tagihan.setText(bulanTagihan);
        categoryViewHolder.catatan_tagihan.setText(catatanTagihan);
        categoryViewHolder.jumlah_tagihan.setText(kurensiIndonesia(jumlahTagihan));
        categoryViewHolder.tanggal_tagihan.setText(tanggalTagihan);

        if(statusTagihan.equals("Lunas")){
            categoryViewHolder.bayar_tagihan.setVisibility(View.GONE);
            categoryViewHolder.pembayaran_tagihan.setText(statusTagihan+" ("+pembayaranTagihan+")");
            categoryViewHolder.pembayaran_tagihan.setBackgroundColor(Color.parseColor("#2196F3"));

        }else if(statusTagihan.equals("Dispensasi")){

            categoryViewHolder.pembayaran_tagihan.setText(statusTagihan);
            categoryViewHolder.pembayaran_tagihan.setBackgroundColor(Color.parseColor("#FF9800"));

        }else if(statusTagihan.equals("Menunggu Konfirmasi")){
            categoryViewHolder.bayar_tagihan.setVisibility(View.GONE);
            categoryViewHolder.pembayaran_tagihan.setText(statusTagihan);
            categoryViewHolder.pembayaran_tagihan.setBackgroundColor(Color.parseColor("#2196F3"));

        }else if(statusTagihan.equals("Pembayaran Gagal")){
            categoryViewHolder.pembayaran_tagihan.setText(statusTagihan);
            categoryViewHolder.pembayaran_tagihan.setBackgroundColor(Color.parseColor("#F44336"));

        }else{
            categoryViewHolder.pembayaran_tagihan.setText(statusTagihan);
            categoryViewHolder.pembayaran_tagihan.setBackgroundColor(Color.parseColor("#F44336"));
        }

        categoryViewHolder.bayar_tagihan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("id_tagihan", idTagihan);
                bundle.putString("jumlah_tagihan", jumlahTagihan);
                bundle.putString("catatan_tagihan", catatanTagihan);
                bundle.putString("session_pembayaran",sessionPembayaran);
                Intent pembayaran=new Intent(context, PembayaranActivity.class);
                pembayaran.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                pembayaran.putExtras(bundle);
                context.startActivity(pembayaran);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listTagihan.size();
    }


    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        private TextView bulan_tagihan;
        private TextView catatan_tagihan;
        private TextView pembayaran_tagihan;
        private TextView jumlah_tagihan;
        private TextView tanggal_tagihan;
        private Button bayar_tagihan;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            bulan_tagihan= itemView.findViewById(R.id.tv_bulan_tagihan);
            catatan_tagihan=itemView.findViewById(R.id.tv_catatan_tagihan);
            pembayaran_tagihan=itemView.findViewById(R.id.tv_pembayaran_tagihan);
            jumlah_tagihan=itemView.findViewById(R.id.tv_jumlah_tagihan);
            tanggal_tagihan=itemView.findViewById(R.id.tv_tanggal_tagihan);
            bayar_tagihan=itemView.findViewById(R.id.btn_bayar_tagihan);

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


}

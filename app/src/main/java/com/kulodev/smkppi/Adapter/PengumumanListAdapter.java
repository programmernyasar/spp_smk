package com.kulodev.smkppi.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kulodev.smkppi.Activity.DetailPengumumanActivity;
import com.kulodev.smkppi.Database.SharedPrefManager;
import com.kulodev.smkppi.Model.DataPengumuman;
import com.kulodev.smkppi.R;

import java.util.ArrayList;
import java.util.List;


public class PengumumanListAdapter extends RecyclerView.Adapter<PengumumanListAdapter.CategoryViewHolder> {

    private List<DataPengumuman> listPengumuman= new ArrayList<>();
    private Context context;
    private SharedPrefManager sharedPrefManager;

    public void setPengumumanData(Context context,List<DataPengumuman> items) {
        this.context=context;
        listPengumuman.clear();
        listPengumuman.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public PengumumanListAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemRow = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_pengumuman, viewGroup, false);
        return new CategoryViewHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(@NonNull final PengumumanListAdapter.CategoryViewHolder categoryViewHolder, int position) {
        String idPengumuman=listPengumuman.get(position).getIdPengumuman();
        String bulanPengumuman=listPengumuman.get(position).getBulan();
        String judulPengumuman=listPengumuman.get(position).getJudul();
        String isiPengumuman=listPengumuman.get(position).getIsi();
        String tujuanPengumuman=listPengumuman.get(position).getTujuan();
        String tanggalPengumuman=listPengumuman.get(position).getDateUpdate();
        String sessionPengumuman="list";

        sharedPrefManager = new SharedPrefManager(context);
        String nis=sharedPrefManager.getSPNis();

        categoryViewHolder.bulan_pengumuman.setText(bulanPengumuman);
        categoryViewHolder.judul_pengumuman.setText(judulPengumuman);

        if(nis.equals(tujuanPengumuman)){
            categoryViewHolder.tujuan_pengumuman.setText("Anda");
        }else{
            categoryViewHolder.tujuan_pengumuman.setText(tujuanPengumuman);
        }

        categoryViewHolder.tanggal_pengumuman.setText(tanggalPengumuman);

        categoryViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("session_pengumuman", sessionPengumuman);
                bundle.putString("judul_pengumuman", judulPengumuman);
                bundle.putString("tujuan_pengumuman", tujuanPengumuman);
                bundle.putString("tanggal_pengumuman", tanggalPengumuman);
                bundle.putString("isi_pengumuman", isiPengumuman);
                Intent detailPengumuman=new Intent(context, DetailPengumumanActivity.class);
                detailPengumuman.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                detailPengumuman.putExtras(bundle);
                context.startActivity(detailPengumuman);
            }
        });


    }

    @Override
    public int getItemCount() {
        return listPengumuman.size();
    }


    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        private TextView bulan_pengumuman;
        private TextView judul_pengumuman;
        private TextView tujuan_pengumuman;
        private TextView tanggal_pengumuman;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            bulan_pengumuman= itemView.findViewById(R.id.tv_bulan_pengumuman);
            judul_pengumuman=itemView.findViewById(R.id.tv_judul_pengumuman);
            tujuan_pengumuman=itemView.findViewById(R.id.tv_tujuan_pengumuman);
            tanggal_pengumuman=itemView.findViewById(R.id.tv_tanggal_pengumuman);

        }
    }


}

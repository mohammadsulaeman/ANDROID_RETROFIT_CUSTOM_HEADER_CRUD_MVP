package com.example.biodatamvp.model;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.biodatamvp.R;
import com.example.biodatamvp.constants.Constant;
import com.example.biodatamvp.utils.PicassoTrustAll;
import com.example.biodatamvp.utils.api.ServiceGenerator;
import com.example.biodatamvp.utils.api.service.BiodataService;
import com.example.biodatamvp.utils.json.ResponseJson;
import com.example.biodatamvp.view.DetailsActivity;
import com.example.biodatamvp.view.UpdateDeleteActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BiodataAdapter extends RecyclerView.Adapter<BiodataAdapter.ItemRowHolder> {

    private Context context;
    private List<Biodata> biodataList;
    private int rowLayout;

    public BiodataAdapter(Context context, List<Biodata> biodataList, int rowLayout) {
        this.context = context;
        this.biodataList = biodataList;
        this.rowLayout = rowLayout;
    }

    @NonNull
    @Override
    public ItemRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout,parent,false);
        return new ItemRowHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemRowHolder holder, int position) {
        final Biodata biodata = biodataList.get(position);
        holder.nim.setText(biodata.getNim());
        holder.nama.setText(biodata.getFullName());
        if (!biodata.getPhoto().isEmpty()){
            Picasso.get().load(Constant.IMAGES_BIODATA+biodata.getPhoto()).into(holder.images);
            System.out.println("url Gambar : "+Constant.IMAGES_BIODATA+biodata.getPhoto());
        }
        holder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent details = new Intent(context.getApplicationContext(), DetailsActivity.class);
                details.putExtra(DetailsActivity.ID_BIODATA,biodata.getId());
                details.putExtra(DetailsActivity.NIM_BIODATA,biodata.getNim());
                details.putExtra(DetailsActivity.NAMA_BIODATA,biodata.getFullName());
                details.putExtra(DetailsActivity.PHONE_BIODATA,biodata.getPhone());
                details.putExtra(DetailsActivity.PHOTO_BIODATA,biodata.getPhoto());
                details.putExtra(DetailsActivity.DOB_BIODATA,biodata.getDob());
                details.putExtra(DetailsActivity.EMAIL_BIODATA,biodata.getEmail());
                details.putExtra(DetailsActivity.ALAMAT_BIODATA,biodata.getAddress());
                details.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(details);
            }
        });
        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent update = new Intent(context.getApplicationContext(), UpdateDeleteActivity.class);
                update.putExtra(UpdateDeleteActivity.ID_BIODATA,biodata.getId());
                update.putExtra(UpdateDeleteActivity.NIM_BIODATA,biodata.getNim());
                update.putExtra(UpdateDeleteActivity.NAMA_BIODATA,biodata.getFullName());
                update.putExtra(UpdateDeleteActivity.PHONE_BIODATA,biodata.getPhone());
                update.putExtra(UpdateDeleteActivity.PHOTO_BIODATA,biodata.getPhoto());
                update.putExtra(UpdateDeleteActivity.DOB_BIODATA,biodata.getDob());
                update.putExtra(UpdateDeleteActivity.EMAIL_BIODATA,biodata.getEmail());
                update.putExtra(UpdateDeleteActivity.ALAMAT_BIODATA,biodata.getAddress());
                update.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(update);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != biodataList ? biodataList.size() : 0);
    }

    class ItemRowHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        CircleImageView images;
        TextView nim;
        TextView nama;
        Button details,update;
         public ItemRowHolder(@NonNull View itemView) {
             super(itemView);
             cardView = itemView.findViewById(R.id.card_item_klik);
             images = itemView.findViewById(R.id.item_images_data);
             nim = itemView.findViewById(R.id.item_nim_data);
             nama = itemView.findViewById(R.id.item_nama_data);
             details = itemView.findViewById(R.id.btn_detail_item);
             update = itemView.findViewById(R.id.btn_upddel_item);
         }
     }
}

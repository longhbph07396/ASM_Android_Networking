package com.hblong.assigment.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hblong.assigment.R;
import com.hblong.assigment.activity.HomeActivity;
import com.hblong.assigment.fragment.GallerieFragment;
import com.hblong.assigment.model.GetCategory;
import com.hblong.assigment.model.ImageFavourite;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ImageHolder> {
    private Context context;
    private List<GetCategory.Galleries.Gallery> gallerys;

    public CategoryAdapter(Context context, List<GetCategory.Galleries.Gallery> gallerys) {
        this.context = context;
        this.gallerys = gallerys;
    }

    @NonNull
    @Override
    public CategoryAdapter.ImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_adapter_category, parent, false);
        return new ImageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CategoryAdapter.ImageHolder holder, int position) {
        Glide.with(context).load(gallerys.get(position).primary_photo_extras.url_m).into(holder.imgvAvatar);
        holder.tvTitle.setText(gallerys.get(position).title._content);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((FragmentActivity) view.getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new GallerieFragment(gallerys.get(position).gallery_id)).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return gallerys.size();
    }

    public class ImageHolder extends RecyclerView.ViewHolder {
        private ImageView imgvAvatar;
        private TextView tvTitle;

        public ImageHolder(@NonNull View itemView) {
            super(itemView);

            imgvAvatar = itemView.findViewById(R.id.imgv_avatar);
            tvTitle = itemView.findViewById(R.id.tv_title);

        }
    }
}

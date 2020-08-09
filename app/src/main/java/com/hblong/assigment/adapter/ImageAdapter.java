package com.hblong.assigment.adapter;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
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
import com.hblong.assigment.fragment.FavouriteFragment;
import com.hblong.assigment.fragment.GallerieFragment;
import com.hblong.assigment.fragment.ImageDetailFragment;
import com.hblong.assigment.model.GetListImageCallerie;
import com.hblong.assigment.model.ImageFavourite;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageHolder> {
    private int fragmentCode;
    private Context context;
    private List<ImageFavourite> images;
    private List<GetListImageCallerie.Photos.PhoTo> phoTos;

    public ImageAdapter(Context context, List<ImageFavourite> images, int code) {
        this.context = context;
        this.images = images;
        fragmentCode = code;
    }

    public ImageAdapter(int fragmentCode, Context context, List<GetListImageCallerie.Photos.PhoTo> phoTos) {
        this.fragmentCode = fragmentCode;
        this.context = context;
        this.phoTos = phoTos;
    }

    @NonNull
    @Override
    public ImageAdapter.ImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_image, parent, false);
        return new ImageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ImageAdapter.ImageHolder holder, int position) {
        if (fragmentCode == FavouriteFragment.FRAGMENT_CODE) {
            holder.tvViewer.setText(images.get(position).views);
            holder.imageView.setImageResource(R.drawable.icon_view);
            if (images.get(position).url_l!=null){
                Glide.with(context).asBitmap().load(images.get(position).url_l).placeholder(R.drawable.place).into(holder.imgImage);
            }else {
                Glide.with(context).asBitmap().load(images.get(position).url_z).placeholder(R.drawable.place).into(holder.imgImage);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HomeActivity.imageFavourites = images;
                    HomeActivity.imageCur = position;
                    ((FragmentActivity) v.getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new ImageDetailFragment(fragmentCode)).commit();
                }
            });
        } else if (fragmentCode == GallerieFragment.FRAGMENT_CODE) {
            holder.tvViewer.setText(phoTos.get(position).views);
            holder.imageView.setImageResource(R.drawable.icon_view);
            if (phoTos.get(position).url_l!=null){
                Glide.with(context).asBitmap().load(phoTos.get(position).url_l).placeholder(R.drawable.place).into(holder.imgImage);
            }else {
                Glide.with(context).asBitmap().load(phoTos.get(position).url_o).placeholder(R.drawable.place).into(holder.imgImage);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HomeActivity.phoTos = phoTos;
                    HomeActivity.imageCur = position;
                    ((FragmentActivity) v.getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new ImageDetailFragment(fragmentCode)).commit();
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        if (fragmentCode == FavouriteFragment.FRAGMENT_CODE) {
            return images.size();
        } else if (fragmentCode == GallerieFragment.FRAGMENT_CODE) {
            return phoTos.size();
        }
        return 0;
    }

    public class ImageHolder extends RecyclerView.ViewHolder {
        private ImageView imgImage, imageView;
        private TextView tvViewer;

        public ImageHolder(@NonNull View itemView) {
            super(itemView);

            imgImage = itemView.findViewById(R.id.img_image);
            imageView = itemView.findViewById(R.id.imageView2);
            tvViewer = itemView.findViewById(R.id.tv_viewer);

        }
    }
}

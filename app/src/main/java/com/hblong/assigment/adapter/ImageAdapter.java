package com.hblong.assigment.adapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.hblong.assigment.R;
import com.hblong.assigment.acsyntack.DownloadImage;
import com.hblong.assigment.acsyntack.LoadImage;
import com.hblong.assigment.activity.DetailActivity;
import com.hblong.assigment.fragment.FavouriteFragment;
import com.hblong.assigment.fragment.GallerieFragment;
import com.hblong.assigment.model.GetListImageCallerie;
import com.hblong.assigment.model.ImageFavourite;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.ExecutionException;

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
            String link = linkEmpty(images.get(position).getLink());
            holder.imgImage.setMinimumWidth((images.get(position).width_m));
            holder.imgImage.setMinimumHeight((images.get(position).height_m));

Log.e("longhb",linkEmpty(images.get(position).getLink())+"");
            if (images.get(position).width_m == 0) {
                images.get(position).width_m = images.get(position).width_n;
                images.get(position).height_m = images.get(position).height_n;
            }
            Picasso.get()
                    .load(link)
                    .resize(images.get(position).width_m, images.get(position).height_m)
                    .into(holder.imgImage);

            holder.tvViewer.setText(images.get(position).views);
            holder.imageView.setImageResource(R.drawable.icon_view);


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("cur", position);
                    intent.putExtra("code", fragmentCode);
                    intent.putExtra("images", (Serializable) images);
                    context.startActivity(intent);
                    ((FragmentActivity) v.getContext()).overridePendingTransition(R.anim.bg_splash_in, R.anim.bg_splash_out);

                }
            });
        } else if (fragmentCode == GallerieFragment.FRAGMENT_CODE) {
            holder.tvViewer.setText(phoTos.get(position).views);
            holder.imageView.setImageResource(R.drawable.icon_view);


            holder.imgImage.setMinimumWidth((phoTos.get(position).width_m));
            holder.imgImage.setMinimumHeight((phoTos.get(position).height_m));

            if (phoTos.get(position).width_m == 0) {
                phoTos.get(position).width_m = phoTos.get(position).width_n;
                phoTos.get(position).height_m = phoTos.get(position).height_n;
            }


            Picasso.get()
                    .load(linkEmpty(phoTos.get(position).getLink()))
                    .resize(phoTos.get(position).width_m, phoTos.get(position).height_m)
                    .into(holder.imgImage);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("cur", position);
                    intent.putExtra("code", fragmentCode);
                    intent.putExtra("photo", (Serializable) phoTos);
                    context.startActivity(intent);
                    ((FragmentActivity) v.getContext()).overridePendingTransition(R.anim.bg_splash_in, R.anim.bg_splash_out);
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

    private String linkEmpty(String[] strings) {
        for (int i = strings.length - 1; i >= 0; i--) {
            if (strings[i] != null) {
                return strings[i];
            }
        }
        return null;
    }


}

package com.hblong.assigment.acsyntack;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoadImage extends AsyncTask<String, Void, Bitmap> {
    private ImageView imageView;
    private int w, h;

    public LoadImage(ImageView imageView, int w, int h) {
        this.imageView = imageView;
        this.w = w;
        this.h = h;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        try {
            URL url = new URL(strings[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        Bitmap resized = Bitmap.createScaledBitmap(bitmap, w, h, true);
        imageView.setImageBitmap(resized);


    }
}

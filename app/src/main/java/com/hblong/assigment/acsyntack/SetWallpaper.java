package com.hblong.assigment.acsyntack;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SetWallpaper extends AsyncTask<String, Void, Bitmap> {
    private Context context;

    public SetWallpaper(Context context) {
        this.context = context;
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
            WallpaperManager m = WallpaperManager.getInstance(context);
            try {
                m.setBitmap(myBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        Toast.makeText(context, "Success!", Toast.LENGTH_LONG).show();

    }
}

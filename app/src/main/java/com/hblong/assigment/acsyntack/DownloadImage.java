package com.hblong.assigment.acsyntack;

import android.app.Notification;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.hblong.assigment.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import static com.hblong.assigment.context.App.CHANNEL_ID;

public class DownloadImage extends AsyncTask<String, Integer, Void> {
    private NotificationManagerCompat managerCompat;
    private Notification notification;
    private RemoteViews collapsedView;

    private Context context;

    public DownloadImage(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        managerCompat = NotificationManagerCompat.from(context);

        collapsedView = new RemoteViews(context.getPackageName(), R.layout.notification_collapsed);

        notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.doaw)
                .setCustomContentView(collapsedView)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .build();
        managerCompat.notify(1, notification);
    }

    @Override
    protected Void doInBackground(String[] strings) {
        URL url = null;
        int i = 0;
        int total = 0;
        try {
            url = new URL(strings[0]);
            URLConnection urlConnection = url.openConnection();
            total = urlConnection.getContentLength();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();

            try {
                File storagePath = Environment.getExternalStorageDirectory();
                File file = new File(storagePath.getAbsolutePath() + "/long-" + System.currentTimeMillis() + ".png");
                if (!file.exists()) {
                    file.createNewFile();
                }
                OutputStream output = new FileOutputStream(file, false);
                try {
                    byte[] buffer = new byte[1024];
                    int bytesRead = 0;
                    while ((bytesRead = input.read(buffer, 0, buffer.length)) >= 0) {
                        output.write(buffer, 0, bytesRead);
                    }
                    i += 1024;
                    publishProgress((int) i *100/ total);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } finally {
                    output.close();
                }
            } finally {
                input.close();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e("longhb", e.getMessage());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.e("longhb", e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("longhb", e.getMessage());
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        collapsedView.setProgressBar(R.id.progressBar2, 100, values[0], true);
        managerCompat.notify(1, notification);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        collapsedView = new RemoteViews(context.getPackageName(), R.layout.ok);

        notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.doaw)
                .setCustomContentView(collapsedView)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .build();
        managerCompat.notify(1, notification);
    }
}
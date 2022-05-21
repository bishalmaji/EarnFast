package com.earnfast.earnfast.cources;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Downback extends AsyncTask<String, String, String> {
    Activity activity;
    public Downback(Activity activity) {
        this.activity=activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Toast.makeText(activity, "started", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected String doInBackground(String... strings) {
        final String vidurl = "https://res.cloudinary.com/dvixcxs52/video/upload/v1652990929/SURAJ_KUMAR_SINGH_4_ts2qjb.mp4";
        downloadfile(vidurl);
        return null;

    }
    private void downloadfile(String vidurl) {

        SimpleDateFormat sd = new SimpleDateFormat("yymmhh");
        String date = sd.format(new Date());
        String name = "111video" + date + ".mp4";

        try {
            String rootDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                    + File.separator + "Video";
            File rootFile = new File(rootDir,name);
            rootFile.mkdir();
            URL url = new URL(vidurl);
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            c.setRequestMethod("GET");
            c.setDoOutput(true);
            c.connect();
            FileOutputStream f = new FileOutputStream(rootFile);
            InputStream in = c.getInputStream();
            byte[] buffer = new byte[1024];
            int len1 = 0;
            while ((len1 = in.read(buffer)) > 0) {
                f.write(buffer, 0, len1);
            }
            f.close();
        } catch (IOException e) {
            Log.d("bkmmmmm", "downloadfile: "+e.getLocalizedMessage());;
        }


    }


}

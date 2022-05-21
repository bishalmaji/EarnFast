package com.earnfast.earnfast.cources;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.MediaRouteButton;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;


import com.earnfast.earnfast.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DemoClasses extends AppCompatActivity {

      VideoView videoView;
    private ProgressBar mainProgressBar;
    private LinearLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_classes);
        String link="https://res.cloudinary.com/dvixcxs52/video/upload/v1652990929/SURAJ_KUMAR_SINGH_4_ts2qjb.mp4";
        videoView=findViewById(R.id.demoVideo);
        Button playBtn=findViewById(R.id.demoPlay);
        mainProgressBar=findViewById(R.id.demoProgress);
        mainLayout=findViewById(R.id.demoLayout);
//        Uri uri=Uri.parse(link);
//        videoView.setVideoURI(uri);
//        MediaController mediaController=new MediaController(DemoClasses.this);
//        mediaController.setAnchorView(videoView);
//        videoView.setMediaController(mediaController);
//         videoView.start();
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Downback DB = new Downback(DemoClasses.this);
//                DB.execute(link);
            }
        });
//     getYoutubeDownloadUrl(link);




    }}
//
//    private void getYoutubeDownloadUrl(String youtubeLink) {
//        new YouTubeExtractor(this) {
//
//            @Override
//            public void onExtractionComplete(SparseArray<YtFile> ytFiles, VideoMeta vMeta) {
//                mainProgressBar.setVisibility(View.GONE);
//
//                if (ytFiles == null) {
//                    // Something went wrong we got no urls. Always check this.
//                    finish();
//                    return;
//                }
//                // Iterate over itags
//                for (int i = 0, itag; i < ytFiles.size(); i++) {
//                    itag = ytFiles.keyAt(i);
//                    // ytFile represents one file with its url and meta data
//                    YtFile ytFile = ytFiles.get(itag);
//
//                    // Just add videos in a decent format => height -1 = audio
//                    if (ytFile.getFormat().getHeight() == -1 || ytFile.getFormat().getHeight() >= 360) {
//                        addButtonToMainLayout(vMeta.getTitle(), ytFile);
//                    }
//                }
//            }
//        }.extract(youtubeLink);
//    }
//
//    private void addButtonToMainLayout(final String videoTitle, final YtFile ytfile) {
//        // Display some buttons and let the user choose the format
//        String btnText = (ytfile.getFormat().getHeight() == -1) ? "Audio " +
//                ytfile.getFormat().getAudioBitrate() + " kbit/s" :
//                ytfile.getFormat().getHeight() + "p";
//        btnText += (ytfile.getFormat().isDashContainer()) ? " dash" : "";
//        Button btn = new Button(this);
//        btn.setText(btnText);
//        btn.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                String filename;
//                if (videoTitle.length() > 55) {
//                    filename = videoTitle.substring(0, 55) + "." + ytfile.getFormat().getExt();
//                } else {
//                    filename = videoTitle + "." + ytfile.getFormat().getExt();
//                }
//                filename = filename.replaceAll("[\\\\><\"|*?%:#/]", "");
//                downloadFromUrl(ytfile.getUrl(), videoTitle, filename);
//                finish();
//            }
//        });
//        mainLayout.addView(btn);
//    }
//
//    private void downloadFromUrl(String youtubeDlUrl, String downloadTitle, String fileName) {
//        Uri uri = Uri.parse(youtubeDlUrl);
//        DownloadManager.Request request = new DownloadManager.Request(uri);
//        request.setTitle(downloadTitle);
//
//        request.allowScanningByMediaScanner();
//        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
//
//        DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
//        manager.enqueue(request);
//    }}
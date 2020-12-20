package com.example.samsunghomeworkpicture;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class UrlPictureDownloader extends Service {
    public static String DOWNLOAD_SERVICE="DOWNLOAD_SERVICE";
    public static String PICTURE_CODE="PICTURE_CODE";
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // сообщение о запуске службы
        Toast.makeText(this, "Service started", Toast.LENGTH_SHORT).show();
        Thread threadUrl=new Thread(new PicTask());
        threadUrl.start();
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
       Toast.makeText(this,"Service stopped",Toast.LENGTH_SHORT).show();
    }
    private class PicTask implements Runnable{

        @Override
        public void run() {
            try {
                URL urlLink=new URL("https://cdn.sstatic.net/Sites/stackoverflow/Img/apple-touch-icon@2.png?v=73d79a89bded");
                Intent i=new Intent(DOWNLOAD_SERVICE);
               Bitmap pic=BitmapFactory.decodeStream((InputStream) urlLink.getContent());
                Log.d("UrlService","Bitmap pic is created");
                ByteArrayOutputStream bitmapOutput= new ByteArrayOutputStream();
                Log.d("UrlService","ByteArray");
                pic.compress(Bitmap.CompressFormat.PNG,100,bitmapOutput);
                i.putExtra(PICTURE_CODE,bitmapOutput.toByteArray());
                sendBroadcast(i);
                Log.d("UrlService","Broadcast is sended");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

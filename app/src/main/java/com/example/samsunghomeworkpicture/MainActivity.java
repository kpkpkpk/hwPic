package com.example.samsunghomeworkpicture;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import static com.example.samsunghomeworkpicture.UrlPictureDownloader.*;

public class MainActivity extends AppCompatActivity {
    Button button;
    ImageView image;
    Context context=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image=findViewById(R.id.imageView);
        button=findViewById(R.id.bttn);
        registerReceiver(receiver,new IntentFilter(UrlPictureDownloader.DOWNLOAD_SERVICE));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context,UrlPictureDownloader.class);
                startService(i);
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(context, UrlPictureDownloader.class);
        stopService(intent);
    }
    protected BroadcastReceiver receiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.hasExtra(UrlPictureDownloader.PICTURE_CODE)) {
                image.setImageBitmap(BitmapFactory.decodeByteArray(intent.getByteArrayExtra(UrlPictureDownloader.PICTURE_CODE), 0, intent.getByteArrayExtra(UrlPictureDownloader.PICTURE_CODE).length));
                Log.d("BroadcastLogF", "Image is set");
            }else{
                image.setImageResource(R.drawable.clown);
            }
        }
    };
}
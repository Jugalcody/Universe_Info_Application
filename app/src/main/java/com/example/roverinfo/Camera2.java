package com.example.roverinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.util.Linkify;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class Camera2 extends AppCompatActivity {

    TextView t, t2;
    ImageView im;
    ProgressBar p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        t = findViewById(R.id.cam);
        t2 = findViewById(R.id.dat);
        im = findViewById(R.id.imag);
        p = findViewById(R.id.pBar);

        getWindow().setStatusBarColor(getColor(R.color.main));

        /* GET DATA FROM INTENT */
        String title=getIntent().getStringExtra("title");
        String desc=getIntent().getStringExtra("desc");
        String img=getIntent().getStringExtra("img");

        if(title==null) title="No Title";
        if(desc==null) desc="No Description";
        if(img==null) img="";

        t.setText(title);
        t2.setText(desc);
        Linkify.addLinks(t2, Linkify.WEB_URLS);

        if(img.isEmpty()){
            p.setVisibility(View.GONE);
            Toast.makeText(this,"Image not found",Toast.LENGTH_LONG).show();
            return;
        }

        Picasso.get()
                .load(img)
                .placeholder(R.drawable.mars)
                .error(R.drawable.mars)
                .into(im, new Callback() {
                    @Override
                    public void onSuccess() {
                        p.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {
                        p.setVisibility(View.GONE);
                        Toast.makeText(Camera2.this,"Image Load Failed",Toast.LENGTH_LONG).show();
                    }
                });
    }
}
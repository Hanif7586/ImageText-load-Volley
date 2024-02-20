package com.example.newsapps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class DeatilsActivity extends AppCompatActivity {

    TextView texttitle,txtprice,txtdes;
    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deatils);



        texttitle=findViewById(R.id.txttitleid);

        txtdes=findViewById(R.id.txtdesID);
        imageView=findViewById(R.id.imageViewID);








        // Initialize SharedPreferences



        Intent intent=getIntent();
        texttitle.setText(intent.getStringExtra("Title"));

        txtdes.setText(intent.getStringExtra("des"));





        String imageUrl = getIntent().getStringExtra("Image");
        Picasso.get().load(imageUrl).into(imageView);

    }
}
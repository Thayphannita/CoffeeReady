package com.example.asus.coffeeready;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class CoffeeDetail extends AppCompatActivity {


    private TextView coffeeNameTxt;
    private ImageView coffeeImageImg;
    private Button buttonOrderTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coffee_detail);

        coffeeImageImg = findViewById(R.id.coffee_image);
        coffeeNameTxt = findViewById(R.id.coffee_name);
        buttonOrderTxt = findViewById(R.id.button_order);

        Intent intent = getIntent();
        String coffeeName = intent.getStringExtra("name");
        String coffeeId = intent.getStringExtra("id");
        String coffeeUrl = intent.getStringExtra("url");

        coffeeNameTxt.setText(coffeeName);
        Picasso.get()
                .load(coffeeUrl)
                .into(coffeeImageImg);
    }
}

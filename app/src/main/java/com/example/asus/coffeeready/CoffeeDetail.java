package com.example.asus.coffeeready;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class CoffeeDetail extends AppCompatActivity {


    private TextView coffeeNameTxt;
    private ImageView coffeeImageImg;
    private TextView descriptionTxt;
    private Button buttonOrderTxt;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coffee_detail);
        toolbar=findViewById(R.id.toolbar);
        setTitle("Coffee Ready");
        setSupportActionBar(toolbar);
//        toolbar.setSubtitle("coffee ready");

        coffeeImageImg = findViewById(R.id.coffee_image);
        coffeeNameTxt = findViewById(R.id.coffee_name);
        descriptionTxt = findViewById(R.id.description);
        buttonOrderTxt = findViewById(R.id.button_order);

        Intent intent = getIntent();
        String coffeeName = intent.getStringExtra("name");
        String description =intent.getStringExtra("description");
        String coffeeId = intent.getStringExtra("id");
        String coffeeUrl = intent.getStringExtra("url");

        coffeeNameTxt.setText(coffeeName);
        descriptionTxt.setText("Description :"+description);
        Picasso.get()
                .load(coffeeUrl)
                .into(coffeeImageImg);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_bar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.action_back){
            startActivity(new Intent(this,HomeActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

}

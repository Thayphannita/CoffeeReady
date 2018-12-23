package com.example.asus.coffeeready;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class CoffeeDetail extends AppCompatActivity {


    private TextView coffeeNameTxt;
    private ImageView coffeeImageImg;
    private TextView descriptionTxt;
    private Button buttonOrderTxt;
    private FirebaseFirestore db;
    private Button buttonSmall;
    private Button buttonMedium;
    private Button buttonBig;
    private Toolbar toolbar;
    private CoffeePrice price;
    private String coffeeId;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coffee_detail);
        toolbar=findViewById(R.id.toolbar);
//        setTitle("Coffee Ready");
        setSupportActionBar(toolbar);
//        toolbar.setSubtitle("coffee ready");
        db = FirebaseFirestore.getInstance();
        price = new CoffeePrice();
        coffeeImageImg = findViewById(R.id.coffee_image);
        coffeeNameTxt = findViewById(R.id.coffee_name);
        descriptionTxt = findViewById(R.id.description);
        buttonOrderTxt = findViewById(R.id.button_order);
        buttonSmall = findViewById(R.id.btn_small);
        buttonMedium = findViewById(R.id.btn_medium);
        buttonBig = findViewById(R.id.btn_big);

        Intent intent = getIntent();
        String coffeeName = intent.getStringExtra("name");
        String description =intent.getStringExtra("description");
        coffeeId = intent.getStringExtra("id");
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

    public void onButtonOrderClick(View v){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Map<String, Object> addData = new HashMap<>();
        addData.put("user_id", user.getUid());
        addData.put("product_id", coffeeId);
        addData.put("price", price.getPrice());
        addData.put("quantity", "1");
        db.collection("orderItem").document(user.getUid()).set(addData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(), "Successfully ordered", Toast.LENGTH_LONG).show();
            }
        });

        Map<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("token_id", FirebaseInstanceId.getInstance().getToken());
        db.collection("active_status").document(user.getUid()).set(tokenMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("TAG","saved token to active status");
            }
        });
    }
    public void onButtonSmallClick(View v){
        db.collection("price").document("small").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                price.setPrice(documentSnapshot.get("price").toString());
                buttonSmall.setText(documentSnapshot.get("price").toString());
                Log.d("TAG","Price: "+price.getPrice());

            }
        });
    }
    public void onButtonMediumClick(View v){
        db.collection("price").document("medium").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("TAG","Price: "+documentSnapshot.get("price"));
                price.setPrice(documentSnapshot.get("price").toString());
                buttonMedium.setText(documentSnapshot.get("price").toString());
            }
        });
    }
    public void onButtonBigClick(View v){
        db.collection("price").document("big").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("TAG","Price: "+documentSnapshot.get("price"));
                price.setPrice(documentSnapshot.get("price").toString());
                buttonBig.setText(documentSnapshot.get("price").toString());
            }
        });
    }
}
